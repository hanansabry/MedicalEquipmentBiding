package com.app.medicalequipmentbiding.presentation.client;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.models.Offer;
import com.app.medicalequipmentbiding.databinding.OfferItemLayoutBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

public class OrderOffersAdapter extends RecyclerView.Adapter<OrderOffersAdapter.OrderOfferViewHolder> {

    private final List<Offer> offerList;
    private final OrderOffersCallback callback;
    private int selectedPosition = -1;
    private final SparseBooleanArray showRankVisibilityArray;


    public OrderOffersAdapter(List<Offer> offerList, OrderOffersCallback callback) {
        this.offerList = offerList;
        this.callback = callback;
        showRankVisibilityArray = new SparseBooleanArray(offerList.size());
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
        holder.bind(offer, callback, position);
        if (position == selectedPosition) {
            holder.itemView.setBackground(AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.cornered_primary_shadow_two_with_stroke));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(R.color.white));
        }

        boolean showRankView = showRankVisibilityArray.get(position);
        if (showRankView) {
            holder.binding.rankLayout.setVisibility(View.VISIBLE);
        } else {
            holder.binding.rankLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public void updateSelectedPosition(int position) {
        notifyItemChanged(selectedPosition);
        selectedPosition = position;
        notifyItemChanged(selectedPosition);
    }

    public void setShowRankView(int position, boolean show) {
        showRankVisibilityArray.put(position, show);
        notifyItemChanged(position);
    }

    static class OrderOfferViewHolder extends RecyclerView.ViewHolder {

        private final OfferItemLayoutBinding binding;

        public OrderOfferViewHolder(@NonNull OfferItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(Offer offer, OrderOffersCallback callback, int position) {
            binding.setOffer(offer);
            binding.setPosition(position);
            binding.setCallback(callback);
            binding.executePendingBindings();
        }
    }

    public interface OrderOffersCallback {
        void showOfferDetails(Offer offer);

        void onOfferClicked(Offer offer, int position);

        void onSaveRankClicked(int position, String vendorId, String offerId);
    }
}
