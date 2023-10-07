package com.app.medicalequipmentbiding.utils;

import android.view.View;
import android.widget.TextView;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.Equipment;
import com.app.medicalequipmentbiding.data.models.EquipmentOffer;

import java.util.List;

import androidx.databinding.BindingAdapter;

public class BindingAdaptersUtils {

    @BindingAdapter({"bind:closeDate", "bind:status"})
    public static void bindOrderStatus(TextView textView, long closeDate, String status) {
        if (closeDate < System.currentTimeMillis() || status.equals(BidingOrder.OrderStatus.CLOSED.name())) {
            textView.setText(BidingOrder.OrderStatus.CLOSED.name());
            textView.setBackgroundColor(textView.getContext().getColor(R.color.red));
        } else {
            textView.setText(status);
            textView.setBackgroundColor(textView.getContext().getColor(R.color.colorPrimary));
        }
    }

    @BindingAdapter({"bind:startDate", "bind:closeDate"})
    public static void bindOrderDates(TextView textView, long startDate, long closeDate) {
        textView.setText(String.format("From: %1s to: %2s",
                Utils.convertMillisecondsToDate(startDate, Constants.DATE_FORMAT),
                Utils.convertMillisecondsToDate(closeDate, Constants.DATE_FORMAT))
        );
    }

    @BindingAdapter("date")
    public static void bindDate(TextView textView, Long milliseconds) {
        if (milliseconds != null) {
            textView.setText(Utils.convertMillisecondsToDate(milliseconds, Constants.DATE_FORMAT));
        }
    }

    @BindingAdapter("delivery")
    public static void bindDelivery(TextView textView, boolean isDelivery) {
        if (isDelivery) {
            textView.setText("Delivery");
        } else {
            textView.setText("No Delivery");
        }
    }

    @BindingAdapter("offerPrice")
    public static void bindOfferPrice(TextView textView, List<EquipmentOffer> items) {
        if (items != null && !items.isEmpty()) {
            double total = 0;
            for (EquipmentOffer equipmentOffer : items) {
                total += equipmentOffer.getTotalPrice();
            }
            textView.setText(String.format("%s $", total));
        } else {
            textView.setText("0 $");
        }
    }

}
