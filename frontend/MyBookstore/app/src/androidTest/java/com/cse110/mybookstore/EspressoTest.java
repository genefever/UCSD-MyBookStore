/*
    Given that I am at login page of the app, when I input my user name, password, and click enter,
    then if that information is correct I will be able to access the app beginning with the
    selling page.

    Given that I am on the selling page, when I click on the add button, add information for a new
    book, and click on the button to sell, then the app returns the user to the selling page, and
    the new book is displayed in the list.

    Given that I am at the buying page, when I click on the search bar, input an ISBN number, and
    click enter, the search result of books with that ISBN number should display on the buying page
    list (edited the one you posted above slightly if that's okay).
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
public class EspressoTest {

}

