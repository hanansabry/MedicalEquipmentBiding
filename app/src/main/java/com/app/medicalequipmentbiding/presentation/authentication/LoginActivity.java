package com.app.medicalequipmentbiding.presentation.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.databinding.ActivityLoginBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.presentation.client.ClientBidingListActivity;
import com.app.medicalequipmentbiding.presentation.vendor.ActiveBidingListActivity;
import com.app.medicalequipmentbiding.utils.Constants;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private AuthenticationViewModel authenticationViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    private String loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginType = getIntent().getStringExtra(Constants.LOGIN_TYPE);
        if (loginType.equals(Constants.CLIENT)) {
            binding.loginLbl.setText(R.string.login_as_client);
        } else {
            binding.loginLbl.setText(R.string.login_as_vendor);
        }

        authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        authenticationViewModel.getClientLiveData().observe(this, client -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.loginButton.setVisibility(View.VISIBLE);
            if (client != null) {
                Toast.makeText(this, "Login as client successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ClientBidingListActivity.class));
                sessionManager.createLoginSession(client.getUserId(), client.getOrganization(), client.getEmail(), Constants.CLIENT);
            }
        });
        authenticationViewModel.getVendorLiveData().observe(this, vendor -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.loginButton.setVisibility(View.VISIBLE);
            if (vendor != null) {
                Toast.makeText(this, "Login as vendor successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ActiveBidingListActivity.class));
                sessionManager.createLoginSession(vendor.getUserId(), vendor.getOrganization(), vendor.getEmail(), Constants.VENDOR);
            }
        });
        authenticationViewModel.getErrorState().observe(this, error -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.loginButton.setVisibility(View.VISIBLE);
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public View getDataBindingView() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void onLoginClicked(View view) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginButton.setVisibility(View.GONE);
        String email = binding.emailEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.email_password_required, Toast.LENGTH_SHORT).show();
            return;
        }

        if (loginType.equals(Constants.CLIENT)) {
            authenticationViewModel.loginAsClient(email, password);
        } else {
            authenticationViewModel.loginAsVendor(email, password);
        }
    }
}