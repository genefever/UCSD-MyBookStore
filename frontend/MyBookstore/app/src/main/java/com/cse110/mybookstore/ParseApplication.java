package com.cse110.mybookstore;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by parth on 11/15/15.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

       // ParseObject.registerSubclass(BookItem.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "cBc3QnGepSxOdkwlQXFe5dx0QyM8pJZ7zSrs0SE1", "XbRb19CVl5fzcCrXRlmrcUg8JNJGkvCRZtZaZcS6");


    }
}
