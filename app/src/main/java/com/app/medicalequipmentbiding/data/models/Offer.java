package com.app.medicalequipmentbiding.data.models;

import java.util.HashMap;
import java.util.List;

public class Offer {

    private String offerId;
    private String orderId;
    private String vendorId;
    private String vendorName;
    private double vendorRank;
    private List<EquipmentOffer> itemsOffers;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public double getVendorRank() {
        return vendorRank;
    }

    public void setVendorRank(double vendorRank) {
        this.vendorRank = vendorRank;
    }

    public List<EquipmentOffer> getItemsOffers() {
        return itemsOffers;
    }

    public void setItemsOffers(List<EquipmentOffer> itemsOffers) {
        this.itemsOffers = itemsOffers;
    }
}
