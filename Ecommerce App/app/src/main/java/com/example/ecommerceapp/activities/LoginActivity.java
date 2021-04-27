package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.dataBase.AsyncTask.CheckCustomerLogin;
import com.example.ecommerceapp.dataBase.RoomFactory;
import com.example.ecommerceapp.models.Customer;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText email,password;
    private TextView forget,singUp;
    private CheckBox rememberMe;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = getApplicationContext().getSharedPreferences( getString(R.string.preference_file_key),MODE_PRIVATE);


        initializeFlied();

        String user_email=email.getText().toString();
        String user_password=password.getText().toString();



        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToSignUpActivity();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_email=email.getText().toString();
                String user_password=password.getText().toString();

                if (!(email.getText().toString().equals("")&& password.getText().toString().equals(""))){
                    //CheckAdmin(user_email,user_password);

                    try {
                        Customer customer = new CheckCustomerLogin(RoomFactory.getNotesDatabase(LoginActivity.this).getCustomerDAO()).execute(email.getText().toString(),password.getText().toString()).get();
                        if (customer != null){
                            if (rememberMe.isChecked()){
                                SavedInSharedPreferences(customer,true);
                            }
                            else {
                                SavedInSharedPreferences(customer,false);
                            }
                            SendToHomeActivity();
                        }
                        else {
                         ShowMessage("Invalid Email or Password ");
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void CheckAdmin(String email, String password) {

        if (email.equals("admin") && password.equals("admin")){
            SendToAdminHome();
        }
    }

    private void SendToAdminHome() {

    }

    private void SavedInSharedPreferences(Customer customer,boolean b) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_user_ID), customer.getCustID());
        editor.putString(getString(R.string.saved_user_email), customer.getUsername());
        editor.putString(getString(R.string.saved_user_password), customer.getPassword());
        editor.putString(getString(R.string.saved_user_img), customer.getImg_path());
        editor.putBoolean(getString(R.string.saved_user_boolean), b);
        editor.apply();
    }



    private void SendToSignUpActivity() {
        Intent intent= new Intent(LoginActivity.this,SignUpActivity.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //finish();
    }

    private void initializeFlied() {
        login_btn = findViewById(R.id.btn_login);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        forget = findViewById(R.id.login_forgetPassword);
        singUp = findViewById(R.id.login_new_account);
        rememberMe = findViewById(R.id.login_remember_me);
    }

    private void SendToHomeActivity() {
        Intent intent= new Intent(LoginActivity.this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void ShowMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}