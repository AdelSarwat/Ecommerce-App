package com.example.ecommerceapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.HomeActivity;
import com.example.ecommerceapp.activities.ItemViewActivity;
import com.example.ecommerceapp.activities.LoginActivity;

public class DashBoardFragment extends Fragment {

    private Button btn_laptop,btn_tv,btn_mobile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.dashboard_fragment,container,false);

        btn_laptop = root.findViewById(R.id.btn_laptop);
        btn_mobile = root.findViewById(R.id.btn_mobile);
        btn_tv = root.findViewById(R.id.btn_tv);

        btn_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToActivity("mobile");
            }
        });

        btn_laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToActivity("laptop");
            }
        });

        btn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToActivity("tv");
            }
        });
        return  root;
    }



    private void SendToActivity(String s) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ItemViewActivity.class);
        intent.putExtra("val",s);
        startActivity(intent);
    }
}
