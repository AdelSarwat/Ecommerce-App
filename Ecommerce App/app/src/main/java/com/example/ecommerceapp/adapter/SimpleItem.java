package com.example.ecommerceapp.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ecommerceapp.R;

public class SimpleItem extends DrawerItem<SimpleItem.ViewHolder> {

    private int selectedItemIconTent;
    private int selectedItemTitleTent;

    private int normalItemIconTent;
    private int normalItemTitleTent;

    private Drawable icon;
    private String title;


    public SimpleItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    @Override
    public ViewHolder CreateViewHolder(ViewGroup parnet) {

        LayoutInflater inflater = LayoutInflater.from(parnet.getContext());
        View view = inflater.inflate(R.layout.item_option, parnet, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {
        holder.title.setText(title);
        holder.icon.setImageDrawable(icon);

//        holder.title.setText(isChecked ? selectedItemTitleTent : normalItemTitleTent);
        //holder.icon.setColorFilter(isChecked ? selectedItemIconTent : normalItemIconTent);
    }

    public SimpleItem withSelectedIconTint(int SelectedIconTint){
         this.selectedItemIconTent = SelectedIconTint;
         return this;
    }

    public SimpleItem withSelectedTileTint(int SelectedTitleTint){
        this.selectedItemTitleTent  = SelectedTitleTint;
        return this;
    }

    public SimpleItem withIconTint(int normalIconTint){
        this.normalItemIconTent = normalIconTint;
        return this;
    }

    public SimpleItem withTitleTint(int normalTitleTint){
        this.normalItemTitleTent = normalTitleTint;
        return this;
    }
    static class ViewHolder extends DrawerAdapter.ViewHolder {

        private ImageView icon;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icont);
            title = itemView.findViewById(R.id.titlet);
        }
    }
}
