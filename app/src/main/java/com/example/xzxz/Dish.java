package com.example.xzxz;

import java.io.Serializable;

public class Dish implements Serializable {
    private String name;
    private String description;
    private String price;
    private int id;

    // Конструкторы, геттеры и сеттеры

    public Dish() {
    }

    public Dish(String name, String description, String price,int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id=id;
    }


public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public  int getId(){
        return id;
    }
}
