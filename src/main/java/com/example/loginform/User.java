package com.example.loginform;

public class User {
   private String name ;
   private String NamesOutOfStock;
    public User (String name){
        this.name = name;
    }

    public String getNamesOutOfStock() {
        return NamesOutOfStock;
    }

    public void setNamesOutOfStock(String namesOutOfStock) {
        NamesOutOfStock = namesOutOfStock;
    }
}
