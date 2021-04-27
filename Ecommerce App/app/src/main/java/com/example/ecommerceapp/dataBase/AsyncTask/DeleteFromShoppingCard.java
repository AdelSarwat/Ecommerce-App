package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Category;

public class DeleteFromShoppingCard extends AsyncTask<Integer,Void, Void> {

    private CustomerDao customerDao;

    public DeleteFromShoppingCard(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }




    @Override
    protected Void doInBackground(Integer... integers) {
        customerDao.DeleteProductFromShoppingCard(integers[0],integers[1]);
        return null;
    }
}
