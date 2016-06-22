/*
    Given that I am at login page of the app, when I input my user name, password, and click enter,
    then if that information is correct I will be able to access the app beginning with the
    selling page.
 */

package com.cse110.mybookstore;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule public final ActivityTestRule<Login> activityRule =
            new ActivityTestRule<>(Login.class);

    /*
        Given that I am at login page of the app, when I input my user name, password, and click
        enter,then if that information is correct I will be able to access the app beginning
        with the selling page.
     */

    @Test
    public void testLogin() {
        onView(withId(R.id.etUsername)).perform(typeText("gmoney"));
        onView(withId(R.id.etPassword)).perform(typeText("password2"), closeSoftKeyboard());

        onView(withId(R.id.etUsername)).check(matches(withText("gmoney")));
        onView(withId(R.id.etPassword)).check(matches(withText("password2")));

        onView(withId(R.id.loginButton)).perform(click());
    }

}

