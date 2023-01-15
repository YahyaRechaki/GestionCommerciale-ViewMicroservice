package com.example.gestioncommerciale;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;

public class Login extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button redirectToRegistration;
    //private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.useremail);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        redirectToRegistration = findViewById(R.id.redirectToRegistration);
        //registerButton = findViewById(R.id.register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    // show error message
                    Toast.makeText(getApplicationContext(), "Please enter a valid username and password", Toast.LENGTH_SHORT).show();
                } else {
                    // send login request to the server
                    Toast.makeText(getApplicationContext(), "Sending login request to the server", Toast.LENGTH_SHORT).show();

                    JSONObject json = new JSONObject();
                    try {
                        json.put("email", email);
                        json.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                    Request request = new Request.Builder()
                            .url("http://192.168.1.123:9090/auth/login")
                            .post(requestBody)
                            .addHeader("Content-Type", "application/json")
                            .build();

                    client.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("failure : ", e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                // Handle successful response
                                Log.d("typeof response", response.getClass().getName());
                                Log.d("BEFORE, res : ", String.valueOf(response));
                                String responseString = response.body().string();
                                Log.d("AFTER, res : ", responseString);
                                // Parse the response string as JSON
                                JSONObject jsonResponse = null;
                                try {
                                    jsonResponse = new JSONObject(responseString);
                                    Log.d("jsonRes : ", String.valueOf(jsonResponse));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // Get the username property from the JSON object
                                try {
                                    if(jsonResponse != null){
                                        Log.d("fromIfTry: ", "fromIfTry");
                                        String user_name = jsonResponse.getString("username");
                                        Intent intent = new Intent(Login.this, Products.class);
                                        intent.putExtra("username", user_name);
                                        startActivity(intent);
                                    }
                                    else{
                                        Log.d("fromElseTry: ", "fromElseTry");
                                        //Toast.makeText(getApplicationContext(), "Username and password are required!!", Toast.LENGTH_SHORT).show();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "No user is registered with this email or password!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // Check if user exists or not
                                // If exists, redirect user to products screen

                                // If not, show error message
                            } else {
                                Log.d("err : ", response.toString());
                                // Handle unsuccessful response
                            }
                        }
                    });


                }
            }
        });

        redirectToRegistration = findViewById(R.id.redirectToRegistration);

        redirectToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });

        /*registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    // show error message
                    Toast.makeText(getApplicationContext(), "Username and password are required", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Registering ...", Toast.LENGTH_SHORT).show();
                    // send register request to the server
                }
            }
        });*/
    }
}
