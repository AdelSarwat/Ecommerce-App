package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Category;
import com.example.ecommerceapp.models.Product;

public class GetProductWithID extends AsyncTask<Integer,Void, Product> {

    private CustomerDao customerDao;

    public GetProductWithID(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }




    @Override
    protected Product doInBackground(Integer... integers) {
        return customerDao.GetProductWithID(integers[0]);
    }
}
