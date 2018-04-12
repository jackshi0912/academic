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
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Christopher on 11/27/2016.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CategoryTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void categoryTest() {

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
        } catch (InterruptedException eee) {

        }

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Categories"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.category_name), withText("Sports"), isDisplayed()));
        appCompatTextView6.perform(click());

        pressBack();

        ViewInteraction imageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton2.perform(click());

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Categories"), isDisplayed()));
        appCompatCheckedTextView4.perform(click());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(R.id.category_name), withText("Academic"), isDisplayed()));
        appCompatTextView7.perform(click());

        pressBack();

        try {
            Thread.sleep(3000);
        }

        catch (InterruptedException eee) {}

        ViewInteraction imageButton1 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton1.perform(click());

        try {
            Thread.sleep(3000);
        }

        catch (InterruptedException eee) {}

        ViewInteraction appCompatCheckedTextView1 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Sign Out"), isDisplayed()));
        appCompatCheckedTextView1.perform(click());
    }
}
