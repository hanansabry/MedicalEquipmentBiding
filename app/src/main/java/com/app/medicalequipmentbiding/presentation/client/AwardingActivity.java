package com.app.medicalequipmentbiding.presentation.client;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.Offer;
import com.app.medicalequipmentbiding.databinding.ActivityAwardingBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.utils.Constants;

import javax.inject.Inject;

public class AwardingActivity extends BaseActivity implements OrderOffersAdapter.OrderOffersCallback {

    private ActivityAwardingBinding binding;
    private ClientBidingViewModel clientBidingViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    private OrderOffersAdapter orderOffersAdapter;
    private Offer selectedOffer;
    private String orderId;

    @Override
    public View getDataBindingView() {
        binding = ActivityAwardingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);

        clientBidingViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(ClientBidingViewModel.class);
        clientBidingViewModel.retrieveOrderDetails(orderId);
        clientBidingViewModel.getBidingOrderLiveData().observe(this, bidingOrder -> {
            if (bidingOrder != null) {
                binding.setOrder(bidingOrder);
                if (bidingOrder.getStatus().equals(BidingOrder.OrderStatus.CLOSED.name())) {
                    binding.selectOfferButton.setVisibility(View.GONE);
                } else {
                    binding.selectOfferButton.setVisibility(View.VISIBLE);
                }
                clientBidingViewModel.retrieveOrderOffers(bidingOrder.getOrderId());
            }
        });

        clientBidingViewModel.getOrderOffersLiveData().observe(this, offers -> {
            orderOffersAdapter = new OrderOffersAdapter(offers, this);
            binding.offersRecyclerview.setAdapter(orderOffersAdapter);
        });

        clientBidingViewModel.getSelectOfferStateLiveData().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Offer is selected", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        clientBidingViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void showOfferDetails(Offer offer) {
        Intent intent = new Intent(this, OfferDetailsActivity.class);
        intent.putExtra(Constants.OFFER_ID, offer.getOfferId());
        startActivity(intent);
    }

    @Override
    public void onOfferClicked(Offer offer, int position) {
        selectedOffer = offer;
        orderOffersAdapter.updateSelectedPosition(position);
    }

    public void onSelectOfferClicked(View view) {
        clientBidingViewModel.selectOffer(orderId, selectedOffer.getOfferId());
    }
}