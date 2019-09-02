package com.example.a125688.americano;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Sara Chira on 3/12/2018.
 */
@IgnoreExtraProperties
public class Cartline {
    Integer attachedToLineNo;
    String description;
    Integer lineNo;
    Double price;
    Integer productNo;
    String size;
    String userId;
    Integer quantity;

   @Exclude
   private String Key;

    public Cartline() {
    }

    public Cartline(Integer attachedToLineNo, String description, Integer lineNo, Double price, Integer productNo, String size, String userId, Integer quantity) {
        this.attachedToLineNo = attachedToLineNo;
        this.description = description;
        this.lineNo = lineNo;
        this.price = price;
        this.productNo = productNo;
        this.size = size;
        this.userId = userId;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAttachedToLineNo() {
        return attachedToLineNo;
    }

    public void setAttachedToLineNo(Integer attachedToLineNo) {
        this.attachedToLineNo = attachedToLineNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

   public String getKey() {
      return Key;
   }

   public void setKey(String key) {
      Key = key;
   }

}
