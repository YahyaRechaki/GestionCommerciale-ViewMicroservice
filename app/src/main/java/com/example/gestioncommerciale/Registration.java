package com.example.gestioncommerciale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;

public class Registration extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    EditText username;
    EditText email;
    EditText password;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.username);
        email    = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup   = findViewById(R.id.signupRegistration);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = username.getText().toString();
                String user_email = email.getText().toString();
                String user_password = password.getText().toString();
                if (user_name.isEmpty() || user_email.isEmpty() || user_password.isEmpty()) {
                    // show error message
                    Toast.makeText(getApplicationContext(), "All the fields are required!", Toast.LENGTH_SHORT).show();
                }else {
                    // send login request to the server
                    Toast.makeText(getApplicationContext(), "Sending login request to the server", Toast.LENGTH_SHORT).show();
                    JSONObject json = new JSONObject();
                    try {
                        json.put("username", user_name);
                        json.put("email", user_email);
                        json.put("password", user_password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                    Request request = new Request.Builder()
                            .url("http://192.168.1.123:9090/auth/register")
                            .post(requestBody)
                            .addHeader("Content-Type", "application/json")
                            .build();

                    client.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                }
            }
        });


    }
}