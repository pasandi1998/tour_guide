package com.example.wikings.Shehani.Model;

public class Hotel {
    private String Name;
    private String Province;
    private String Description;
    private String Address;
    private String Phone;
    private Double Price;

    private String ImageUrl;

    public Hotel() {
    }

    public Hotel(String name, String province, String description, String address, String phone, Double price, String imageUrl) {
        Name = name;
        Province = province;
        Description = description;
        Address = address;
        Phone = phone;
        Price = price;
        ImageUrl = imageUrl;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}

