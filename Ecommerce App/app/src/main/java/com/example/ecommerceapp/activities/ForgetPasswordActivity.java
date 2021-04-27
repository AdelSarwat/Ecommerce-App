package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.Email.GMailSender;

import com.example.ecommerceapp.Email.Utils;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.dataBase.AsyncTask.GetPassword;
import com.example.ecommerceapp.dataBase.RoomFactory;

import java.util.concurrent.ExecutionException;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button button;
    EditText txt_forget;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setTitle("Forget Password ");

        setContentView(R.layout.activity_forget_password);
        button = findViewById(R.id.btn_send_mail);

        txt_forget = findViewById(R.id.txt_forget);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sender = Utils.EMAIL;
                String senderPass = Utils.PASSWORD;
                String receiver = txt_forget.getText().toString();

                if (!receiver.isEmpty()) {

                    String pass = GetPasswordForUser(receiver);
                    if (pass != null) {

                        String title = "Send Password";
                        String message = "Your Password is : "+pass;
                        dialog = ProgressDialog.show(ForgetPasswordActivity.this, "",
                                "Sending Email. Please wait...", true);

                        dialog.show();
                        sendEmail(sender, senderPass, receiver, title, message);
                    }
                    else {
                        ShowMessage("this Email Not Found,please Add Right one ");
                    }
                } else {
                    ShowMessage("Please Add Year Email ");
                }
            }
        });

    }

    private String GetPasswordForUser(String receiver) {
        String pass = null;
        try {
            pass = new GetPassword(RoomFactory.getNotesDatabase(this).getCustomerDAO()).execute(receiver).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pass;
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
                dialog.dismiss();
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


    private void ShowMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}