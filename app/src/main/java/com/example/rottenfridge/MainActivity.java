package com.example.rottenfridge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
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
public class MainActivity extends AppCompatActivity {

    //Button btn_scan;
    //ImageButton btn= findViewById(R.id.ivHomebt1);
    private Toolbar toolbar;
    public CardView card;
    private ImageButton imageButton;
    public int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        /*card = findViewById(R.id.cardHome);
        imageButton= findViewById(R.id.ivHomebt1);
        if(card != null){
            card.setOnClickListener((v) -> {
                startActivity(new Intent(getApplicationContext(), ProductsActivity.class));
            });
        }
        if(imageButton != null){
            imageButton.setOnClickListener((v) -> {
                startActivity(new Intent(getApplicationContext(), ProductsActivity.class));
            });
        }

         */

        /*btn_scan = findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(v->
                {
                    scanCode();
                });
         */
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
                //i.putExtra("id", id);
                //i.putExtra("text", text);
                startActivity(i);
            });
    }

    public void setCard(CardView card) {
        this.card = card;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public CardView getCard() {
        return card;
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