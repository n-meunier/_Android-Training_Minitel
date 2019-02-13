package com.nmeunier.minitel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        user = new ParseUser();
    }


//    public void onClick(View view) {
////        Log.i("on Click", "View Clicked!");
//
//        if (view.getId() == R.id.changeSignupModeTextView) {
//
//            Button signupButton = (Button) findViewById(R.id.signupButton);
//
//            if (signUpModeActive) {
//
//                signUpModeActive = false;
//                signupButton.setText("Login");
//                changeSignupModeTextView.setText("Or, Signup");
//
//            } else {
//
//                signUpModeActive = true;
//                signupButton.setText("Signup");
//                changeSignupModeTextView.setText("Or, Login");
//
//            }
//
//        } else if (view.getId() == R.id.backgroundRelativeLayout || view.getId() == R.id.logoImageView) {
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//        }
//
//    }
//
    public void signUp(View view) {

        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();

        } else {

//            ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
//            String user = usernameEditText.getText().toString();
//            Log.i("User", user);
//            query.whereEqualTo("username", user);
//
//            query.findInBackground(new FindCallback<ParseObject>() {
//               @Override
//               public void done(List<ParseObject> objects, ParseException e) {
//                   if (e == null) {
//                       if (objects.size() > 0) {
//                           Log.i("Info", "Known user");
//                       } else {
//                           Log.i("Info", "Unknown user");
//                       }
//
//                   }
//               }
//           });

//            if (signUpModeActive) {
//
//                ParseUser user = new ParseUser();
//
//                user.setUsername(usernameEditText.getText().toString());
//                user.setPassword(passwordEditText.getText().toString());
//
//                user.signUpInBackground(new SignUpCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e == null) {
//
//                            Log.i("Signup", "Successful");
//                            showUserList();
//
//                        } else {
//
//                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//
//            } else {

                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user != null) {

                            Log.i("Signup", "Login successful");
//                            showUserList();
                            Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
                            startActivity(intent);

                        } else {

                            Toast.makeText(MenuActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                });


//            }
        }


    }
}
