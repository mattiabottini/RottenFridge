package com.MattiaBottini.rottenfridge;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();
    EditText name_input, expiration_input, quantity_input;
    Button add_button;
    Toolbar toolbar;
    private static final int RESULT_SPEECH = 1;
    private FloatingActionButton micButtonName, micButtonQuantity;
    boolean button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name_input=findViewById(R.id.product_name);
        expiration_input=findViewById(R.id.product_expirationDate);
        quantity_input=findViewById(R.id.product_quantity);
        add_button=findViewById(R.id.add_Button);

        micButtonName = findViewById(R.id.micButtonName);
        micButtonQuantity = findViewById(R.id.micButtonQuantity);
        micButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    name_input.setText("");
                    button1=true;
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Your Device does not support Speech to Text!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        micButtonQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    quantity_input.setText("");
                    button2=true;
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Your Device does not support Speech to Text!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
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
                expiration_input.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_input_date));
                expiration_input.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_calendar_month_focused), null, null, null);
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
        expiration_input.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_input));
        expiration_input.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_calendar_month_24), null, null, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH:
                if(resultCode==RESULT_OK && data!=null){
                    if(button1) {
                        ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        name_input.setText(text.get(0));
                        button1=false;
                    } else if(button2){
                        ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        String intFound = getNumberFromResult(text);
                        if (!isNumeric(intFound)) {
                            quantity_input.setText("");
                            button2=false;
                        } else {
                            quantity_input.setText(String.valueOf(intFound));
                            button2=false;
                        }
                    }
                }
                break;
        }
    }

    // method to loop through results trying to find a number
    private String getNumberFromResult(ArrayList<String> results) {
        for (String str : results) {
            if (getIntNumberFromText(str.toLowerCase()) != "") {
                return getIntNumberFromText(str);
            }
        }
        return "";
    }

    // method to convert string number to integer
    private String getIntNumberFromText(String strNum) {
        switch (strNum) {
            case "zero":
                return "0";
            case "uno":
            case "one":
            case "una":
                return "1";
            case "due":
            case "two":
            case "to":
                return "2";
            case "tre":
            case "three":
            case "free":
                return "3";
            case "quattro":
            case "four":
            case "for":
                return "4";
            case "cinque":
            case "five":
                return "5";
            case "sei":
            case "six":
                return "6";
            case "sette":
            case "seven":
                return "7";
            case "otto":
            case "eight":
                return "8";
            case "nove":
            case "nine":
                return "9";
        }
        return strNum;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}

