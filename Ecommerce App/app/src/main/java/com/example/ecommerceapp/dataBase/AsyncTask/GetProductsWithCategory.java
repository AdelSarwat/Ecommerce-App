package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Product;

import java.util.List;

public class GetProductsWithCategory extends AsyncTask<Integer,Void, List<Product>> {

    private CustomerDao customerDao;

    public GetProductsWithCategory(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    protected List<Product> doInBackground(Integer... integers) {
        return customerDao.GetPRODUCTSWithCategory(integers[0]);
    }
}
