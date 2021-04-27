package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Customer;
import com.example.ecommerceapp.models.Order;

public class insertOrder extends AsyncTask<Order,Void,Void> {

    private CustomerDao customerDao;

    public insertOrder(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    protected Void doInBackground(Order... orders) {
        customerDao.insertOrder(orders[0]);
        return null;
    }
}
