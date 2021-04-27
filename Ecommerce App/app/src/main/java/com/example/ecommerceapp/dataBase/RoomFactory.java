package com.example.ecommerceapp.dataBase;

import android.content.Context;

import com.example.ecommerceapp.dataBase.CustomerDataBase;

public class RoomFactory {
    private static CustomerDataBase customerDataBase;

    public static CustomerDataBase getNotesDatabase(Context context) {
        customerDataBase= CustomerDataBase.getInstance(context);
        return customerDataBase;
    }
}
