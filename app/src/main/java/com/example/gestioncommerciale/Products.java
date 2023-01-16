package com.example.gestioncommerciale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;

public class Products extends AppCompatActivity {

    private TextView username_textview;
    final String API_URL_READ_PRODUCTS = "http://192.168.1.123:9091/product/readproducts";
    OkHttpClient client = new OkHttpClient();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Request request = new Request.Builder()
                .url(API_URL_READ_PRODUCTS)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                Log.d("onFailure: ", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    Gson gson = new Gson();
                    final Product[] products = gson.fromJson(jsonString, Product[].class);
                    Log.d("products", String.valueOf(products.length));
                    runOnUiThread(new Runnable() {
                        public void run() {

                        }
                    });
                } else {
                    Log.d("err : ", "errElse");
                    // Handle error response
                }
            }
        });


        username_textview = findViewById(R.id.username_textview);
        String user_name = getIntent().getStringExtra("username");
        username_textview.setText("Welcome Mr. " + user_name + ", Choose your products.");


    }

}