package com.app.medicalequipmentbiding.data.models;

public class VendorOfferHistory {

    private String orderTitle;
    private String winningVendor;
    private double offerPrice;

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getWinningVendor() {
        return winningVendor;
    }

    public void setWinningVendor(String winningVendor) {
        this.winningVendor = winningVendor;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }
}
