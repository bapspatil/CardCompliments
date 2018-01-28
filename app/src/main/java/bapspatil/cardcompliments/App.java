package bapspatil.cardcompliments;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by bapspatil
 */

public class App extends Application {

    private static App APP;

    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
        if(BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree()); // For debugging in the debug flavor of the app only
    }

    public static App getInstance() {
        return APP;
    }
}
