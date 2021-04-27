package com.example.ecommerceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.models.OnAddToCardClick;
import com.example.ecommerceapp.models.Product;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyVewHolder> {

    private List<Product> products;
    private Context context;
    private OnAddToCardClick cardClick;

    public RVAdapter(List<Product> products, Context context, OnAddToCardClick cardClick) {
        this.products = products;
        this.context = context;
        this.cardClick = cardClick;
    }

    @NonNull
    @Override
    public MyVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new MyVewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVewHolder holder, int position) {
        holder.ProductName.setText(products.get(position).getProName());
        holder.ImageTvShow.setImageResource(Integer.parseInt(products.get(position).getPro_img()));
        holder.ProductPrice.setText("Price " + products.get(position).getPrice() + "$");
        if (products.get(position).getQuantity() == 0) {
            holder.AddCard.setEnabled(false);
        }
        holder.AddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardClick.onClick(products.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyVewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        View ViewBackground;
        RoundedImageView ImageTvShow;
        TextView ProductName, ProductPrice;
        Button AddCard;
        ImageView imageSelected;

        public MyVewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layoutTvShow);
            ViewBackground = itemView.findViewById(R.id.viewBackGround);
            ImageTvShow = itemView.findViewById(R.id.Card_imageTvShow);
            ProductName = itemView.findViewById(R.id.Card_proName);
            imageSelected = itemView.findViewById(R.id.imageSelected);
            ProductPrice = itemView.findViewById(R.id.Card_proPrice);
            AddCard = itemView.findViewById(R.id.add_to_card_btn);

        }
    }

    public void filterList(ArrayList<Product> arrayList) {
        products = arrayList;
        notifyDataSetChanged();
    }
}
