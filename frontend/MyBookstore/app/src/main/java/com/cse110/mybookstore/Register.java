package com.cse110.mybookstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Register<T> extends AppCompatActivity implements View.OnClickListener {

    EditText firstName, lastName, username, email, password;
    Button registerButton;
    TextView cancelRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = (EditText) findViewById(R.id.etFirstName);
        lastName = (EditText) findViewById(R.id.etLastName);
        username = (EditText) findViewById(R.id.etUsername);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        registerButton = (Button) findViewById(R.id.registerButton);
        cancelRegistration = (TextView) findViewById(R.id.cancel_action);


        registerButton.setOnClickListener(this);
        cancelRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.registerButton:
                // Collect user info
                String firstNameStr = firstName.getText().toString();
                String lastNameStr = lastName.getText().toString();
                String usernameStr = username.getText().toString();
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();

                // Create a new User upon registration

                registerUser(firstNameStr, lastNameStr, usernameStr, emailStr, passwordStr);

                // Start Activity
                break;

            case R.id.cancel_action:
                startActivity(new Intent(this, Login.class));

            default:
        }
    }

    // need a way to indicate failure
    private void registerUser(final String fName, final String lName, final String username,
                              final String email, final String password) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://glacial-caverns-5065.herokuapp.com/signup";
        HashMap<String, String> params = new HashMap<>();


        params.put("first", fName);
        params.put("last", lName);
        params.put("password", password);
        params.put("username", username);
        params.put("email", email);

        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));

                            if (response.has("error")) {
                                new AlertDialog.Builder(Register.this)
                                        .setMessage("Username is already in use")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                            }
                            else {
                                if (!User.isNull()) {
                                    User.clearUser();
                                }

                                User.initInstance(fName, lName, username, email);

                                begin();
                            }
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF-8";
            }
        };

        queue.add(req);
    }

    private void begin() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
