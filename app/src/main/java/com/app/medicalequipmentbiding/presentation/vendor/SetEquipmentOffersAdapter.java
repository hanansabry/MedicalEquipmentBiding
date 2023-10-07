package com.app.medicalequipmentbiding.presentation.vendor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.medicalequipmentbiding.data.models.Equipment;
import com.app.medicalequipmentbiding.databinding.SetEquipmentOfferItemLayoutBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SetEquipmentOffersAdapter extends RecyclerView.Adapter<SetEquipmentOffersAdapter.SetEquipmentOfferViewHolder> {

    private final List<Equipment> equipmentList;

    public SetEquipmentOffersAdapter(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    @NonNull
    @Override
    public SetEquipmentOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SetEquipmentOfferItemLayoutBinding binding = SetEquipmentOfferItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new SetEquipmentOfferViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SetEquipmentOfferViewHolder holder, int position) {
        Equipment equipment = equipmentList.get(position);
        holder.bind(equipment);
    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    static class SetEquipmentOfferViewHolder extends RecyclerView.ViewHolder {

        private final SetEquipmentOfferItemLayoutBinding binding;

        public SetEquipmentOfferViewHolder(@NonNull SetEquipmentOfferItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(Equipment equipment) {
            binding.setEquipment(equipment);
        }
    }
}
