package com.example.ecommerceapp.activities;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapter.DrawerAdapter;
import com.example.ecommerceapp.adapter.DrawerItem;
import com.example.ecommerceapp.adapter.SimpleItem;
import com.example.ecommerceapp.adapter.SpaceItem;
import com.example.ecommerceapp.fragment.DashBoardFragment;
import com.example.ecommerceapp.fragment.ShoppingCardFragment;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_NEARBY_RES = 3;
    private static final int POS_SETTING = 4;
    private static final int POS_ABOUT_US = 5;
    private static final int POS_LOGOUT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setTitle(" DashBoard ");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitle();
        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_MY_PROFILE),
                createItemFor(POS_SETTING),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(300),
                createItemFor(POS_LOGOUT)
        ));
        adapter.SetListener(this);
        RecyclerView list = findViewById(R.id.drawer_RV);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.purple_500))
                .withTitleTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.purple_500))
                .withSelectedTileTint(color(R.color.purple_500));
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private String[] loadScreenTitle() {
        return getResources().getStringArray(R.array.id_activityScreenTitle);
    }


    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position == POS_DASHBOARD) {
            this.setTitle(" DashBoard ");
            DashBoardFragment dashBoardFragment = new DashBoardFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        } else if (position == POS_MY_PROFILE) {
            this.setTitle("Shopping Card ");
            Intent intent = new Intent(HomeActivity.this, GraphActivity.class);
            startActivity(intent);
            //Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
        } else if (position == (POS_SETTING - 1)) {
            ShoppingCardFragment shoppingCardFragment = new ShoppingCardFragment();
            transaction.replace(R.id.container, shoppingCardFragment);

        } else if (position == POS_LOGOUT - 1) {

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.saved_user_boolean), false);
            editor.apply();

            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        this.setTitle("Shopping Card ");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ShoppingCardFragment shoppingCardFragment = new ShoppingCardFragment();
        transaction.replace(R.id.container, shoppingCardFragment);
       // transaction.addToBackStack(null);
        transaction.commit();
        return super.onOptionsItemSelected(item);
    }
}