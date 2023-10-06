package com.app.medicalequipmentbiding.presentation.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.databinding.ActivityClientBidingListBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;

import javax.inject.Inject;

public class ClientBidingListActivity extends BaseActivity implements BidingListAdapter.BidingListCallback {

    private ActivityClientBidingListBinding binding;
    private ClientBidingViewModel clientBidingViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;


    @Override
    public View getDataBindingView() {
        binding = ActivityClientBidingListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientBidingViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(ClientBidingViewModel.class);
        clientBidingViewModel.retrieveClientBidingOrders(sessionManager.getFirebaseId());
        clientBidingViewModel.getOrdersLiveData().observe(this, orders -> {
            BidingListAdapter adapter = new BidingListAdapter(orders, this);
            binding.bidingListRecyclerview.setAdapter(adapter);
        });

        clientBidingViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    public void onAddClicked(View view) {
        startActivity(new Intent(this, NewBidingOrderActivity.class));
    }

    @Override
    public void onBidingOrderClicked(BidingOrder bidingOrder) {
        Toast.makeText(this, bidingOrder.getTitle() + " is clicked", Toast.LENGTH_SHORT).show();
    }
}