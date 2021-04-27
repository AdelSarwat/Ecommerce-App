package com.example.ecommerceapp.activities;

import com.example.ecommerceapp.dataBase.AsyncTask.GetCategoryID;
import com.example.ecommerceapp.dataBase.AsyncTask.insertCategory;
import com.example.ecommerceapp.dataBase.AsyncTask.insertCustomer;
import com.example.ecommerceapp.dataBase.AsyncTask.insertProduct;
import com.example.ecommerceapp.dataBase.RoomFactory;
import com.example.ecommerceapp.models.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.dataBase.CutomerWithOrder;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private CircleImageView ImgUserPhoto;
    private Spinner spinner;
    private ProgressBar progressBar;
    private Button btn_register;
    private EditText User_Name, User_password, User_Email, User_Job;
    private TextView User_Birthday;
    private String BirthDay;
    private Uri PackedImgUri;
    static int PReqCode;
    static int PREQCODE;
    boolean checkTakeImage;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeFlied();

        boolean check = sharedPref.getBoolean(getString(R.string.saved_databse), false);

        if (check == false) {
            AddDataBase();
        }


        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22) {
                    CheckAndRequestForPermission();
                } else {
                    OpenGallery();
                }
            }
        });
        User_Birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePackerDialog();

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name, gender, job, email, password;
                name = User_Name.getText().toString();
                job = User_Job.getText().toString();
                email = User_Email.getText().toString();
                password = User_password.getText().toString();
                gender = spinner.getSelectedItem().toString();

                if (checkTakeImage != true) {
                    ShowMassage("Please Choose a photo, please");
                } else if (name.isEmpty() || job.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    ShowMassage("Please Add All Flied ");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    Customer customer = new Customer();
                    customer.setImg_path(PackedImgUri.getPath());
                    customer.setCutName(name);
                    customer.setBirthdate(BirthDay);
                    customer.setGender(gender);
                    customer.setUsername(email);
                    customer.setPassword(password);
                    customer.setJob(job);

                    new insertCustomer(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute(customer);
                    ShowMassage("Register successfully");
                    SendToLoginActivity();
                }


            }
        });

    }

    private void AddDataBase() {
        AddCategories();
        AddProducts();
        AddShoppingCard();
    }

    private void AddShoppingCard() {
    }

    private void AddProducts() {
        Category Mobiles = null;
        Category Laptops = null;
        Category Tv = null;
        try {
            Mobiles = new GetCategoryID(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute("Mobiles").get();
            Laptops = new GetCategoryID(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute("Laptops").get();
            Tv = new GetCategoryID(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute("Tv").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AddMobilesProduct(Mobiles);
        AddTvProducts(Tv);
    }

    private void AddMobilesProduct(Category Mobile) {

        for (int i = 1; i < 5; i++) {
            Product p1 = new Product();
            p1.setCat_ID(Mobile.getCatID());
            p1.setPrice((5000*i)/2);
            p1.setProName("Redmi Note 8 ");
            p1.setQuantity(5+i);
            p1.setPro_img(String.valueOf(R.drawable.redmi_note_8));
            new insertProduct(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute(p1);

            Product p2 = new Product();
            p2.setCat_ID(Mobile.getCatID());
            p2.setPrice(3000*i);
            p2.setProName("Redmi Note 7 plus");
            p2.setQuantity(5+i);
            p2.setPro_img(String.valueOf(R.drawable.iphone));
            new insertProduct(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute(p2);

            Product p3 = new Product();
            p3.setCat_ID(Mobile.getCatID());
            p3.setPrice(5000);
            p3.setProName("iphone 12 ");
            p3.setQuantity(5);
            p3.setPro_img(String.valueOf(R.drawable.iphone2));
            new insertProduct(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute(p3);

        }
    }

    private void AddTvProducts(Category tv) {
        for (int i=1 ;i<8;i++) {
            Product p1 = new Product();
            p1.setCat_ID(tv.getCatID());
            p1.setPrice((8000*i)/2);
            p1.setProName("Lg tv 59 inch ");
            p1.setQuantity((5+i));
            p1.setPro_img((String.valueOf(R.drawable.tv)));
            new insertProduct(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute(p1);
        }
    }


    private void AddCategories() {

        Category c = new Category();
        c.setCatName("Mobiles");
        new insertCategory(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute(c);
        Category c2 = new Category();
        c2.setCatName("Laptops");
        new insertCategory(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute(c2);
        Category c3 = new Category();
        c3.setCatName("Tv");
        new insertCategory(RoomFactory.getNotesDatabase(SignUpActivity.this).getCustomerDAO()).execute(c3);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.saved_databse), true);
        editor.apply();
    }

    private void SendToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        progressBar.setVisibility(View.GONE);
        finish();

    }


    private void initializeFlied() {

        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        User_Birthday = findViewById(R.id.user_brithday);
        ImgUserPhoto = findViewById(R.id.user_img);
        User_Name = findViewById(R.id.user_name);
        User_Job = findViewById(R.id.user_job);
        User_password = findViewById(R.id.user_password);
        User_Email = findViewById(R.id.user_email);
        spinner = findViewById(R.id.user_spinner_gender);
        progressBar = findViewById(R.id.pro_register);
        btn_register = findViewById(R.id.btn_register);
        progressBar.setVisibility(View.INVISIBLE);

        PReqCode = 1;
        PREQCODE = 1;
        checkTakeImage = false;

    }

    private void CheckAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ShowMassage("Please Accept For Required Permission");
            } else {

                ActivityCompat.requestPermissions(SignUpActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            OpenGallery();
        }
    }

    private void ShowMassage(String s) {

        Toast.makeText(SignUpActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void OpenGallery() {
        Intent GalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent, PREQCODE);

    }

    private void ShowDatePackerDialog() {

        DatePickerDialog dialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        BirthDay = i2 + "/" + (i1 + 1) + "/" + i;
        User_Birthday.setText(BirthDay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PREQCODE && data != null) {
            PackedImgUri = data.getData();
            ImgUserPhoto.setImageURI(PackedImgUri);
            checkTakeImage = true;
        }

    }
}