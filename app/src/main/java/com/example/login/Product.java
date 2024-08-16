package com.example.login;

public class Product {
    private String name;
    private String price;
    private int type;

    public Product(String name, String price, int type) {
        this.name = name;
        this.price = price;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getType() {
        return type;
    }
}
