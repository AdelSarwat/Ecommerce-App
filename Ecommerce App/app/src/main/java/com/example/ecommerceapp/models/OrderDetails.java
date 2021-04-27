package com.example.ecommerceapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"OrdID","ProID"},foreignKeys = {
        @ForeignKey(
                entity =Order.class,
                parentColumns = "OrdID",
                childColumns = "OrdID",
                onDelete =CASCADE
        ),
        @ForeignKey(
                entity =Product.class,
                parentColumns = "ProID",
                childColumns = "ProID",
                onDelete =CASCADE
        ),
})

public class OrderDetails {

    public int OrdID;
    public int ProID;
    private int Quantity;


    public OrderDetails() {
    }

    public int getOrdID() {
        return OrdID;
    }

    public void setOrdID(int ordID) {
        OrdID = ordID;
    }

    public int getProID() {
        return ProID;
    }

    public void setProID(int proID) {
        ProID = proID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
