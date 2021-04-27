package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Category;

public class GetPassword extends AsyncTask<String,Void, String> {

    private CustomerDao customerDao;

    public GetPassword(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    protected String doInBackground(String... strings) {
        return customerDao.ForGetPassword(strings[0]);
    }
}
