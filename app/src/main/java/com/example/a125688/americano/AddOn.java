package com.example.a125688.americano;

/**
 * Created by SaraChiraDiaz on 2/18/2018.
 */

public class AddOn {
    String description;
    Integer number;
    Integer addOnType;
    Double price;
    Integer productType;

    public AddOn() {
    }

    public AddOn(String description, Integer number, Integer addOnType, Double price, Integer productType) {
        this.description = description;
        this.number = number;
        this.addOnType = addOnType;
        this.price = price;
        this.productType = productType;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getAddOnType() {
        return addOnType;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setAddOnType(Integer addOnType) {
        this.addOnType = addOnType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

