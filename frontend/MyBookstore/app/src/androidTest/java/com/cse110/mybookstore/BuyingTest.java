/*
    Given that I am at the buying page, when I click on the search bar, input an ISBN number, and
    click enter, the search result of books with that ISBN number should display on the buying page
    list.
 */

package com.cse110.mybookstore;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BuyingTest {

    @Rule public final ActivityTestRule<BuyingActivity> activityRule =
            new ActivityTestRule<>(BuyingActivity.class);

    @Before
    public void setUp() {
        User.initInstance("gary", "thorn", "gmoney", "gmoney@gmail.com");
    }

    /*
    Given that I am at the buying page, when I click on the search bar, input an ISBN number, and
    click enter, the search result of books with that ISBN number should display on the buying page
    list.
    */

    @Test
    public void testLogin() {
        // Click the search bar, enter an ISBN number, then click enter
        onView(withId(R.id.menu_item_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("4242424242424"), pressKey(66));

        // Wait for correct book to be retrieved
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the only item in the list, and check if it is the correct book
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.titleInfo)).check(matches(withText("Hello")));
    }
}

