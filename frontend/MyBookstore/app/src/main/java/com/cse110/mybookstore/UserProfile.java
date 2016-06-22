package com.cse110.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


/* This represents the User Profile Activity */

public class UserProfile extends BaseActivity {

    private static User user;
    private TextView nameText;
    private TextView userNameText;
    private TextView userEmailText;
    private TextView userBioText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        super.onCreateDrawer();

        nameText = (TextView) findViewById(R.id.nameText);
        userNameText = (TextView) findViewById(R.id.userNameText);
        userEmailText = (TextView) findViewById(R.id.userEmailText);
        userBioText = (TextView) findViewById(R.id.userBioText);

        user = User.getInstance();

        nameText.setText(user.firstName + " " + user.lastName);
        userNameText.setText(user.username);
        userEmailText.setText(user.email);
        userBioText.setText(user.bio);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
        if (id == R.id.action_edit) {
            // Pass the book item to be updated by the UpdateBookActivity Class
            Intent updateBookInfoIntent = new Intent(this, UpdateUserProfile.class);
            //updateBookInfoIntent.putExtra(USER_PROFILE_TRANSFER, user);
            startActivity(updateBookInfoIntent);
        }

        return super.onOptionsItemSelected(item);
    }


}
