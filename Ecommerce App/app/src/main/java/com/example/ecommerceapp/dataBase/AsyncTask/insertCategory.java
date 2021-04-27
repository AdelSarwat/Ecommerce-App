package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Category;
import com.example.ecommerceapp.models.Customer;

public class insertCategory extends AsyncTask<Category,Void,Void> {

    private CustomerDao customerDao;

    public insertCategory(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    protected Void doInBackground(Category... categories) {
        customerDao.AddCategory(categories[0]);
        return null;
    }
}
