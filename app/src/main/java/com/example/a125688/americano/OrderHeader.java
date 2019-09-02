package com.example.a125688.americano;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Tony on 4/5/2018.
 */
@IgnoreExtraProperties
public class OrderHeader {
   String userId;
   Integer orderNo;
   Integer status; // 1 is pending 2 is completed
   String printedName;
   String orderDate;
   String orderTime;
   Double orderTotalAmount;
   @Exclude
   private String Key;

   public OrderHeader() {
   }

   public OrderHeader( String userId, Integer orderNo, Integer status, String printedName, String orderDate, String orderTime) {
      this.userId = userId;
      this.orderNo = orderNo;
      this.status = status;
      this.printedName = printedName;
      this.orderDate = orderDate;
      this.orderTime = orderTime;
   }
   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public String getPrintedName() {
      return printedName;
   }

   public void setPrintedName(String printedName) {
      this.printedName = printedName;
   }

   public String getOrderDate() {
      return orderDate;
   }

   public void setOrderDate(String orderDate) {
      this.orderDate = orderDate;
   }

   public String getOrderTime() {
      return orderTime;
   }

   public void setOrderTime(String orderTime) {
      this.orderTime = orderTime;
   }

   public String getKey() {
      return Key;
   }

   public void setKey(String key) {
      Key = key;
   }

   public Integer getOrderNo() {
      return orderNo;
   }

   public void setOrderNo(Integer orderNo) {
      this.orderNo = orderNo;
   }

   public Double getOrderTotalAmount() {
      return orderTotalAmount;
   }

   public void setOrderTotalAmount(Double orderTotalAmount) {
      this.orderTotalAmount = orderTotalAmount;
   }
}

