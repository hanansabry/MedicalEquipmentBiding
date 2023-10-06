package com.app.medicalequipmentbiding.presentation.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.databinding.BidingOrderItemLayoutBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BidingListAdapter extends RecyclerView.Adapter<BidingListAdapter.BidingOrderViewHolder> {

    private final List<BidingOrder> orderList;
    private final BidingListCallback bidingListCallback;

    public BidingListAdapter(List<BidingOrder> orderList, BidingListCallback bidingListCallback) {
        this.orderList = orderList;
        this.bidingListCallback = bidingListCallback;
    }

    @NonNull
    @Override
    public BidingOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BidingOrderItemLayoutBinding binding = BidingOrderItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new BidingOrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BidingOrderViewHolder holder, int position) {
        BidingOrder bidingOrder = orderList.get(position);
        holder.bind(bidingOrder, bidingListCallback);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class BidingOrderViewHolder extends RecyclerView.ViewHolder {

        private final BidingOrderItemLayoutBinding binding;

        public BidingOrderViewHolder(@NonNull BidingOrderItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(BidingOrder bidingOrder, BidingListCallback callback) {
            binding.setOrder(bidingOrder);
            binding.setCallback(callback);
            binding.executePendingBindings();
        }
    }

    public interface BidingListCallback {
        void onBidingOrderClicked(BidingOrder bidingOrder);
    }
}
