package com.example.loginform;

public class Products {
     String type;
    String name;
    double price;
     int id;
    int cartQuantity;

    public Products(String type, String name, double price, int id,int cartQuantity) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.id = id;
        this.cartQuantity = cartQuantity;
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

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
}
