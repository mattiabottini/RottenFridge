package com.example.rottenfridge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ProductsActivity extends AppCompatActivity {

    TextView tv;
    private Context context;
    TextView prod_id, prod_name, prod_expiration, prod_quantity;
    String id, name, expiration, quantity;
    private Toolbar toolbar;
    private ImageButton modifyButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prod_id = findViewById(R.id.Textidprod);
        prod_name = findViewById(R.id.Text1);
        prod_expiration = findViewById(R.id.Text2b);
        prod_quantity = findViewById(R.id.Text3b);
        modifyButton = findViewById(R.id.imageButton);
        deleteButton = findViewById(R.id.imageButtonDelete);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                intent.putExtra("id", String.valueOf(prod_id.getText()));
                intent.putExtra("name", String.valueOf(prod_name.getText()));
                intent.putExtra("exp", String.valueOf(prod_expiration.getText()));
                intent.putExtra("quantity", String.valueOf(prod_quantity.getText()));
                System.out.println(prod_id.getText());
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        getAndSetIntentData();
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") &&
                getIntent().hasExtra("name") &&
                getIntent().hasExtra("exp") &&
                getIntent().hasExtra("quantity")){
            //Getting Data
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            expiration = getIntent().getStringExtra("exp");
            quantity = getIntent().getStringExtra("quantity");
            //Setting Data
            prod_id.setText(id);
            prod_name.setText(name);
            prod_expiration.setText(expiration);
            prod_quantity.setText(quantity);
        } else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name);
        builder.setMessage("Are you sure you want to remove " + name.toUpperCase() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(ProductsActivity.this);
                mydb.deleteOneProduct(id);
                Intent intent = new Intent(ProductsActivity.this, MainActivity.class);
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
}