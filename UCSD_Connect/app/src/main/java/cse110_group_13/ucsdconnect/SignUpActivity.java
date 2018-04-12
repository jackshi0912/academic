package cse110_group_13.ucsdconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
 * Class Notes: The code for this activity follows the tutorial at
 * https://www.youtube.com/watch?v=jmOEeJ4CFH4
 * Much of the logic is changed however
 *
 * Description:This class creates the activity and display for the sign up screen when a user
 * creates a new user account in our app.
 *
 * Public Methods: onStart()
 *                 onStop()
 *                 createAccount(String email, String password)
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText mNewEmailField;
    private EditText mNewPasswordField;
    private EditText mConfirmPasswordField;

    private Button mCreateAccountBtn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        mNewEmailField = (EditText) findViewById(R.id.newEmailField);
        mNewPasswordField = (EditText) findViewById(R.id.newPasswordField);
        mConfirmPasswordField = (EditText) findViewById(R.id.confirmPasswordField);

        mCreateAccountBtn = (Button) findViewById(R.id.createAccount);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get email and password
                String email = mNewEmailField.getText().toString();
                String password = mNewPasswordField.getText().toString();
                String confirmPassword = mConfirmPasswordField.getText().toString();

                CharSequence ucsd = "@ucsd.edu";
                if (!email.contains(ucsd)) {
                    Toast.makeText(SignUpActivity.this, "Need UCSD Email", Toast.LENGTH_SHORT).show();
                }

                else if (password.length() < 8) {
                    Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                }

                else if (!(password.equals(confirmPassword))) {
                    Toast.makeText(SignUpActivity.this, "Passwords Must Match", Toast.LENGTH_SHORT).show();
                }

                else {
                    createAccount(email, password);
                }

            }
        });


    }

    @Override
    public void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    public void onStop() {
        super.onStop();
        if (mAuthListener != null) mAuth.removeAuthStateListener(mAuthListener);
    }

    public void createAccount(String email, String password) {

        Log.d(TAG, "createAccount: " + email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Account Creation Failed",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("[Sign up]", "Task not successful");
                        }

                        else {
                            // create the user
                            Log.d("[Sign up]", "Task successful");
                            User.currentUser = new User();
                            startActivity(new Intent(SignUpActivity.this, MenuActivity.class));
                        }

                    }
                });
    }
}
