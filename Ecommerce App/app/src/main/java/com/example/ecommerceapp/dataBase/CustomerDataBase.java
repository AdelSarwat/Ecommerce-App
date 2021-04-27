package com.example.ecommerceapp.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ecommerceapp.models.Category;
import com.example.ecommerceapp.models.Customer;
import com.example.ecommerceapp.models.Order;
import com.example.ecommerceapp.models.OrderDetails;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.models.ShoppingCard;

@Database(entities = {Customer.class, Order.class, Category.class, Product.class, OrderDetails.class, ShoppingCard.class}, version = 5, exportSchema = false)
public abstract class CustomerDataBase extends RoomDatabase {
    private static CustomerDataBase instance;

    public abstract CustomerDao getCustomerDAO();

    public static synchronized CustomerDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CustomerDataBase.class, "customers_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
