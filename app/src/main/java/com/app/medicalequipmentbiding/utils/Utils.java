package com.app.medicalequipmentbiding.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.app.medicalequipmentbiding.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;

public class Utils {

    public static String convertTimestampToDate(long timestamp, String format) {
        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return dateFormat.format(date);
    }

    public static String convertMillisecondsToDate(long milliseconds, String format) {
        if (milliseconds == 0) {
            return null;
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            return dateFormat.format(calendar.getTime());
        }
    }

    public static AlertDialog.Builder createAlertDialog(Context context,
                                                        String title,
                                                        String message,
                                                        Drawable icon,
                                                        String positiveButtonText,
                                                        @Nullable String negativeButtonText,
                                                        DialogInterface.OnClickListener positiveListener,
                                                        @Nullable DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(icon);
        alertDialog.setPositiveButton(positiveButtonText, positiveListener);
        if (negativeButtonText != null) {
            alertDialog.setNegativeButton(negativeButtonText, negativeListener);
        }
        return alertDialog;
    }

    public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                onDateSetListener, year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    public static void createAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener approveListener) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.yes, approveListener);
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.cancel();
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static String getHumanTimeText(long milliseconds) {
        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }
}
