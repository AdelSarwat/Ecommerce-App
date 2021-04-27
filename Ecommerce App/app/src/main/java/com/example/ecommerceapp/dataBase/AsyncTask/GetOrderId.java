package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;

public class GetOrderId extends AsyncTask<String,Void, Integer> {

    private CustomerDao customerDao;

    public GetOrderId(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    protected Integer doInBackground(String... strings) {
        return customerDao.GetOrderID(strings[0]);
    }
}
