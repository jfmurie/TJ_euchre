package com.example.tjeuchre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class rules extends AppCompatActivity {
    TextView textView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        textView = (TextView) findViewById(R.id.textView2);

        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void mainActivity(View v)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}