package com.techline.buzzsocial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowersRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramGetUserFollowersResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoggedUser;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;

public class LoginActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private static String TAG = "LOGIN";

    SharedPreferences SP;
    EditText pswd,usrusr;
    TextView sup,lin;
    private long longPk = 0;
    ArrayList<String> followersUserNameList = new ArrayList<String>();
    ArrayList<String> followersPicUrlList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lin = findViewById(R.id.lin);
        usrusr = findViewById(R.id.usrusr);
        pswd = findViewById(R.id.pswrdd);
        sup = findViewById(R.id.sup);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        lin.setTypeface(custom_font1);
        sup.setTypeface(custom_font);
        usrusr.setTypeface(custom_font);
        pswd.setTypeface(custom_font);


        lin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (usrusr.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Username is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pswd.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Password is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Instagram4Android instagram = Instagram4Android.builder().username(usrusr.getText().toString()).password(pswd.getText().toString()).build();
                Log.d(TAG, "Before Setup");
                instagram.setup();
                Log.d(TAG, "after Setup");
                try {
                    Log.d(TAG, "Before login");
                    InstagramLoginResult completeLoginResult = instagram.login();
                    Log.d(TAG, "completeLoginResult >>"+completeLoginResult);
                    String myMessage = completeLoginResult.getMessage();
                    Log.d(TAG, "myMessage is " + myMessage);
                    Toast.makeText(getApplicationContext(), myMessage, Toast.LENGTH_SHORT).show();

                    String myStatus = completeLoginResult.getStatus();
                    Log.d(TAG, "myStatus is " + myStatus);
                    if (myStatus.equalsIgnoreCase("OK")) {
                        Log.d(TAG, "myStatus is " + myStatus);
                        Log.d(TAG, "status is oK run again");
                        InstagramLoggedUser logged_in_user = completeLoginResult.getLogged_in_user();
                        Log.d(TAG, "myStatus is " + myStatus);
                        String AllUsrMsg = "You are Logged in as " + logged_in_user;
                        Toast.makeText(getApplicationContext(), AllUsrMsg, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "AllUsrMsg is " + AllUsrMsg);
                        String full_name = completeLoginResult.getLogged_in_user().full_name;
                        Log.d(TAG, "full_name is " + full_name);

                        String profile_pic_url = completeLoginResult.getLogged_in_user().profile_pic_url;
                        Log.d(TAG, "profile_pic_url is " + profile_pic_url);
                        String usernameInsta = completeLoginResult.getLogged_in_user().username;
                        Log.d(TAG, "usernameInsta is " + usernameInsta);
                        longPk = completeLoginResult.getLogged_in_user().pk;
                        String pk = String.valueOf(completeLoginResult.getLogged_in_user().pk);
                        Log.d(TAG, "pk is " + pk);
                        String profile_pic_id = completeLoginResult.getLogged_in_user().profile_pic_id;
                        Log.d(TAG, "profile_pic_id is " + profile_pic_id);

                        InstagramGetUserFollowersResult allMyFollowers = instagram.sendRequest(new InstagramGetUserFollowersRequest(longPk));
                        List<InstagramUserSummary> users = allMyFollowers.getUsers();
                        for (InstagramUserSummary user : users) {
                            Log.d(TAG, "User " + user.getUsername() + " follows "+full_name +"!");
                            followersUserNameList.add(user.getUsername());
                            Log.d(TAG, "user.getProfile_pic_url() " + user.getProfile_pic_url() + " follows "+full_name +"!");
                            followersPicUrlList.add(user.getProfile_pic_url());
                        }
                        int no_of_followers = allMyFollowers.getUsers().size() + 1;
                        Log.d(TAG, "no_of_followers >> "+no_of_followers);
                        Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                        it.putExtra("full_name", full_name);
                        it.putExtra("profile_pic_url", profile_pic_url);
                        it.putExtra("usernameInsta", usernameInsta);
                        it.putExtra("pk", pk);
                        it.putExtra("profile_pic_id", profile_pic_id);
                        it.putExtra("followersUserNameList", followersUserNameList);
                        it.putExtra("no_of_followers", no_of_followers);
                        it.putExtra("followersPicUrlList", followersPicUrlList);

                        startActivity(it);
                        finish();
                    }
                    else{
                        return;
                    }
                    Log.d(TAG, "after login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
/*        Instagram4Android instagram = new Instagram4Android(usrusr.getText().toString(),pswd.getText().toString());
        instagram.setup();
        try {
            instagram.login();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
               /* Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(it);
                finish();*/
            }
        });

    }

    public void onClickButon(View view)  {

    }}
