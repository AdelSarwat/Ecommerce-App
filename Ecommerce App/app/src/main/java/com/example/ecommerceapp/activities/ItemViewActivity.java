package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.ecommerceapp.CaptureAct;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RVAdapter;
import com.example.ecommerceapp.dataBase.AsyncTask.CheckCustomerLogin;
import com.example.ecommerceapp.dataBase.AsyncTask.GetCategoryID;
import com.example.ecommerceapp.dataBase.AsyncTask.GetProductsWithCategory;
import com.example.ecommerceapp.dataBase.AsyncTask.insertCustomer;
import com.example.ecommerceapp.dataBase.AsyncTask.insertToCard;
import com.example.ecommerceapp.dataBase.RoomFactory;
import com.example.ecommerceapp.fragment.ShoppingCardFragment;
import com.example.ecommerceapp.models.Category;
import com.example.ecommerceapp.models.Customer;
import com.example.ecommerceapp.models.OnAddToCardClick;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.models.ShoppingCard;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ItemViewActivity extends AppCompatActivity implements OnAddToCardClick {

    private Category category;
    private RecyclerView recyclerView;
    private EditText txt_search;
    private List<Product> products;
    public static HashMap<Product,Integer> ShoppingWithQuantity;
    private RVAdapter adapter;
    private ImageView voice_search, qr_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        txt_search = findViewById(R.id.txt_search_pro);
        voice_search = findViewById(R.id.img_voice);
        qr_search = findViewById(R.id.img_scan);
        ShoppingWithQuantity=new HashMap<>();

        voice_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, 1);

            }
        });

        qr_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanCode();

            }
        });

        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        GetCategory();
        try {

            products = new GetProductsWithCategory(RoomFactory.getNotesDatabase(ItemViewActivity.this).getCustomerDAO()).execute(category.getCatID()).get();

            if (products.size() != 0) {


                recyclerView = findViewById(R.id.RV);
                adapter = new RVAdapter(products, this, this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            } else {
                ShowMessage("Category is Empty");
                finish();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void ScanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code ..");
        integrator.initiateScan();
    }

    private void filter(String txt) {
        ArrayList<Product> filteredList = new ArrayList<>();
        for (Product product : products) {
            if (product.getProName().toLowerCase().contains(txt.toLowerCase())) {
                filteredList.add(product);
            }
        }
        adapter.filterList(filteredList);

    }

    private void ShowMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void GetCategory() {
        String value = getIntent().getExtras().getString("val");
        if (value.equals("tv")) {
            try {
                category = new GetCategoryID(RoomFactory.getNotesDatabase(ItemViewActivity.this).getCustomerDAO()).execute("Tv").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else if (value.equals("laptop")) {
            try {
                category = new GetCategoryID(RoomFactory.getNotesDatabase(ItemViewActivity.this).getCustomerDAO()).execute("Laptops").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else if (value.equals("mobile")) {
            try {
                category = new GetCategoryID(RoomFactory.getNotesDatabase(ItemViewActivity.this).getCustomerDAO()).execute("Mobiles").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(Product product) {
        ShoppingCard shoppingCard = new ShoppingCard();
        shoppingCard.setProID(product.getProID());
        //ShowMessage(SplashActivity.CID+"");
        shoppingCard.setCust_ID(SplashActivity.CID);


        new insertToCard(RoomFactory.getNotesDatabase(ItemViewActivity.this).getCustomerDAO()).execute(shoppingCard);

        ShoppingWithQuantity.put(product,1);

        //  products.add(product);
        ShowMessage("Added Successfully");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (arrayList.size() != 0) {
                txt_search.setText(arrayList.get(0));

            }
        } else {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                if (intentResult.getContents() == null) {
                    ShowMessage("Cancelled");
                } else {
                    txt_search.setText(intentResult.getContents());
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);

            }
        }

    }


}