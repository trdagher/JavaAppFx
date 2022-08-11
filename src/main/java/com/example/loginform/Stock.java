package com.example.loginform;

public class Stock {
    String type;
    String name;
    double price;
    int id;
    int stockQuantity;


    public Stock(String type, String name, double price, int id, int stockQuantity) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.id = id;
        this.stockQuantity = stockQuantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
