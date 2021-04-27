package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Customer;

public class CheckCustomerLogin extends AsyncTask<String,Void, Customer> {
private CustomerDao customerDao;

    public CheckCustomerLogin(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    protected Customer doInBackground(String... strings) {
        return customerDao.CheckLogin(strings[0],strings[1]);
    }
}
