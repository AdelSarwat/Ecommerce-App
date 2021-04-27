package com.example.ecommerceapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class SpaceItem extends DrawerItem<SpaceItem.ViewHolder> {

    private int spaceDp;

    public SpaceItem(int spaceDp) {
        this.spaceDp = spaceDp;
    }

    @Override
    public SpaceItem.ViewHolder CreateViewHolder(ViewGroup parnet) {
        Context context =  parnet.getContext();
        View view = new View(context);
        int height = (int)(context.getResources().getDisplayMetrics().density*spaceDp);
        view.setLayoutParams( new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height
        ));
        return  new ViewHolder(view);
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public void bindViewHolder(SpaceItem.ViewHolder holder) {

    }

    public class ViewHolder extends DrawerAdapter.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
