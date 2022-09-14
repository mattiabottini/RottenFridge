package com.MattiaBottini.rottenfridge;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class IntroView extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_view);
        viewPager = findViewById(R.id.viewPager);
        IntroAdapter introAdapter = new IntroAdapter(getSupportFragmentManager());
        viewPager.setAdapter(introAdapter);
    }
}
