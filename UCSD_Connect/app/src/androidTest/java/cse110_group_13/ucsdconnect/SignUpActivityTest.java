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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void signUpActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.signUp), withText("Sign Up"),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.newEmailField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText.perform(replaceText(";@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.newPasswordField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("pa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.confirmPasswordField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("pa"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.createAccount), withText("Create Account"),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.newEmailField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText1.perform(replaceText("t@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.newPasswordField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("pass"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.confirmPasswordField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("pass"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.createAccount), withText("Create Account"),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.newEmailField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("t@ucsd.edu"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.newPasswordField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("pass"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.confirmPasswordField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("pass"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.createAccount), withText("Create Account"),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.newPasswordField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.createAccount), withText("Create Account"),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.confirmPasswordField),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.createAccount), withText("Create Account"),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton6.perform(click());

    }

}
