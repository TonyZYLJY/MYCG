package com.example.a125688.americano;

/**
 * Created by Tony on 4/9/2018.
 */

public class Transaction {
   Integer ordeNo;
   String userId;
   Double totalAmount;
   String date;

   public Transaction() {
   }

   public Transaction(Integer ordeNo, String userId, Double totalAmount, String date) {
      this.ordeNo = ordeNo;
      this.userId = userId;
      this.totalAmount = totalAmount;
      this.date = date;
   }

   public Integer getOrdeNo() {
      return ordeNo;
   }

   public void setOrdeNo(Integer ordeNo) {
      this.ordeNo = ordeNo;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public Double getAmount() {
      return totalAmount;
   }

   public void setAmount(Double totalAmount) {
      this.totalAmount = totalAmount;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }
}
