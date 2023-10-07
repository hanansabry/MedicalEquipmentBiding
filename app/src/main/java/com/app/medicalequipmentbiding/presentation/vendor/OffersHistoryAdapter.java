package com.app.medicalequipmentbiding.presentation.vendor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.medicalequipmentbiding.data.models.VendorOfferHistory;
import com.app.medicalequipmentbiding.databinding.VendorOfferHistoryItemLayoutBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OffersHistoryAdapter extends RecyclerView.Adapter<OffersHistoryAdapter.OfferHistoryViewHolder> {

    private final List<VendorOfferHistory> offerHistoryList;

    public OffersHistoryAdapter(List<VendorOfferHistory> offerHistoryList) {
        this.offerHistoryList = offerHistoryList;
    }

    @NonNull
    @Override
    public OfferHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VendorOfferHistoryItemLayoutBinding binding = VendorOfferHistoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new OfferHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferHistoryViewHolder holder, int position) {
        VendorOfferHistory offerHistory = offerHistoryList.get(position);
        holder.bind(offerHistory);
    }

    @Override
    public int getItemCount() {
        return offerHistoryList.size();
    }

    static class OfferHistoryViewHolder extends RecyclerView.ViewHolder {

        private final VendorOfferHistoryItemLayoutBinding binding;

        public OfferHistoryViewHolder(@NonNull VendorOfferHistoryItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(VendorOfferHistory offerHistory) {
            binding.setOfferHistory(offerHistory);
        }
    }
}
