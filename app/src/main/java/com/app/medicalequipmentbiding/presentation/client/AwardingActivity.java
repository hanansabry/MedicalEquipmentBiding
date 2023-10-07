package com.app.medicalequipmentbiding.presentation.client;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.Offer;
import com.app.medicalequipmentbiding.databinding.ActivityAwardingBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.utils.Constants;

import java.util.List;

import javax.inject.Inject;

public class AwardingActivity extends BaseActivity implements OrderOffersAdapter.OrderOffersCallback {

    private ActivityAwardingBinding binding;
    private ClientBidingViewModel clientBidingViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    private OrderOffersAdapter orderOffersAdapter;
    private Offer selectedOffer;
    private String orderId;
    private BidingOrder bidingOrder;

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
                this.bidingOrder = bidingOrder;
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
            if (bidingOrder.getSelectedOffer() != null) {
                int selectedOfferPosition = getOfferPosition(bidingOrder.getSelectedOffer(), offers);
                orderOffersAdapter.updateSelectedPosition(selectedOfferPosition);
                orderOffersAdapter.setShowRankView(selectedOfferPosition, true);
            }
        });

        clientBidingViewModel.getSelectOfferStateLiveData().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Offer is selected", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        clientBidingViewModel.getSetRankStateLiveData().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Rank is set successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Can't set rank", Toast.LENGTH_SHORT).show();
            }
        });

        clientBidingViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    private int getOfferPosition(String selectedOffer, List<Offer> offers) {
        for (int i = 0; i < offers.size(); i++) {
            Offer offer = offers.get(i);
            if (selectedOffer.equals(offer.getOfferId())) {
                return i;
            }
        }
        return -1;
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

    @Override
    public void onSaveRankClicked(int position, String vendorId, String offerId) {
        View selectedOfferView = binding.offersRecyclerview.getChildAt(position);
        AppCompatSpinner rankSpinner = selectedOfferView.findViewById(R.id.rank_spinner);
        if (rankSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "You must select value", Toast.LENGTH_SHORT).show();
        } else {
            int rankValue = Integer.parseInt(rankSpinner.getSelectedItem().toString().trim());
            clientBidingViewModel.setVendorRank(vendorId, offerId, rankValue);
        }
    }

    public void onSelectOfferClicked(View view) {
        clientBidingViewModel.selectOffer(orderId, selectedOffer.getOfferId());
    }
}