package bapspatil.cardcompliments.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import bapspatil.cardcompliments.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_greeting) TextView mGreetingTextView;
    @BindView(R.id.image_logout) ImageView mLogoutImageView;
    @BindView(R.id.button_developer) Button mDeveloperButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Setting the font family for the greeting text
        Typeface typeface = ResourcesCompat.getFont(this, R.font.nunito_extrabold);
        mGreetingTextView.setTypeface(typeface);

        mAuth = FirebaseAuth.getInstance(); // Get FirebaseAuth instance
        FirebaseUser mUser = mAuth.getCurrentUser(); // Get the user from the FirebaseAuth instance

        // Check if the user and his/her name is null
        if (mUser != null && mUser.getDisplayName() != null) {
            Timber.d("User email: %s\nUser name: %s", mUser.getEmail(), mUser.getDisplayName()); // Log user details
            String greeting = getString(R.string.hey_there_greeting) + mUser.getDisplayName() + getString(R.string.you_are_the_best);
            mGreetingTextView.setText(greeting); // Greet him/her by name
        } else {
            mGreetingTextView.setText(getString(R.string.how_are_you)); // Generic greeting
        }

        // Logout Firebase user with the logout button and take him/her back to Splash Activity > Login Activity
        mLogoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut(); // Logout the user via the FirebaseAuth instance
                Intent intentToLogin = new Intent(MainActivity.this, SplashActivity.class); // Take him/her back to Splash Activity after he/she logs out so he/she can login back again

                ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out); // Bundle passed to intent for screen transitions
                startActivity(intentToLogin, options.toBundle());
                finish();
            }
        });

        // Clicking on the Button will take you to my website :-)
        mDeveloperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Welcome to Bapusaheb Patil's website!", Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse("http://www.bapspatil.com");
                Intent myWebsiteIntent = new Intent(Intent.ACTION_VIEW, uri);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out); // Bundle passed to intent for screen transitions
                startActivity(myWebsiteIntent, options.toBundle());
            }
        });
    }
}
