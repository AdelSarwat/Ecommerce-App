package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Order;

import java.util.List;

public class GetOrderForGraph extends AsyncTask<String,Void, List<Order>> {

    private CustomerDao customerDao;

    public GetOrderForGraph(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    protected List<Order> doInBackground(String... strings) {
        return customerDao.GraphORDERS(strings[0],strings[1]);
    }
}
