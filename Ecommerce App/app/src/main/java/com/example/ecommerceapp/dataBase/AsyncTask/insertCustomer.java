package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Customer;

public class insertCustomer extends AsyncTask<Customer,Void,Void> {

    private CustomerDao customerDao;

    public insertCustomer(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    protected Void doInBackground(Customer... customers) {
         customerDao.insertCustomer(customers[0]);
         return null;
    }
}
