package com.example.myviewlist;

public class Product {
    private String name, info;
    private double price;
    private int qty;

    public Product(String name, double price, String info, int qty) {
        this.name = name;
        this.price = price;
        this.info = info;
        this.qty = qty;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
