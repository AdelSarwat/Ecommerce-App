package com.example.ecommerceapp.dataBase.AsyncTask;

import android.os.AsyncTask;

import com.example.ecommerceapp.dataBase.CustomerDao;
import com.example.ecommerceapp.models.Order;
import com.example.ecommerceapp.models.OrderDetails;

public class insertOD extends AsyncTask<OrderDetails,Void,Void> {

    private CustomerDao customerDao;

    public insertOD(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }




    @Override
    protected Void doInBackground(OrderDetails... orderDetails) {
        customerDao.OD(orderDetails[0]);
        return null;
    }
}
