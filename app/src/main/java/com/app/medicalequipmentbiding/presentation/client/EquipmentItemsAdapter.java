package com.app.medicalequipmentbiding.presentation.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.medicalequipmentbiding.data.models.EquipmentOffer;
import com.app.medicalequipmentbiding.databinding.EquipmentOfferItemLayoutBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EquipmentItemsAdapter extends RecyclerView.Adapter<EquipmentItemsAdapter.EquipmentItemOfferViewHolder> {

    private final List<EquipmentOffer> equipmentOffers;

    public EquipmentItemsAdapter(List<EquipmentOffer> equipmentOffers) {
        this.equipmentOffers = equipmentOffers;
    }

    @NonNull
    @Override
    public EquipmentItemOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EquipmentOfferItemLayoutBinding binding = EquipmentOfferItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new EquipmentItemOfferViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentItemOfferViewHolder holder, int position) {
        EquipmentOffer equipmentOffer = equipmentOffers.get(position);
        holder.bind(equipmentOffer);
    }

    @Override
    public int getItemCount() {
        return equipmentOffers.size();
    }

    static class EquipmentItemOfferViewHolder extends RecyclerView.ViewHolder {

        private final EquipmentOfferItemLayoutBinding binding;

        public EquipmentItemOfferViewHolder(@NonNull EquipmentOfferItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(EquipmentOffer equipmentOffer) {
            binding.setEquipmentOffer(equipmentOffer);
        }
    }
}
