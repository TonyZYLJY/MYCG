package com.example.a125688.americano;

/**
 * Created by Cyprion Puli on 3/12/2018.
 */

public class ProductDetail {
    Double price;
    Integer productNumber;
    Integer size;

    public ProductDetail() {
    }

    public ProductDetail(Double price, Integer productNumber, Integer size) {
        this.price = price;
        this.productNumber = productNumber;
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public Integer getSize() {
        return size;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
