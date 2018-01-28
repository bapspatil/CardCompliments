package bapspatil.cardcompliments.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import bapspatil.cardcompliments.R;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance(); // Get Firebase Auth instance

        // Initiate FirebaseUI with email authentication, with custom theme and logo and SmartLock enabled
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.FirebaseUITheme) // Custom theme
                        .setLogo(R.drawable.logo) // Custom logo
                        .setAvailableProviders(
                                Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build()) // Email authentication provider
                        )
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);

        // Monitor the app if the authentication state changes, i.e. if user logs in or signs up in this case
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // Send email verification to user if his/her email hasn't been verified already
                    if (!mUser.isEmailVerified()) {
                        mUser.sendEmailVerification();
                        Toast.makeText(getApplicationContext(), "Please check your email and verify your account.", Toast.LENGTH_LONG).show();
                    }

                    // Wait for a while until his/her Firebase user account is fully created before sending him/her to the Main Activity
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Timber.d("User email: %s\nUser name: %s", mUser.getEmail(), mUser.getDisplayName()); // Log user details
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }, 3000);
                } else {
                    Timber.e("Error authenticating user!"); // Error authenticating user
                }

            }
        });
    }
}
