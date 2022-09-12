package com.MattiaBottini.rottenfridge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.MattiaBottini.rottenfridge.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

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
    ImageView empty;
    TextView textEmpty;
    AudioManager audioManager;
    SearchView searchView;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        empty = findViewById(R.id.imageView);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        textEmpty = findViewById(R.id.textView);
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
                finish();
                break;

            case R.id.deleteAll:
                    confirmDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cardClick(View v) {
        card=findViewById(v.getId());
            card.setOnClickListener((view) -> {
                Intent i= new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(i);
                finish();
            });
    }

    void storeDataInArrays () {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount()==0){
            empty.setVisibility(View.VISIBLE);
            textEmpty.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()){
                product_id.add(cursor.getString(0));
                product_name.add(cursor.getString(1));
                product_expiration.add(cursor.getString(2));
                product_quantity.add(cursor.getString(3));
            }
            empty.setVisibility(View.GONE);
            textEmpty.setVisibility(View.GONE);
        }
    }

    void confirmDialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to remove all products?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(MainActivity.this);
                mydb.deleteAllProducts();
                Intent intent= new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.create().show();
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