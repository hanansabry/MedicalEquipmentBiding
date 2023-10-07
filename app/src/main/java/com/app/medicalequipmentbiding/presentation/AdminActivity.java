package com.app.medicalequipmentbiding.presentation;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.databinding.ActivityAdminBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.client.ClientBidingViewModel;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class AdminActivity extends BaseActivity {

    private ActivityAdminBinding binding;
    private AdminViewModel adminViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public View getDataBindingView() {
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AdminViewModel.class);
        adminViewModel.retrieveTotalOrdersSales();
        adminViewModel.getTotalSalesLiveData().observe(this, total -> {
            double appPercentage = total * 0.01;
            binding.setTotalSales(total);
            binding.setAppPercentage(appPercentage);
        });

        adminViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    public void onLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        sessionManager.logoutUser();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}