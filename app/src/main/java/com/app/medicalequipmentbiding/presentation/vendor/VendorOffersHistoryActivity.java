package com.app.medicalequipmentbiding.presentation.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.models.Offer;
import com.app.medicalequipmentbiding.databinding.ActivityVendorOffersHistoryBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;

import javax.inject.Inject;

public class VendorOffersHistoryActivity extends BaseActivity {

    private ActivityVendorOffersHistoryBinding binding;
    private VendorViewModel vendorViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public View getDataBindingView() {
        binding = ActivityVendorOffersHistoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vendorViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(VendorViewModel.class);
        vendorViewModel.retrieveVendorOffersHistory(sessionManager.getFirebaseId());
        vendorViewModel.getHistoryLiveData().observe(this, offersHistory -> {
            OffersHistoryAdapter adapter = new OffersHistoryAdapter(offersHistory);
            binding.offersHistoryRecyclerview.setAdapter(adapter);
        });

        vendorViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }
}