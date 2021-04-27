package com.example.ecommerceapp.dataBase;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.ecommerceapp.models.Order;
import com.example.ecommerceapp.models.OrderDetails;
import com.example.ecommerceapp.models.Product;

import java.util.List;

public class OrderWithProduct {
    @Embedded
    public Order order;
    @Relation(
            parentColumn = "OrdID",
            entityColumn = "ProID",
            associateBy = @Junction(OrderDetails.class)
    )
    public List<Product> products;
}
