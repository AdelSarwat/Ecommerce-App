package com.example.ecommerceapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int OrdID;
    @NonNull
    private String OrdDate;
    @NonNull
    private String Address;

    @NonNull
    private String OrderTime;

    @NonNull
    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(@NonNull String orderTime) {
        OrderTime = orderTime;
    }

    @ForeignKey(
            entity = Customer.class,
            parentColumns = "CustID",
            childColumns = "Cust_ID",
            onDelete = CASCADE
    )
    private int Cust_ID;

    public Order() {
    }

    public int getOrdID() {
        return OrdID;
    }

    @NonNull
    public String getOrdDate() {
        return OrdDate;
    }

    public void setOrdDate(@NonNull String ordDate) {
        OrdDate = ordDate;
    }

    @NonNull
    public String getAddress() {
        return Address;
    }

    public void setAddress(@NonNull String address) {
        Address = address;
    }

    public int getCust_ID() {
        return Cust_ID;
    }

    public void setCust_ID(int cust_ID) {
        Cust_ID = cust_ID;
    }
}
