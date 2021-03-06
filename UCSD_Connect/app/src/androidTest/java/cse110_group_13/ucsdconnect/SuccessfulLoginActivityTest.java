package cse110_group_13.ucsdconnect;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SuccessfulLoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void successfulLoginActivityTest() {

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.newEmailField),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("email@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.newPasswordField),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("password"), closeSoftKeyboard());

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

        try {
            Thread.sleep(5000);
        }

        catch (InterruptedException eee) {}

        ViewInteraction appCompatCheckedTextView1 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Sign Out"), isDisplayed()));
        appCompatCheckedTextView1.perform(click());

    }

}
