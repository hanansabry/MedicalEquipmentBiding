package com.app.medicalequipmentbiding.utils;

import android.content.Context;

import com.app.medicalequipmentbiding.R;


public class DeleteListener {

    public void deleteItem(Context context, DeleteFunction func) {
        Utils.createAlertDialog(context,
                context.getString(R.string.delete_dialog_general_title),
                context.getString(R.string.delete_dialog_general_msg),
                (dialog, which) -> {
                    func.delete();
                });
    }

    @FunctionalInterface
    public interface DeleteFunction {
        void delete();
    }
}
