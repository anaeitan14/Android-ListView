package com.example.myviewlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Product> {

    public CustomAdapter(@NonNull Context context, ArrayList databaseAdapter) {
        super(context, R.layout.custom_row, databaseAdapter);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View customView = li.inflate(R.layout.custom_row, parent, false);

        String productName = getItem(position).getName();
        String productPrice = String.valueOf(getItem(position).getPrice());
        String productQty = String.valueOf(getItem(position).getQty());
        String productInfo = getItem(position).getInfo();

        TextView tvItem = customView.findViewById(R.id.productName);
        TextView tvInfo = customView.findViewById(R.id.tvInfo);
        TextView tvPrice = customView.findViewById(R.id.tvPrice);
        TextView tvQty = customView.findViewById(R.id.tvQty);

        tvItem.setText(productName);
        tvPrice.setText(productPrice);
        tvQty.setText(productQty);
        tvInfo.setText(productInfo);

        return customView;
    }
}