package com.example.ecommerceapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Email.GMailSender;
import com.example.ecommerceapp.Email.Utils;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ShoppingCardAdapter;
import com.example.ecommerceapp.activities.ConfirmOrderActivity;
import com.example.ecommerceapp.activities.ForgetPasswordActivity;
import com.example.ecommerceapp.activities.ItemViewActivity;
import com.example.ecommerceapp.activities.SplashActivity;
import com.example.ecommerceapp.dataBase.AsyncTask.DeleteFromShoppingCard;
import com.example.ecommerceapp.dataBase.AsyncTask.GetProductWithID;
import com.example.ecommerceapp.dataBase.GetProductOfShoppingCard;
import com.example.ecommerceapp.dataBase.RoomFactory;
import com.example.ecommerceapp.map.SearchLocationActivity;
import com.example.ecommerceapp.models.CalcluteQuantityInterface;
import com.example.ecommerceapp.models.IncreaseBtn;
import com.example.ecommerceapp.models.OnAddToCardClick;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.models.decreaseBtn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class ShoppingCardFragment extends Fragment implements CalcluteQuantityInterface, IncreaseBtn, decreaseBtn {
    public static List<Product> products;
    private FloatingActionButton floatingActionButton;
    private TextView txt_total;
    private Button btn_Make_Order;

    public static double total;
    public static String Code;

    // list of number of single item
    public static HashMap<Product, Integer> num_of_item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.shopping_card_fragment, container, false);


        total = 0;
        btn_Make_Order = root.findViewById(R.id.btn_make_order);
        txt_total = root.findViewById(R.id.txt_total);
        num_of_item = new HashMap<>();
        products = new ArrayList<>();

        btn_Make_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (products.size() == 0) {
                    ShowMessage("Please add product to Card..");
                } else if (SearchLocationActivity.loc == null) {
                    ShowMessage("Please Choose Location To Deliver");
                } else {

                    boolean check = SendSMS();
                    if (check == true) {
                        SendTOActivity(ConfirmOrderActivity.class);
                    } else {
                        ShowMessage("Error: Invalid Mail ");
                    }
                }
            }
        });


        try {
            List<Integer> integers = new GetProductOfShoppingCard(RoomFactory.getNotesDatabase(getContext()).getCustomerDAO()).execute(SplashActivity.CID).get();
            for (int i = 0; i < integers.size(); i++) {
                Product p = new GetProductWithID(RoomFactory.getNotesDatabase(getContext()).getCustomerDAO()).execute(integers.get(i)).get();
                products.add(p);
                num_of_item.put(p, 1);
                total += p.getPrice();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        floatingActionButton = root.findViewById(R.id.btn_choose_location);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendTOActivity(SearchLocationActivity.class);
            }
        });


        SetPrice(total);
        RecyclerView recyclerView = root.findViewById(R.id.Card_RV);
        ShoppingCardAdapter adapter = new ShoppingCardAdapter(products, getContext(), this, this, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return root;

    }

    private boolean SendSMS() {

        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String receiver = sharedPref.getString(getString(R.string.saved_user_email), null);

        if (receiver == null) {
            ShowMessage("error");
            return false;
        } else {

            Code = getRandomNumberString();
            String title = "Verification Code";
            String message = "Your Code is : " + Code;
            sendEmail(Utils.EMAIL, Utils.PASSWORD, receiver, title, message);
            return true;
        }

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
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                ShowMessage("Mail Sent");
            }
        });
    }

    private void SendTOActivity(Class c) {
        Intent intent = new Intent(getContext(), c);
        startActivity(intent);
    }

    public void SetPrice(double d) {
        txt_total.setText(String.valueOf(d) + " $");

    }

    /*
        @Override
        public void onClick(Product product) {
            //Delete Item From Rv and DataBase
            new DeleteFromShoppingCard(RoomFactory.getNotesDatabase(getContext()).getCustomerDAO()).execute(SplashActivity.CID, product.getProID());
            total -= product.getPrice();
            //SetPrice(total*quantity);
            ShowMessage("Deleted Successfully");
        }
    */
    private void ShowMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(Product product) {

        //Delete Item From Rv and DataBase
        new DeleteFromShoppingCard(RoomFactory.getNotesDatabase(getContext()).getCustomerDAO()).execute(SplashActivity.CID, product.getProID());
        num_of_item.remove(product);
        total -= product.getPrice();
        SetPrice(total);
        ShowMessage("Deleted Successfully");
    }

    @Override
    public void Increase_btn(Product product, Integer quantity) {
        total += product.getPrice();
        SetPrice(total);
        num_of_item.replace(product, quantity);
    }

    @Override
    public void decrease_btn(Product product, Integer q) {
        total -= product.getPrice();
        SetPrice(total);
        num_of_item.replace(product, q);
    }
}
