package cse110_group_13.ucsdconnect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
 * Class Notes
 * The login activity was created by following the turtoral at
 * https://www.youtube.com/watch?v=oi-UAwiBigQ&list=PLGCjwl1RrtcTXrWuRTa59RyRmQ4OedWrt&index=9
 * The skeleton is the same, but some of the logic implemented in each of the methods is different
 *
 * Description: This class creates and activity that provides the lay out for the login frame of
 * our app including a user name and password in order to access the application.
 *
 * Public Methods: startLoginIn()
 *
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField ;
    private EditText mPasswordField ;

    private Button mLoginBtn;
    private Button mSignUpBtn;

    private FirebaseAuth mAuth;
    public static boolean signOut = false;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        mEmailField = (EditText) findViewById(R.id.newEmailField);
        mPasswordField = (EditText) findViewById(R.id.newPasswordField);

        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mSignUpBtn = (Button) findViewById(R.id.signUp);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // sign out the user if they clicked on signout button
                if (signOut) {

                    firebaseAuth.signOut();
                    signOut = false;
                    User.currentUser = null;
                }

                if (firebaseAuth.getCurrentUser() != null) {

                    User.currentUser = new User();
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                }
            }



        };

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLoginIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void startLoginIn() {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (TextUtils.isEmpty(email)) {

            Toast.makeText(LoginActivity.this, "Please enter an email", Toast.LENGTH_LONG).show();

        }

        else if (TextUtils.isEmpty(password)) {

            Toast.makeText(LoginActivity.this, "Please enter a password", Toast.LENGTH_LONG).show();

        }

        else {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful())
                        Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
