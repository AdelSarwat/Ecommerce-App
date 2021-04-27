package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Order;

public class DeleteShoppingCard extends AsyncTask<Void,Void,Void> {

    private CustomerDao customerDao;

    public DeleteShoppingCard(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        customerDao.deleteShoppingCard();
        return null;
    }
}
