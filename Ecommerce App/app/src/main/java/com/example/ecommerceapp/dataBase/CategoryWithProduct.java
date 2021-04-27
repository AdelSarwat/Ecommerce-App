package com.example.ecommerceapp.dataBase;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.ecommerceapp.models.Category;
import com.example.ecommerceapp.models.Product;

import java.util.List;

public class CategoryWithProduct {
    @Embedded
    public Category category;

    @Relation(
            parentColumn = "CatID",
            entityColumn = "Cat_ID"
    )
    public List<Product> products;

    public CategoryWithProduct(Category category, List<Product> products) {
        this.category = category;
        this.products = products;
    }
}
