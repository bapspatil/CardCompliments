package bapspatil.cardcompliments.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import bapspatil.cardcompliments.R;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        hideSystemUI(); // Helper function to hide the system bars and get into full-screen mode

        mAuth = FirebaseAuth.getInstance(); // Get FirebaseAuth instance
        int SPLASH_TIME_OUT = 1500; // Time-out for the Splash Activity in milliseconds

        // Delayed execution of checking whether the user has already logged in or not, after the displaying of splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                // Check if user has already logged in
                if(mAuth.getCurrentUser() == null) { // If the user has not logged in, take him/her to the Login Activity
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                } else { // If the user has already logged in, take him/her to the Main Activity that greets him/her by his/her name
                    i = new Intent(SplashActivity.this, MainActivity.class);
                }

                ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out); // Bundle passed to intent for screen transitions
                startActivity(i, options.toBundle());
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
