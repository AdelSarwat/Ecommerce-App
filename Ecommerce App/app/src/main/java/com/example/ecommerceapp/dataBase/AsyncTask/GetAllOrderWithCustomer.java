package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Order;

import java.util.List;

public class GetAllOrderWithCustomer extends AsyncTask<Integer,Void, List<Order>> {

    private CustomerDao customerDao;

    public GetAllOrderWithCustomer(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }



    @Override
    protected List<Order> doInBackground(Integer... integers) {
        return customerDao.getAllOrdersWithCustomerID(integers[0]);
    }
}
