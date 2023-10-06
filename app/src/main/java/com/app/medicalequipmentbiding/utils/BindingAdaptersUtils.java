package com.app.medicalequipmentbiding.utils;

import android.widget.TextView;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.models.BidingOrder;

import androidx.databinding.BindingAdapter;

public class BindingAdaptersUtils {

    @BindingAdapter({"bind:closeDate", "bind:status"})
    public static void bindOrderStatus(TextView textView, long closeDate, String status) {
        if (closeDate < System.currentTimeMillis()) {
            textView.setText(BidingOrder.OrderStatus.CLOSED.name());
            textView.setBackgroundColor(textView.getContext().getColor(R.color.red));
        } else {
            textView.setText(status);
        }
    }

    @BindingAdapter({"bind:startDate", "bind:closeDate"})
    public static void bindOrderDates(TextView textView, long startDate, long closeDate) {
        textView.setText(String.format("From: %1s to: %2s",
                Utils.convertMillisecondsToDate(startDate, Constants.DATE_FORMAT),
                Utils.convertMillisecondsToDate(closeDate, Constants.DATE_FORMAT))
        );
    }

}
