package com.MattiaBottini.rottenfridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MattiaBottini.rottenfridge.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class ProductsActivity extends AppCompatActivity {

    TextView tv;
    private Context context;
    TextView prod_id, prod_name, prod_expiration_descr, prod_expiration, prod_quantity_descr, prod_quantity;
    String id, name, expiration, quantity;
    private Toolbar toolbar;
    ImageView imgProd;
    TextToSpeech textToSpeech;
    AudioManager audioManager;
    boolean audioOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        toolbar=findViewById(R.id.Toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        prod_id = findViewById(R.id.Textidprod);
        prod_name = findViewById(R.id.Text1);
        prod_expiration_descr = findViewById(R.id.Text2);
        prod_expiration = findViewById(R.id.Text2b);
        prod_quantity_descr = findViewById(R.id.Text3);
        prod_quantity = findViewById(R.id.Text3b);
        imgProd = findViewById(R.id.prodImage);

        getAndSetIntentData();
        String url = "https://storage.googleapis.com/fleet-volt-352308.appspot.com/" + prod_name.getText().toString().toLowerCase() + ".png";
        Picasso.get().load(url).into(imgProd);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i==TextToSpeech.SUCCESS){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                    textToSpeech.speak(prod_name.getText().toString(),TextToSpeech.QUEUE_ADD,null,null);
                    textToSpeech.speak(prod_expiration_descr.getText().toString(),TextToSpeech.QUEUE_ADD,null,null);
                    textToSpeech.speak(prod_expiration.getText().toString(),TextToSpeech.QUEUE_ADD,null,null);
                    textToSpeech.speak(prod_quantity_descr.getText().toString(),TextToSpeech.QUEUE_ADD,null,null);
                    textToSpeech.speak(prod_quantity.getText().toString(),TextToSpeech.QUEUE_ADD,null,null);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product, menu);
        /* AudioManager audio = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (audio.getRingerMode() ==  AudioManager.ADJUST_UNMUTE){
            volumeUp.setIcon(R.drawable.ic_baseline_volume_up_24);
        } else {
            volumeUp.setIcon(R.drawable.ic_baseline_volume_off_24);
        }
         */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.modify:
                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                intent.putExtra("id", String.valueOf(prod_id.getText()));
                intent.putExtra("name", String.valueOf(prod_name.getText()));
                intent.putExtra("exp", String.valueOf(prod_expiration.getText()));
                intent.putExtra("quantity", String.valueOf(prod_quantity.getText()));
                System.out.println(prod_id.getText());
                startActivity(intent);
                break;

            case R.id.delete:
                confirmDialog();
                break;

            case R.id.volumeUp:
                if(!audioOff) {
                    mute(audioManager);
                    item.setIcon(R.drawable.ic_baseline_volume_off_24);
                    audioOff=true;
                } else {
                    unmute(audioManager);
                    item.setIcon(R.drawable.ic_baseline_volume_up_24);
                    audioOff=false;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private void mute(AudioManager audiomanager) {
        //mute audio
        audiomanager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
    }

    public void unmute(AudioManager audiomanager) {
        //unmute audio
        audiomanager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
    }

}