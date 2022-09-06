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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();
    EditText name_input, expiration_input, quantity_input;
    Button add_button;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name_input=findViewById(R.id.product_name);
        expiration_input=findViewById(R.id.product_expirationDate);
        quantity_input=findViewById(R.id.product_quantity);
        add_button=findViewById(R.id.add_Button);
        toolbar=findViewById(R.id.Toolbar);
        toolbar.setTitle("Add Product");
        toolbar.setBackground(Drawable.createFromPath("#f2f4f8"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        expiration_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((name_input.getText().toString().trim().length() > 0) &&
                        (expiration_input.getText().toString().trim().length() > 0) &&
                        (quantity_input.getText().toString().trim().length() > 0)) {
                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                    myDB.addProduct(name_input.getText().toString().trim(),
                            expiration_input.getText().toString().trim(),
                            Integer.valueOf(quantity_input.getText().toString().trim()));
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(AddActivity.this, "You must complete all the fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ITALIAN);
        expiration_input.setText(dateFormat.format(myCalendar.getTime()));
    }
}