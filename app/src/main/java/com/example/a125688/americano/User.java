package com.example.a125688.americano;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tony on 2/9/2018.
 */

public class User implements Parcelable{
    String userId;
    String name;
    String password;
    Double accountAmount;
    Integer rewards;

    public User(String name, String password, String userId, Double accountAmount, Integer rewards) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.accountAmount = accountAmount;
        this.rewards = rewards;
    }

    public User(){
        super();
    }

    public User(Parcel in) {
        this.userId = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.accountAmount = in.readDouble();
        this.rewards = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel user, int flags) {
        user.writeString(userId);
        user.writeString(name);
        user.writeString(password);
        user.writeDouble(accountAmount);
        user.writeInt(rewards);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {return userId;}

    public  Double getAccountAmount() {return accountAmount;}

    public Integer getRewards() {
        return rewards;
    }

    public void setAccountAmount(Double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public void setRewards(Integer rewards) {
        this.rewards = rewards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {this.userId = userId;}
}
