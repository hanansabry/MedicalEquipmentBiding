package com.app.medicalequipmentbiding.presentation.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.databinding.ActivityAwardingBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.utils.Constants;

import javax.inject.Inject;

public class AwardingActivity extends BaseActivity {

    private ActivityAwardingBinding binding;
    private ClientBidingViewModel clientBidingViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public View getDataBindingView() {
        binding = ActivityAwardingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String orderId = getIntent().getStringExtra(Constants.ORDER_ID);

        clientBidingViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(ClientBidingViewModel.class);
        clientBidingViewModel.retrieveOrderDetails(orderId);
        clientBidingViewModel.getBidingOrderLiveData().observe(this, bidingOrder -> {
            if (bidingOrder != null) {
                binding.setOrder(bidingOrder);
            }
        });

        clientBidingViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }
}