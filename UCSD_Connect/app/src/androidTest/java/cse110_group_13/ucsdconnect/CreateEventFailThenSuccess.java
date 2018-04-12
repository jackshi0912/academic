package cse110_group_13.ucsdconnect;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateEventFailThenSuccess {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void createEventFailThenSuccess() {
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

        catch (InterruptedException eee) {}

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Create Event"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.create_event_button), withText("Set Location and Create Event"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.create_event_name),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("aa"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.create_event_button), withText("Set Location and Create Event"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.create_event_description),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("bb"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.create_event_category),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withText("Other"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.create_event_button), withText("Set Location and Create Event"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.create_event_start_date), withText("Event Start Date"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")), withContentDescription("Next month"),
                        withParent(allOf(withClassName(is("android.widget.DayPickerView")),
                                withParent(withClassName(is("com.android.internal.widget.DialogViewAnimator"))))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withText("OK"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.create_event_button), withText("Set Location and Create Event"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.create_event_end_date), withText("Event End Date"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")), withContentDescription("Next month"),
                        withParent(allOf(withClassName(is("android.widget.DayPickerView")),
                                withParent(withClassName(is("com.android.internal.widget.DialogViewAnimator"))))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withText("OK"), isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.create_event_start_time), withText("Event Start Time"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withText("PM"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withText("OK"), isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.create_event_button), withText("Set Location and Create Event"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.create_event_end_time), withText("Event End Time"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withText("OK"), isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.create_event_button), withText("Set Location and Create Event"),
                        withParent(allOf(withId(R.id.activity_create_event),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton11.perform(click());

        pressBack();

        pressBack();

        try {
            Thread.sleep(5000);
        }

        catch (InterruptedException eee) {}

        ViewInteraction imageButton1 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton1.perform(click());

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException eee) {

        }

        ViewInteraction appCompatCheckedTextView1 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Sign Out"), isDisplayed()));
        appCompatCheckedTextView1.perform(click());
    }

}
