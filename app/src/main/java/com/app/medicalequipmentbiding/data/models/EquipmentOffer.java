package com.app.medicalequipmentbiding.data.models;

public class EquipmentOffer {

    private String itemId;
    private double totalPrice;
    private String type;
    private String item;
    private int quantity;
    private String notes;
    private String state;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public enum EquipmentState {
        NEW("New"), LIKE_NEW("Like New"), USED("Used");

        public final String state;
        EquipmentState(String state) {
            this.state = state;
        }
    }
}
