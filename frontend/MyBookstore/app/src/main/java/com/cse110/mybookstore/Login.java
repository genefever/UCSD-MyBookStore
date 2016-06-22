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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener{

    TextView registerLink;
    EditText username, password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerLink = (TextView) findViewById(R.id.registerLink);

        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.loginButton:

                // TODO: Collect User data from the server to initialize User variable HERE
                loginUser(username.getText().toString(), password.getText().toString());

                // TODO: Need to validate username and password before allowing log in.

                break;

            case R.id.registerLink:
                // Start Register Activity when 'New User' Button is clicked
                startActivity(new Intent(this, Register.class));
                break;
            default:
        }

    }

    private void loginUser(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("https://glacial-caverns-5065.herokuapp.com/login?username=%s&password=%s",
                username, password);

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));

                            if (response.has("error") && response.getString("error").equals("INCORRECT PASSWORD")) {
                                new AlertDialog.Builder(Login.this)
                                        .setMessage("Incorrect Password")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        }).show();
                            }
                            else if (response.has("error") && response.getString("error").equals("USERNAME DOES NOT EXIST")) {
                                new AlertDialog.Builder(Login.this)
                                        .setMessage("Username does not exist")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        }).show();
                            }
                            else {
                                if (!User.isNull()) {
                                    User.clearUser();
                                }

                                User.initInstance(response.getJSONObject("user").getString("first"),
                                        response.getJSONObject("user").getString("last"),
                                        response.getJSONObject("user").getString("username"),
                                        response.getJSONObject("user").getString("email"),
                                        response.getJSONObject("user").getString("bio"));

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
        });

        queue.add(req);
    }

    private void begin() {
        startActivity(new Intent(this, MainActivity.class));
    }



}
