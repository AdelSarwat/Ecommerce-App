package com.example.ecommerceapp.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity( indices = {@Index(value = {"ProID"}, unique = true)})
public class Product {

    @PrimaryKey(autoGenerate = true)
   public int ProID;

    @NonNull
    private String ProName;
    @NonNull
    private double Price;
    @NonNull
    private int Quantity;
    private String pro_img;

    @NonNull
    @ForeignKey(
            entity = Category.class,
            parentColumns = "CatID",
            childColumns = "Cat_ID",
            onDelete = CASCADE
    )
    private int Cat_ID;

    public String getPro_img() {
        return pro_img;
    }

    public void setPro_img(String pro_img) {
        this.pro_img = pro_img;
    }

    public Product() {

    }

    public int getProID() {
        return ProID;
    }


    @NonNull
    public String getProName() {
        return ProName;
    }

    public void setProName(@NonNull String proName) {
        ProName = proName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getCat_ID() {
        return Cat_ID;
    }

    public void setCat_ID(int cat_ID) {
        Cat_ID = cat_ID;
    }
}
