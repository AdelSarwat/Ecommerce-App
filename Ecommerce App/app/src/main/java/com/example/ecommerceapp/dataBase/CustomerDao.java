package com.example.ecommerceapp.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.ecommerceapp.models.Category;
import com.example.ecommerceapp.models.Customer;
import com.example.ecommerceapp.models.Order;
import com.example.ecommerceapp.models.OrderDetails;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.models.ShoppingCard;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Dao
public interface CustomerDao {

    // Customer Function And quires
    @Transaction
    @Insert
    void insertCustomer(Customer customer);


    @Transaction
    @Insert
    void AddCategory(Category category);

    @Transaction
    @Insert
    void AddProduct(Product product);

    @Transaction
    @Insert
    void insertToCard(ShoppingCard shoppingCard);

    @Transaction
    @Insert
    void insertOrder(Order order);


    @Transaction
    @Query("SELECT * FROM `Order` WHERE OrderTime between :end AND :start ")
    List<Order> GraphORDERS(String start,String end);

    @Transaction
    @Query("delete from ShoppingCard")
    void deleteShoppingCard();

    @Transaction
    @Query("select OrdID from `Order` where OrderTime=:time")
    Integer GetOrderID(String time);

    @Transaction
    @Query("select Password from customer where Username=:username")
    String ForGetPassword(String username);

    @Transaction
    @Query("delete from ShoppingCard where Cust_ID=:CID and ProID =:PID")
    void DeleteProductFromShoppingCard(int CID,int PID );

    @Transaction
    @Query("select * from Product where ProID=:id")
    Product GetProductWithID(int id);

    @Transaction
    @Query("select * from product where Cat_ID=:id")
    List<Product> GetPRODUCTSWithCategory(int id);

    @Transaction
    @Query("select ProID from ShoppingCard where Cust_ID=:id")
    List<Integer> getAllProductInShoppingCard(int id);



    @Transaction
    @Query("Select * from Category where CatName=:name")
    Category getCategoryID(String name);

    @Transaction
    @Insert
          void OD  (OrderDetails ORDER_DETAILS);

    @Transaction
    @Query("Select * from Customer where Username=:username and Password =:password")
     Customer CheckLogin(String username , String password);


    @Transaction
    @Query("Select * from `Order` where Cust_ID=:id")
    List<Order> getAllOrdersWithCustomerID(int id);

    @Transaction
    @Query("Select * from `Order` where OrdID=:id")
    Order getOrderWithID(int id);


   // to get Shopping Card Item
    @Transaction
    @Query("SELECT * FROM `Order` where OrdID=:id")
    public List<OrderWithProduct> getOrderWithProducts(int id);

    @Transaction
    @Query("SELECT * FROM Product where ProID=:id")
    public List<ProductWithOrder> PRODUCT_WITH_ORDERS(int id);


}
