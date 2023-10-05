package com.app.medicalequipmentbiding.presentation.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.databinding.ActivityActiveBidingListBinding;
import com.app.medicalequipmentbiding.presentation.BaseActivity;

public class ActiveBidingListActivity extends BaseActivity {

    private ActivityActiveBidingListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View getDataBindingView() {
        binding = ActivityActiveBidingListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}