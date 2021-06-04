package com.example.taskmaster;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class settingTest {
    @Rule
    public ActivityScenarioRule<SettingPage> activityRule =new ActivityScenarioRule<>(SettingPage.class);


    @Test
    public void editUserName(){
        onView(withId(R.id.editTextTextPersonName3)).check(matches(withText("set a Name")));
        onView(withId(R.id.content)).check(matches(withText("set a Name")));
    }

    @Test
    public void importantUIElementsNameInput() {
        onView(withId(R.id.editTextTextPersonName3)).check(matches(isDisplayed()));
    }

    @Test
    public void importantUIElementsSaveButton() {

        onView(withText("set a Name")).check(matches(isDisplayed()));
                onView(withId(R.id.button5)).perform(click());
        onView(withId(R.id.settingsButtonHome)).perform(click());
        onView(withId(R.id.editTextTextPersonName3)).perform(typeText("set a Name"));


    }


}