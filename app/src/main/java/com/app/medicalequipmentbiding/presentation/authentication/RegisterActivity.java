package com.app.medicalequipmentbiding.presentation.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.models.Client;
import com.app.medicalequipmentbiding.data.models.Vendor;
import com.app.medicalequipmentbiding.databinding.ActivityRegisterBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.presentation.MainActivity;
import com.app.medicalequipmentbiding.presentation.client.ClientBidingListActivity;
import com.app.medicalequipmentbiding.presentation.vendor.ActiveBidingListActivity;
import com.app.medicalequipmentbiding.utils.Constants;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;

public class RegisterActivity extends BaseActivity {

    private AuthenticationViewModel authenticationViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    private ActivityRegisterBinding binding;
    private String accountType = Constants.CLIENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        authenticationViewModel.getValidationState().observe(this, status -> {
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.GONE);
            binding.registerButton.setVisibility(View.VISIBLE);
        });

        authenticationViewModel.getClientLiveData().observe(this, client -> {
            Toast.makeText(this, client.getUserId(), Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.GONE);
            binding.registerButton.setVisibility(View.VISIBLE);
            sessionManager.createLoginSession(client.getUserId(), client.getOrganization(), client.getEmail(), Constants.CLIENT);
            //go to client path
            startActivity(new Intent(this, ClientBidingListActivity.class));
        });

        authenticationViewModel.getVendorLiveData().observe(this, vendor -> {
            Toast.makeText(this, vendor.getUserId(), Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.GONE);
            binding.registerButton.setVisibility(View.VISIBLE);
            sessionManager.createLoginSession(vendor.getUserId(), vendor.getOrganization(), vendor.getEmail(), Constants.VENDOR);
            //go to vendor path
            startActivity(new Intent(this, ActiveBidingListActivity.class));
        });

        authenticationViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.GONE);
            binding.registerButton.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public View getDataBindingView() {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void onLoginClicked(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onRegisterClicked(View view) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.registerButton.setVisibility(View.GONE);

        String organizationName = binding.organizationEditText.getText().toString();
        String email = binding.emailEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();
        String rePassword = binding.confirmPasswordEditText.getText().toString();
        String phone = binding.phoneEditText.getText().toString();
        String address = binding.addressEditText.getText().toString();
        String approveNum = binding.approveNumEditText.getText().toString();
        accountType = binding.accountTypeRadioGroup.getCheckedRadioButtonId() == R.id.client_radio ? Constants.CLIENT : Constants.VENDOR;

        boolean isValid = authenticationViewModel.validateRegister(
                organizationName, email, password, rePassword, phone, approveNum, accountType
        );
        if (isValid) {
            if (accountType.equals(Constants.CLIENT)) {
                Client client = new Client();
                client.setOrganization(organizationName);
                client.setEmail(email);
                client.setPassword(password);
                client.setPhone(phone);
                client.setAddress(address);
                client.setApproveNum(approveNum);
                authenticationViewModel.registerClient(client);
            } else {
                Vendor vendor = new Vendor();
                vendor.setOrganization(organizationName);
                vendor.setEmail(email);
                vendor.setPassword(password);
                vendor.setPhone(phone);
                vendor.setAddress(address);
                vendor.setApproveNum(approveNum);
                vendor.setRank(0);
                authenticationViewModel.registerVendor(vendor);
            }
        }
    }
}