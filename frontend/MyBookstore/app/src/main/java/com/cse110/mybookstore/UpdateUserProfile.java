package com.cse110.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateUserProfile extends BaseActivity {

    private static User user;
    EditText updateBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        user = User.getInstance();

        updateBio = (EditText) findViewById(R.id.editProfileBio);
        updateBio.setText(user.bio);

        activateToolbarWithHomeEnabled();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    // A method that describes what to do when the Edit User Profile button is clicked on the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // For this case it's to edit the current book content
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {

            updateBio = (EditText) findViewById(R.id.editProfileBio);

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://glacial-caverns-5065.herokuapp.com/updatebio";
            HashMap<String, String> params = new HashMap<>();

            params.put("bio", updateBio.getText().toString());
            params.put("username", User.username);

            JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                VolleyLog.v("Response:%n %s", response.toString(4));


                                User.bio = updateBio.getText().toString();
                                // Pass the book item to be updated by the UpdateBookActivity Class
                                Intent updateBookInfoIntent = new Intent(UpdateUserProfile.this, UserProfile.class);
                                Toast.makeText(UpdateUserProfile.this, "Profile updated.", Toast.LENGTH_SHORT).show();
                                //updateBookInfoIntent.putExtra(USER_PROFILE_TRANSFER, user);
                                startActivity(updateBookInfoIntent);
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

        return super.onOptionsItemSelected(item);
    }

}
