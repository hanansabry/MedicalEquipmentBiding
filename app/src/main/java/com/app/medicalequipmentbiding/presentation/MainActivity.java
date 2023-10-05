package com.app.medicalequipmentbiding.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.databinding.ActivityMainBinding;
import com.app.medicalequipmentbiding.presentation.authentication.LoginActivity;
import com.app.medicalequipmentbiding.presentation.authentication.RegisterActivity;
import com.app.medicalequipmentbiding.utils.Constants;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View getDataBindingView() {
        com.app.medicalequipmentbiding.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void onLoginAsClientClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Constants.LOGIN_TYPE, Constants.CLIENT);
        startActivity(intent);
    }

    public void onLoginAsVendorClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Constants.LOGIN_TYPE, Constants.VENDOR);
        startActivity(intent);
    }

    public void onRegisterNowClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}