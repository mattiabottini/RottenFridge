package com.example.rottenfridge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Button btn_scan;
    //ImageButton btn= findViewById(R.id.ivHomebt1);
    RecyclerView recyclerView;
    private Toolbar toolbar;
    public CardView card;
    private ImageButton imageButton;
    public int id=0;
    MyDatabaseHelper myDB;
    ArrayList <String> product_id, product_name, product_expiration, product_quantity;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        myDB = new MyDatabaseHelper(MainActivity.this);
        product_id = new ArrayList<>();
        product_name = new ArrayList<>();
        product_expiration = new ArrayList<>();
        product_quantity = new ArrayList<>();

        storeDataInArrays();
        customAdapter= new CustomAdapter(MainActivity.this, product_id, product_name, product_expiration, product_quantity);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Intent intent= new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                break;

            case R.id.delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cardClick(View v) {
        card=findViewById(v.getId());
            card.setOnClickListener((view) -> {
                Intent i= new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(i);
            });
    }

    void storeDataInArrays () {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "Nessun Prodotto", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                product_id.add(cursor.getString(0));
                product_name.add(cursor.getString(1));
                product_expiration.add(cursor.getString(2));
                product_quantity.add(cursor.getString(3));
            }
        }
    }
    /*private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
     */

   /* public void clickListener() {
    }

    */


}