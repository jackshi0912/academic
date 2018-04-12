package cse110_group_13.ucsdconnect;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Christopher on 10/18/2016.
 *
 * Class Notes: This was taken from the firebase tutorial at
 * https://www.youtube.com/watch?v=sTk7q3f-t0I&list=PLGCjwl1RrtcTXrWuRTa59RyRmQ4OedWrt&index=6
 *
 * Description: This clas deals with the starting up of the app and connecting to firebase.
 *
 * Public Methods: onCreate()
 *
 */

public class UCSDConnect extends Application {

    // can access android context anywhere
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
