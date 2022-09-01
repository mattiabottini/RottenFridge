package com.example.rottenfridge;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList product_id, product_name, product_expiration, product_quantity;



    CustomAdapter (Context context, ArrayList product_id, ArrayList product_name, ArrayList product_expiration,
                   ArrayList product_quantity) {
        this.context=context;
        this.product_id=product_id;
        this.product_name=product_name;
        this.product_expiration=product_expiration;
        this.product_quantity=product_quantity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.product_id_txt.setText(String.valueOf(product_id.get(position)));
        holder.product_name_txt.setText(String.valueOf(product_name.get(position)));
        holder.product_expirationDate_txt.setText(String.valueOf(product_expiration.get(position)));
        holder.product_quantity_txt.setText(String.valueOf(product_quantity.get(position)));
    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_id_txt, product_name_txt, product_expirationDate_txt, product_quantity_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id_txt = itemView.findViewById(R.id.product_id_txt);
            product_name_txt = itemView.findViewById(R.id.product_name_txt);
            product_expirationDate_txt = itemView.findViewById(R.id.product_expirationDate_txt);
            product_quantity_txt = itemView.findViewById(R.id.product_quantity_txt);
        }
    }
}
