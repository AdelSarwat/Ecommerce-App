package com.example.ecommerceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.activities.ItemViewActivity;
import com.example.ecommerceapp.models.CalcluteQuantityInterface;
import com.example.ecommerceapp.models.IncreaseBtn;
import com.example.ecommerceapp.models.OnAddToCardClick;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.models.decreaseBtn;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCardAdapter extends RecyclerView.Adapter<ShoppingCardAdapter.MyViewHolder> {

    List<Product> products;
    Context context;
    private CalcluteQuantityInterface onClick;
    private IncreaseBtn increaseBtn;
    private decreaseBtn decreaseBtn;

    public ShoppingCardAdapter(List<Product> products, Context context, CalcluteQuantityInterface cardClick, IncreaseBtn increaseBtn, decreaseBtn decreaseBtn) {
        this.products = products;
        this.context = context;
        this.onClick = cardClick;
        this.increaseBtn=increaseBtn;
        this.decreaseBtn=decreaseBtn;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_view_shopping_card, parent, false);
        return new ShoppingCardAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.Card_ProductName.setText(products.get(position).getProName());
        holder.Card_imageTvShow.setImageResource(Integer.parseInt(products.get(position).getPro_img()));
        holder.Card_ProductPrice.setText("Price " + products.get(position).getPrice() + "$");



        holder.Card_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClick.onClick(products.get((position)));
                products.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, products.size());
            }
        });

        holder.btn_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantity = Integer.valueOf(holder.txt_quantity.getText().toString());
               int q = quantity + 1;
                if (q >= products.get(position).getQuantity()) {
                    ShowMessage("No Item In Stock");
                } else {
                    holder.txt_quantity.setText(String.valueOf(q));
                    increaseBtn.Increase_btn(products.get(position),quantity);
                }

            }
        });

        holder.btn_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.valueOf(holder.txt_quantity.getText().toString());
                int q= quantity-1;
                if (quantity==1){
                    ShowMessage("No Operation");
                }
                else{
                    holder.txt_quantity.setText(String.valueOf(q));
                    decreaseBtn.decrease_btn(products.get(position),q);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView Card_imageTvShow;
        TextView Card_ProductName, Card_ProductPrice, txt_quantity;
        Button Card_btn_delete;
        ImageButton btn_increase, btn_decrease;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Card_imageTvShow = itemView.findViewById(R.id.Card_imageTvShow);
            Card_ProductName = itemView.findViewById(R.id.Card_proName);
            Card_ProductPrice = itemView.findViewById(R.id.Card_proPrice);
            Card_btn_delete = itemView.findViewById(R.id.Card_btn_Delete);
            btn_increase = itemView.findViewById(R.id.img_btn_increase);
            btn_decrease = itemView.findViewById(R.id.img_btn_decrease);
            txt_quantity = itemView.findViewById(R.id.txt_view_quantity);

        }
    }

    void ShowMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}
