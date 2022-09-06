package com.MattiaBottini.rottenfridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.MattiaBottini.rottenfridge.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, exp_input, quantity_input;
    Button updateButton;
    String id, name, expiration, quantity;
    final Calendar myCalendar= Calendar.getInstance();
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input = findViewById(R.id.product_name);
        exp_input = findViewById(R.id.product_expirationDate);
        quantity_input = findViewById(R.id.product_quantity);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        exp_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateButton = findViewById(R.id.update_Button);
        getAndSetIntentData();
        toolbar=findViewById(R.id.Toolbar);
        toolbar.setTitle("Update Product");
        toolbar.setBackground(Drawable.createFromPath("#f2f4f8"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((name_input.getText().toString().trim().length() > 0) &&
                        (exp_input.getText().toString().trim().length() > 0) &&
                        (quantity_input.getText().toString().trim().length() > 0)) {
                    MyDatabaseHelper mydb = new MyDatabaseHelper(UpdateActivity.this);
                    mydb.updateData(id, name_input.getText().toString(), exp_input.getText().toString(), quantity_input.getText().toString());
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(UpdateActivity.this, "You must complete all the fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            name_input.setText(name);
            exp_input.setText(expiration);
            quantity_input.setText(quantity);
        } else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ITALIAN);
        exp_input.setText(dateFormat.format(myCalendar.getTime()));
    }
}