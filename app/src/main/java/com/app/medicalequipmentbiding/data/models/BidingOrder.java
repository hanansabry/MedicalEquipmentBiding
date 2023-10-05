package com.app.medicalequipmentbiding.data.models;

import java.util.HashMap;

public class BidingOrder {

    private String orderId;
    private String clientId;
    private String title;
    private String startDate;
    private String closeDate;
    private boolean isDelivery;
    private String status;
    private HashMap<String, Equipment> orderItems;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, Equipment> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(HashMap<String, Equipment> orderItems) {
        this.orderItems = orderItems;
    }
}
