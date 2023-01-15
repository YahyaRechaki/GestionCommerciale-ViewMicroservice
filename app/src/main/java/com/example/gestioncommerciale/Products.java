package com.example.gestioncommerciale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Products extends AppCompatActivity {

    private TextView username_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        username_textview = findViewById(R.id.username_textview);
        String user_name = getIntent().getStringExtra("username");
        username_textview.setText("Welcome Mr. " + user_name + ", Choose your products.");
    }

}