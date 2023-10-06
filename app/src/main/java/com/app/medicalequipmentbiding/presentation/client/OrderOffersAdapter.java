package com.app.medicalequipmentbiding.presentation.client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.medicalequipmentbiding.data.models.Offer;
import com.app.medicalequipmentbiding.databinding.OfferItemLayoutBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderOffersAdapter extends RecyclerView.Adapter<OrderOffersAdapter.OrderOfferViewHolder> {

    private final List<Offer> offerList;
    private final OrderOffersCallback callback;

    public OrderOffersAdapter(List<Offer> offerList, OrderOffersCallback callback) {
        this.offerList = offerList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public OrderOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OfferItemLayoutBinding binding = OfferItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new OrderOfferViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderOfferViewHolder holder, int position) {
        Offer offer = offerList.get(position);
        holder.bind(offer, callback);
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    static class OrderOfferViewHolder extends RecyclerView.ViewHolder {

        private final OfferItemLayoutBinding binding;

        public OrderOfferViewHolder(@NonNull OfferItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(Offer offer, OrderOffersCallback callback) {
            binding.setOffer(offer);
            binding.setCallback(callback);
            binding.executePendingBindings();
        }
    }

    public interface OrderOffersCallback {
        void showOfferDetails(Offer offer);
    }
}
