package com.app.medicalequipmentbiding.data.models;

import java.util.HashMap;

public class Offer {

    private String offerId;
    private String orderId;
    private String vendorId;
    private HashMap<String, EquipmentOffer> itemsOffers;

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

    public HashMap<String, EquipmentOffer> getItemsOffers() {
        return itemsOffers;
    }

    public void setItemsOffers(HashMap<String, EquipmentOffer> itemsOffers) {
        this.itemsOffers = itemsOffers;
    }
}
