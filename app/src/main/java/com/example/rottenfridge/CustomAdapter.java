package com.example.rottenfridge;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList product_images, product_id, product_name, product_expiration, product_quantity;
    int position;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.product_id_txt.setText(String.valueOf(product_id.get(position)));
        holder.product_name_txt.setText(String.valueOf(product_name.get(position)));
        String url = "https://storage.googleapis.com/fleet-volt-352308.appspot.com/" + holder.product_name_txt.getText().toString().toLowerCase() + ".png";
        System.out.println(url);
        Picasso.get().load(url).into(holder.product_image);
        holder.product_expirationDate_txt.setText(String.valueOf(product_expiration.get(position)));
        holder.product_quantity_txt.setText(String.valueOf(product_quantity.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("id", String.valueOf(product_id.get(position)));
                intent.putExtra("name", String.valueOf(product_name.get(position)));
                intent.putExtra("exp", String.valueOf(product_expiration.get(position)));
                intent.putExtra("quantity", String.valueOf(product_quantity.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_id_txt, product_name_txt, product_expirationDate_txt, product_quantity_txt;
        LinearLayout mainLayout;
        ImageView product_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id_txt = itemView.findViewById(R.id.product_id_txt);
            product_name_txt = itemView.findViewById(R.id.product_name_txt);
            product_image = itemView.findViewById(R.id.imageViewProduct);
            String url = "https://upload.wikimedia.org/wikipedia/commons/6/69/" + product_name_txt.getText().toString() + ".png";
            System.out.println(url);
            Picasso.get().load(url).into(product_image);
            product_expirationDate_txt = itemView.findViewById(R.id.product_expirationDate_txt);
            product_quantity_txt = itemView.findViewById(R.id.product_quantity_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

}
