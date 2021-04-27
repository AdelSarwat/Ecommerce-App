package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Email.GMailSender;
import com.example.ecommerceapp.Email.Utils;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.dataBase.AsyncTask.DeleteFromShoppingCard;
import com.example.ecommerceapp.dataBase.AsyncTask.DeleteShoppingCard;
import com.example.ecommerceapp.dataBase.AsyncTask.GetAllOrderWithCustomer;
import com.example.ecommerceapp.dataBase.AsyncTask.GetOrderId;
import com.example.ecommerceapp.dataBase.AsyncTask.insertOD;
import com.example.ecommerceapp.dataBase.AsyncTask.insertOrder;
import com.example.ecommerceapp.dataBase.RoomFactory;
import com.example.ecommerceapp.fragment.ShoppingCardFragment;
import com.example.ecommerceapp.map.SearchLocationActivity;
import com.example.ecommerceapp.models.Order;
import com.example.ecommerceapp.models.OrderDetails;
import com.example.ecommerceapp.models.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ConfirmOrderActivity extends AppCompatActivity {

    private EditText txt_num;
    private TextView txt_time;
    private Button btn_confirm_order, btn_resend;
    String code;
    SharedPreferences sharedPref;
    HashMap<Product,Integer> hashMap;
    private int cid;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_confirm_order);
        txt_num = findViewById(R.id.txt_confirm_num);
        btn_confirm_order = findViewById(R.id.btn_confirm_order);
        btn_resend = findViewById(R.id.btn_resend);
        txt_time = findViewById(R.id.txt_counter);
        btn_resend.setEnabled(false);
        hashMap =ShoppingCardFragment.num_of_item;

        code= ShoppingCardFragment.Code;
        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        cid = sharedPref.getInt(getString(R.string.saved_user_ID),0);

        try {
            List<Order> orders =new GetAllOrderWithCustomer(RoomFactory.getNotesDatabase(this).getCustomerDAO()).execute(cid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CountDown();

        btn_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = txt_num.getText().toString();
                if (!value.isEmpty()) {
                    if (value.equals(code)){
                        SubmitOrder();
                        ShowMessage(" Order Make Successfully ");
                        SendToActivity();
                    }
                    else {
                        ShowMessage("Please Enter Valid Verification Code ");
                    }

                } else {
                    ShowMessage("Please Enter Verification Number");
                }
            }
        });



        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_resend.setEnabled(false);
                SendSMS();
                CountDown();
               // btn_resend.setEnabled(false);
            }
        });

    }

    private void SendToActivity() {

        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void SubmitOrder() {
        SaveOrder();
        SaveOrderDetails();
        DeleteItemsInShoppingCard();
    }

    private void DeleteItemsInShoppingCard() {
        new DeleteShoppingCard(RoomFactory.getNotesDatabase(ConfirmOrderActivity.this).getCustomerDAO()).execute();
    }

    private void SaveOrderDetails() {

        String Details="";

        OrderDetails orderDetails;
        int ORID=GetOrderID();
        for ( Map.Entry<Product, Integer> entry : hashMap.entrySet()) {
            Product key = entry.getKey();
            Integer q = entry.getValue();
            orderDetails = new OrderDetails();
            orderDetails.setProID(key.getProID());
            orderDetails.setQuantity(q);
            orderDetails.setOrdID(ORID);



            new insertOD(RoomFactory.getNotesDatabase(ConfirmOrderActivity.this).getCustomerDAO()).execute(orderDetails);

        }
           // ShowMessage("Order Detail Is Done");

    }

    private int GetOrderID(){

        int id=0;
        try {
             id= new GetOrderId(RoomFactory.getNotesDatabase(ConfirmOrderActivity.this).getCustomerDAO()).execute(time).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void SaveOrder() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        Order order = new Order();
        order.setAddress(SearchLocationActivity.loc.toString());
        order.setCust_ID(cid);
        order.setOrdDate(formattedDate);
        order.setOrderTime(c.toString());
        new insertOrder(RoomFactory.getNotesDatabase(ConfirmOrderActivity.this).getCustomerDAO()).execute(order);
        time=c.toString();
        int id = order.getOrdID();
        ShowMessage(" Order Done ");
    }


    private boolean SendSMS() {

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String receiver = sharedPref.getString(getString(R.string.saved_user_email), null);

        if (receiver == null) {
            ShowMessage("error");
            return false;
        } else {

            code = getRandomNumberString();
            String title = "Verification Code";
            String message = "Your Code is : " + code;
            sendEmail(Utils.EMAIL, Utils.PASSWORD, receiver, title, message);
            return true;
        }

    }


    void CountDown(){
        CountDownTimer countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {
                int sec= (int) (l/1000);
                String format= String.format("%02d:%02d",0,sec);
                txt_time.setText(format);
            }

            @Override
            public void onFinish() {
                btn_resend.setEnabled(true);
            }
        }.start();
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private void sendEmail(final String Sender, final String Password, final String Receiver, final String Title, final String Message) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Sender, Password);
                    sender.sendMail(Title, "<b>" + Message + "</b>", Sender, Receiver);
                    makeAlert();

                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                    ShowMessage("e.getMessage()");
                }

            }

        }).start();
    }


    private void makeAlert() {
        this.runOnUiThread(new Runnable() {
            public void run() {
                ShowMessage("Mail Sent");
            }
        });
    }

    void ShowMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}