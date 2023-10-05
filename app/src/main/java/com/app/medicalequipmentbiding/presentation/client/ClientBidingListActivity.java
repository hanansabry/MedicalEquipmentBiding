package com.app.medicalequipmentbiding.presentation.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.databinding.ActivityClientBidingListBinding;
import com.app.medicalequipmentbiding.presentation.BaseActivity;

public class ClientBidingListActivity extends BaseActivity {

    private ActivityClientBidingListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_biding_list);
    }

    @Override
    public View getDataBindingView() {
        binding = ActivityClientBidingListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}