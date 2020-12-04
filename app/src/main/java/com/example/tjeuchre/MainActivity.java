package com.example.tjeuchre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void rulesActivity(View v)
    {
        Intent i = new Intent(this, rules.class);
        startActivity(i);

    }

    public void gameActivity(View v)
    {
        Intent i = new Intent(this, GameScreen.class);
        startActivity(i);
    }
}