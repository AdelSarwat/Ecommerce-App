package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Customer;
import com.example.ecommerceapp.models.Product;

public class insertProduct extends AsyncTask<Product,Void,Void> {

    private CustomerDao customerDao;

    public insertProduct(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }



    @Override
    protected Void doInBackground(Product... products) {
        customerDao.AddProduct(products[0]);
        return null;
    }
}
