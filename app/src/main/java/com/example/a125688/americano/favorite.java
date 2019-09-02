package com.example.a125688.americano;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Tony on 4/6/2018.
 */
@IgnoreExtraProperties
public class favorite {
   Integer productNo;
   String description;
   String userId;
   @Exclude
   private String Key;

   public favorite() {
   }

   public favorite(Integer productNo, String description) {
      this.productNo = productNo;
      this.description = description;
   }

   public Integer getProductNo() {
      return productNo;
   }

   public void setProductNo(Integer productNo) {
      this.productNo = productNo;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getKey() {
      return Key;
   }

   public void setKey(String key) {
      Key = key;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }
}
