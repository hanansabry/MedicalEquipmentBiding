package com.app.medicalequipmentbiding.data.models;

import java.util.HashMap;
import java.util.List;

public class BidingOrder {

    private String orderId;
    private String clientId;
    private String title;
    private long startDate;
    private long closeDate;
    private boolean isDelivery;
    private String status;
    private List<Equipment> orderItems;

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

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(long closeDate) {
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

    public List<Equipment> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Equipment> orderItems) {
        this.orderItems = orderItems;
    }

    public enum OrderStatus {
        NEW, CLOSED, ON_DELIVERY;
    }
}
