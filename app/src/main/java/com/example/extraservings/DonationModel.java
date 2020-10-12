package com.example.extraservings;

public class DonationModel {

    private String address, food;
    private int quantity, id;
    //private boolean isActive;


    public DonationModel( int id,String address, String food, int quantity) {
        this.address = address;
        this.food = food;
        this.quantity = quantity;
        this.id = id;
    }

    public DonationModel() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
