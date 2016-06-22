/*
    Given that I am on the selling page, when I click on the add button, add information for a new
    book, and click on the button to sell, then the app returns the user to the selling page, and
    the new book is displayed in the list.
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
public class SellingTest {

    @Rule public final ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    /*
        Given that I am at login page of the app, when I input my user name, password, and click
        enter,then if that information is correct I will be able to access the app beginning
        with the selling page.
     */

    @Before
    public void setUp() {
        User.initInstance("gary", "thorn", "gmoney", "gmoney@gmail.com");
    }

    /*
    Given that I am on the selling page, when I click on the add button, add information for a new
    book, and click on the button to sell, then the app returns the user to the selling page, and
    the new book is displayed in the list.
     */

    @Test
    public void testLogin() {
        onView(withId(R.id.addButton)).perform(click());

        onView(withId(R.id.title_input)).perform(typeText("A Good Book Part 5")).check(matches(withText("A Good Book Part 5")));
        onView(withId(R.id.author_input)).perform(typeText("Gary Reginald Thorn")).check(matches(withText("Gary Reginald Thorn")));
        onView(withId(R.id.isbn_input)).perform(typeText("1234444567890")).check(matches(withText("1234444567890")));
        onView(withId(R.id.price_input)).perform(typeText("45")).check(matches(withText("45")));
        onView(withId(R.id.description_input)).perform(typeText("It's really good")).check(matches(withText("It's really good")));

        onView(withId(R.id.add_book_button)).perform(click());
    }

}

