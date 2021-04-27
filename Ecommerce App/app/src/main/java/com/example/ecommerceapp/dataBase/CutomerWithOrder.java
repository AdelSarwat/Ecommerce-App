package com.example.ecommerceapp.dataBase;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.ecommerceapp.models.Customer;
import com.example.ecommerceapp.models.Order;

import java.util.List;

public class CutomerWithOrder {
    @Embedded
    private Customer customer;
    @Relation(
            parentColumn = "CustID",
            entityColumn = "Cust_ID"
    )
    public List<Order> orders;

    public CutomerWithOrder(Customer customer, List<Order> orders) {
        this.customer = customer;
        this.orders = orders;
    }
}
