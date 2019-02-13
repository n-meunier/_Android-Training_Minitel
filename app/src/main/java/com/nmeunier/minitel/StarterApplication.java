package com.nmeunier.minitel;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Application;
import android.util.Log;

public class StarterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.ParseAppId)
                // if desired
                .clientKey(BuildConfig.ParseApiKey)
                .server(BuildConfig.ParseServer)
                .build()
        );

//        Run the app once and check if the ExampleObject has been created on your server
//        Then comment the following lines to prevent the creation of a new ExampleObject at every run
//        EXAMPLE --------------------
//        ParseObject object = new ParseObject("ExampleObject");
//        object.put("myNumber", "123");
//        object.put("myString", "test");
//
//        object.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    Log.i("Parse Result", "Successful!");
//                } else {
//                    Log.i("Parse Result", "Failed" + ex.toString());
//                }
//            }
//        });
//        EXAMPLE --------------------

        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
