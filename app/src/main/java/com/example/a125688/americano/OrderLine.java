package com.example.a125688.americano;

/**
 * Created by Tony on 4/7/2018.
 */

public class OrderLine {
   Integer ordeKey;
   Integer attachedToLineNo;
   String description;
   Integer lineNo;
   Double price;
   Integer productNo;
   String size;
   String userId;
   Integer quantity;

   public OrderLine() {
   }

   public OrderLine(Integer ordeKey, Integer attachedToLineNo, String description, Integer lineNo, Double price, Integer productNo, String size, String userId, Integer quantity) {
      this.ordeKey = ordeKey;
      this.attachedToLineNo = attachedToLineNo;
      this.description = description;
      this.lineNo = lineNo;
      this.price = price;
      this.productNo = productNo;
      this.size = size;
      this.userId = userId;
      this.quantity = quantity;
   }

   public Integer getOrdeKey() {
      return ordeKey;
   }

   public void setOrdeKey(Integer ordeKey) {
      this.ordeKey = ordeKey;
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

   public Integer getQuantity() {
      return quantity;
   }

   public void setQuantity(Integer quantity) {
      this.quantity = quantity;
   }
}
