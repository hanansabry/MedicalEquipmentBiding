package com.app.medicalequipmentbiding.presentation.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.databinding.ActivityOfferDetailsBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.utils.Constants;

import javax.inject.Inject;

public class OfferDetailsActivity extends BaseActivity {

    private ActivityOfferDetailsBinding binding;
    private ClientBidingViewModel clientBidingViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public View getDataBindingView() {
        binding = ActivityOfferDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String offerId = getIntent().getStringExtra(Constants.OFFER_ID);

        clientBidingViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(ClientBidingViewModel.class);
        clientBidingViewModel.retrieveOfferDetails(offerId);
        clientBidingViewModel.getOfferDetailsLiveData().observe(this, offer -> {
            binding.setOffer(offer);
            EquipmentItemsAdapter adapter = new EquipmentItemsAdapter(offer.getItemsOffers());
            binding.equipmentItemsRecyclerview.setAdapter(adapter);
        });

        clientBidingViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

}