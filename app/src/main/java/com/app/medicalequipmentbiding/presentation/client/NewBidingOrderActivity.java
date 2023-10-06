package com.app.medicalequipmentbiding.presentation.client;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.Equipment;
import com.app.medicalequipmentbiding.data.models.MedicalType;
import com.app.medicalequipmentbiding.databinding.ActivityNewBidingOrderBinding;
import com.app.medicalequipmentbiding.databinding.AddEquipmentItemLayoutBinding;
import com.app.medicalequipmentbiding.di.ViewModelProviderFactory;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.utils.Constants;
import com.app.medicalequipmentbiding.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProvider;

public class NewBidingOrderActivity extends BaseActivity {

    private ActivityNewBidingOrderBinding binding;
    private ClientBidingViewModel clientBidingViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    private ArrayAdapter<String> typesAdapter;
    private List<MedicalType> medicalTypes;
    private List<Equipment> equipmentList;
    private long startDate, closeDate;

    @Override
    public View getDataBindingView() {
        binding = ActivityNewBidingOrderBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        equipmentList = new ArrayList<>();
        clientBidingViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(ClientBidingViewModel.class);
        clientBidingViewModel.getAddOrderStateLiveData().observe(this, success -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.save.setVisibility(View.VISIBLE);
            finish();
        });
        clientBidingViewModel.getErrorState().observe(this, error -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.save.setVisibility(View.VISIBLE);
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
        retrieveMedicalTypes();
    }

    private void retrieveMedicalTypes() {
        clientBidingViewModel.retrieveMedicalEquipmentTypes();
        clientBidingViewModel.getTypesLiveData().observe(this, types -> {
            medicalTypes = types;
            typesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            ArrayList<String> typesNamesList = new ArrayList<>();
            for (MedicalType type : types) {
                typesNamesList.add(type.getName());
            }
            typesAdapter.add(getString(R.string.select_type));
            typesAdapter.addAll(typesNamesList);
        });
    }

    public void onDateClicked(View view) {
        Utils.showDatePicker(this, (view1, year, month, dayOfMonth) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            String date = Utils.convertMillisecondsToDate(cal.getTimeInMillis(), Constants.DATE_FORMAT);
            startDate = cal.getTimeInMillis();
            binding.dateEditText.setText(date);
        });
    }

    public void onAddItemClicked(View view) {
        AddEquipmentItemLayoutBinding equipmentItemLayoutBinding = AddEquipmentItemLayoutBinding.inflate(getLayoutInflater());
        View addEquipmentItemView = equipmentItemLayoutBinding.getRoot();
        addEquipmentItemView.setId(View.generateViewId());

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(NewBidingOrderActivity.this, android.R.layout.simple_list_item_1);
        itemsAdapter.add(getString(R.string.select_item));
        equipmentItemLayoutBinding.equipmentItemSpinner.setAdapter(itemsAdapter);

        equipmentItemLayoutBinding.equipmentTypeSpinner.setAdapter(typesAdapter);
        equipmentItemLayoutBinding.equipmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    MedicalType selectedType = medicalTypes.get(position - 1);
                    itemsAdapter.addAll(selectedType.getItems());
                    equipmentItemLayoutBinding.equipmentItemSpinner.setAdapter(itemsAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        equipmentItemLayoutBinding.deleteItem.setOnClickListener(v -> binding.equipmentItemsLayout.removeView(addEquipmentItemView));
        binding.equipmentItemsLayout.addView(addEquipmentItemView);
    }

    private boolean validateEquipmentList() {
        for (int i = 0; i < binding.equipmentItemsLayout.getChildCount(); i++) {
            View itemLayout = binding.equipmentItemsLayout.getChildAt(i);
            AppCompatSpinner typeSpinner = itemLayout.findViewById(R.id.equipment_type_spinner);
            AppCompatSpinner itemSpinner = itemLayout.findViewById(R.id.equipment_item_spinner);
            AppCompatSpinner itemStatusSpinner = itemLayout.findViewById(R.id.item_status_spinner);
            EditText quantityEditText = itemLayout.findViewById(R.id.quantity_edittext);
            EditText notesEditText = itemLayout.findViewById(R.id.notes_edittext);

            String selectedType = typeSpinner.getSelectedItem().toString();
            String selectedItem = itemSpinner.getSelectedItem().toString();
            String selectedStatus = itemStatusSpinner.getSelectedItem().toString();
            int quantity = quantityEditText.getText().toString().isEmpty() ? 0 : Integer.parseInt(quantityEditText.getText().toString());
            String notes = notesEditText.getText().toString();

            if (quantity == 0 || typeSpinner.getSelectedItemPosition() == 0 ||
                    itemSpinner.getSelectedItemPosition() == 0 || itemStatusSpinner.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "You must enter all items data", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Equipment equipment = new Equipment();
                equipment.setItem(selectedItem);
                equipment.setType(selectedType);
                equipment.setNotes(notes);
                equipment.setQuantity(quantity);
                equipment.setState(selectedStatus);
                equipmentList.add(equipment);
            }
        }
        return true;
    }

    public void onSaveClicked(View view) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.save.setVisibility(View.GONE);
        boolean isValidList = validateEquipmentList();
        if (!isValidList) {
            binding.progressBar.setVisibility(View.GONE);
            binding.save.setVisibility(View.VISIBLE);
            return;
        }

        String title = binding.titleEditText.getText().toString();
        boolean isDelivery = binding.deliverySwitch.isChecked();
        int duration = binding.durationEditText.getText().toString().isEmpty() ? 0 :
                Integer.parseInt(binding.durationEditText.getText().toString());
        //set end date
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, duration);
        closeDate = calendar.getTimeInMillis();

        if (title.isEmpty() || duration == 0 || startDate == 0 || closeDate == 0 || equipmentList.isEmpty()) {
            Toast.makeText(this, R.string.all_fields_required, Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.GONE);
            binding.save.setVisibility(View.VISIBLE);
        } else {
            BidingOrder order = new BidingOrder();
            order.setTitle(title);
            order.setStartDate(startDate);
            order.setCloseDate(closeDate);
            order.setDelivery(isDelivery);
            order.setStatus(BidingOrder.OrderStatus.NEW.name());
            order.setClientId(sessionManager.getFirebaseId());
            order.setOrderItems(equipmentList);
            clientBidingViewModel.addNewOrder(order);
        }
    }
}