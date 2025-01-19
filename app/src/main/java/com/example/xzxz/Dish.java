package com.example.xzxz;

import java.io.Serializable;

public class Dish implements Serializable {
        private Long id;
        private String name;
        private double price;
        private String description;

        // Конструкторы, геттеры и сеттеры

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
