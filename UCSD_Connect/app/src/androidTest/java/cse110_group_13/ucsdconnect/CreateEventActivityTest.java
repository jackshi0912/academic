package cse110_group_13.ucsdconnect;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateEventActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void createEventActivityTest() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.newEmailField),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("email@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.newPasswordField),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginBtn), withText("Login"),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException eee) {

        }

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Create Event"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.create_event_name),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("New"), closeSoftKeyboard());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.create_event_start_date), withText("Event Start Date"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withText("OK"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.create_event_end_date), withText("Event End Date"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withText("OK"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.create_event_start_time), withText("Event Start Time"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withText("OK"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.create_event_end_time), withText("Event End Time"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withText("OK"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.create_event_category),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withText("Other"), isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.create_event_description),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Other"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.create_event_button), withText("Set Location and Create Event"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton6.perform(click());

        pressBack();

        ViewInteraction appCompatCheckedTextView1 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Sign Out"), isDisplayed()));
        appCompatCheckedTextView1.perform(click());

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException eee) {

        }


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
