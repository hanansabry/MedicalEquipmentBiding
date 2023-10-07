package com.app.medicalequipmentbiding.presentation.vendor;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.Equipment;
import com.app.medicalequipmentbiding.data.models.EquipmentOffer;
import com.app.medicalequipmentbiding.data.models.Offer;
import com.app.medicalequipmentbiding.databinding.ActivityOrderDetailsBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProvider;

public class OrderDetailsActivity extends BaseActivity {

    private ActivityOrderDetailsBinding binding;
    private VendorViewModel vendorViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    private SetEquipmentOffersAdapter adapter;
    private String orderId;
    private BidingOrder bidingOrder;

    @Override
    public View getDataBindingView() {
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        vendorViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(VendorViewModel.class);
        vendorViewModel.retrieveOrderDetails(orderId);
        vendorViewModel.getBidingOrderLiveData().observe(this, bidingOrder -> {
            this.bidingOrder = bidingOrder;
            if (bidingOrder != null) {
                binding.setOrder(bidingOrder);
                adapter = new SetEquipmentOffersAdapter(bidingOrder.getOrderItems());
                binding.equipmentItemsRecyclerview.setAdapter(adapter);
            }
        });

        vendorViewModel.getOfferStateLiveData().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Offer is sent successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        vendorViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    public void onSaveOfferClicked(View view) {
        Offer offer = new Offer();
        offer.setVendorId(sessionManager.getFirebaseId());
        offer.setVendorName(sessionManager.getUserName());
        offer.setOrderId(orderId);

        List<EquipmentOffer> equipmentOfferList = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            View equipmentView = binding.equipmentItemsRecyclerview.getChildAt(i);
            EditText priceEditText = equipmentView.findViewById(R.id.price_edittext);
            AppCompatSpinner stateSpinner = equipmentView.findViewById(R.id.state_spinner);

            double price = priceEditText.getText().toString().isEmpty() ? 0 : Double.parseDouble(priceEditText.getText().toString());
            String status = stateSpinner.getSelectedItemPosition() == 0 ? null : stateSpinner.getSelectedItem().toString();

            if (price == 0 || status == null) {
                Toast.makeText(this, "You must enter all items data", Toast.LENGTH_SHORT).show();
                return;
            }

            Equipment equipment = bidingOrder.getOrderItems().get(i);
            EquipmentOffer equipmentOffer = new EquipmentOffer();
            equipmentOffer.setType(equipment.getType());
            equipmentOffer.setItem(equipment.getItem());
            equipmentOffer.setQuantity(equipment.getQuantity());
            equipmentOffer.setNotes(equipment.getNotes());
            equipmentOffer.setState(status);
            equipmentOffer.setTotalPrice(price);
            equipmentOfferList.add(equipmentOffer);
        }
        offer.setItemsOffers(equipmentOfferList);
        vendorViewModel.saveOffer(offer);
    }
}