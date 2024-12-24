package com.example.xzxz;
public class Inform {
    private String description;
    private String email;
    private String phone;
    private String addres;

    // Пустой конструктор
    public Inform() {}

    // Геттеры и сеттеры
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return addres; }
    public void setAddress(String addres) { this.addres = addres; }
}
