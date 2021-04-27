package com.example.ecommerceapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int CatID;
    @NonNull
    private String CatName;

    public Category() {
    }

    public int getCatID() {
        return CatID;
    }

    @NonNull
    public String getCatName() {
        return CatName;
    }

    public void setCatName(@NonNull String catName) {
        CatName = catName;
    }
}
