package com.cse110.mybookstore;


import android.app.Application;
import android.content.Context;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by genehorecka on 11/10/15.
 */
public class User {

    public static String firstName, lastName, username, email, bio;

    private static User instance;

    public static void initInstance(String username) {
        if (instance == null) {
            instance = new User(username);
        }
    }

    public static void initInstance(String firstName, String lastName, String username, String email) {
        if (instance == null) {
            instance = new User(firstName, lastName, username, email);
        }
    }

    public static void initInstance(String firstName, String lastName, String username, String email, String bio) {
        if (instance == null) {
            instance = new User(firstName, lastName, username, email, bio);
        }
    }

    public static void clearUser() {
        instance = null;
    }


    private User(String firstName, String lastName, String username, String email, String bio) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.bio = bio;

        instance = this;
    }

    private User(String firstName, String lastName, String username, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.bio = "";

        instance = this;
    }

    private User(String username) {
        this.username = username;
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.bio = "";

        instance = this;
    }

    public static User getInstance() {
        return instance;
    }

    public static boolean isNull() {
        return instance == null;
    }
}
