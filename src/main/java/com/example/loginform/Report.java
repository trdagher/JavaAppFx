package com.example.loginform;

public class Report {
    String type;
    String name;
    double price;
    int id;
    int quantitySold;

    public Report(String type, String name, double price, int id, int quantitySold) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.id = id;
        this.quantitySold = quantitySold;
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

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}
