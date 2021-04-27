package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.activities.SplashActivity;
import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Category;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.models.ShoppingCard;

public class insertToCard extends AsyncTask<ShoppingCard,Void,Void> {

    private CustomerDao customerDao;

    public insertToCard(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    protected Void doInBackground(ShoppingCard... shoppingCards) {
       customerDao.insertToCard(shoppingCards[0]);
        return null;
    }
}
