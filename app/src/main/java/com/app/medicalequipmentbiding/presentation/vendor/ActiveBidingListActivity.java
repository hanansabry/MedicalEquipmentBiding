package com.app.medicalequipmentbiding.presentation.vendor;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.databinding.ActivityActiveBidingListBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.presentation.MainActivity;
import com.app.medicalequipmentbiding.presentation.client.BidingListAdapter;
import com.app.medicalequipmentbiding.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class ActiveBidingListActivity extends BaseActivity implements BidingListAdapter.BidingListCallback {

    private ActivityActiveBidingListBinding binding;
    private VendorViewModel vendorViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public View getDataBindingView() {
        binding = ActivityActiveBidingListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vendorViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(VendorViewModel.class);
        vendorViewModel.retrieveActiveBiding();
        vendorViewModel.getOrdersLiveData().observe(this, bidingOrders -> {
            BidingListAdapter adapter = new BidingListAdapter(bidingOrders, this);
            binding.bidingListRecyclerview.setAdapter(adapter);
        });

        vendorViewModel.getErrorState().observe(this, error -> {
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

    @Override
    public void onBidingOrderClicked(BidingOrder bidingOrder) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra(Constants.ORDER_ID, bidingOrder.getOrderId());
        startActivity(intent);
    }
}