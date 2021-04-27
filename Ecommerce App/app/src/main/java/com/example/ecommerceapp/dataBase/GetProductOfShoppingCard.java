package com.example.ecommerceapp.dataBase;

import android.os.AsyncTask;

import com.example.ecommerceapp.models.Category;

import java.util.List;

public class GetProductOfShoppingCard extends AsyncTask<Integer,Void, List<Integer>> {

    private CustomerDao customerDao;

    public GetProductOfShoppingCard(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    protected List<Integer> doInBackground(Integer... integers) {
        return customerDao.getAllProductInShoppingCard(integers[0]);
    }
}
