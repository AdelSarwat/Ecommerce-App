package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Category;
import com.example.ecommerceapp.models.Customer;

public class GetCategoryID extends AsyncTask<String,Void, Category> {

    private CustomerDao customerDao;

    public GetCategoryID(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    protected Category doInBackground(String... strings) {

        return customerDao.getCategoryID(strings[0]);
    }
}
