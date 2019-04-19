package com.techline.buzzsocial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowersRequest;
import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowingRequest;
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramGetUserFollowersResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoggedUser;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsernameResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;
import dev.niekirk.com.instagram4android.requests.payload.StatusResult;

public class LoginActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private static String TAG = "LOGIN";
    private String userNameStore="", passWordStore="";
    SharedPreferences SP;
    EditText pswd,usrusr;
    TextView sup,lin;
    private long longPk = 0;
    ArrayList<String> followersUserNameList = new ArrayList<String>();
    ArrayList<String> followersPicUrlList = new ArrayList<String>();
    ArrayList<String> followersFullNameList = new ArrayList<String>();
    ArrayList<String> followersPkList = new ArrayList<String>();

    ArrayList<String> followingUserNameList = new ArrayList<String>();
    ArrayList<String> followingPicUrlList = new ArrayList<String>();
    ArrayList<String> followingFullNameList = new ArrayList<String>();
    ArrayList<String> followingPkList = new ArrayList<String>();
    int progress = 0;
    ProgressBar simpleProgressBar;
    int maxValue = 0;
    int progressValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        simpleProgressBar=(ProgressBar) findViewById(R.id.progressbar); // initiate the progress bar
        maxValue=simpleProgressBar.getMax(); // get maximum value of the progress bar
        progressValue=simpleProgressBar.getProgress(); // get progress value from the progress bar
         lin = findViewById(R.id.lin);
        simpleProgressBar.setMax(100); // 100 maximum value for the progress bar
        simpleProgressBar.setProgress(50); // 50 default progress value for the progress bar

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
                simpleProgressBar.setVisibility(View.VISIBLE);
                setProgressValue(progress);
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
                        userNameStore=usrusr.getText().toString();
                        passWordStore=pswd.getText().toString();
                        InstagramLoggedUser logged_in_user = completeLoginResult.getLogged_in_user();
                        Log.d(TAG, "myStatus is " + myStatus);
                        String AllUsrMsg = "You are Logged in as " + logged_in_user;
//                        Toast.makeText(getApplicationContext(), AllUsrMsg, Toast.LENGTH_SHORT).show();
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

                        //followers starts here
                        InstagramGetUserFollowersResult allMyFollowers = instagram.sendRequest(new InstagramGetUserFollowersRequest(longPk));
                        List<InstagramUserSummary> users = allMyFollowers.getUsers();
                        for (InstagramUserSummary user : users) {
                            Log.d(TAG, "User Name" + user.getUsername() + " follows "+full_name +"!");
                            followersUserNameList.add(user.getUsername());
                            Log.d(TAG, "user.getProfile_pic_url() " + user.getProfile_pic_url() + " follows "+full_name +"!");
                            followersPicUrlList.add(user.getProfile_pic_url());
                            Log.d(TAG, "user full name " + user.getFull_name() + " follows "+full_name +"!");
                            followersFullNameList.add(user.getFull_name());
                            Log.d(TAG, "user PK " + user.getPk() + " follows "+full_name +"!");
                            followersPkList.add(user.getFull_name());
                        }
                        int no_of_followers = allMyFollowers.getUsers().size() + 1;
                        Log.d(TAG, "no_of_followers >> "+no_of_followers);

                        //followers ends here

                        //following starts here
                        StatusResult allImFollowing = instagram.sendRequest(new InstagramGetUserFollowingRequest(longPk));
                        List<InstagramUserSummary> usersImFollowing = ((InstagramGetUserFollowersResult) allImFollowing).getUsers();
                        for (InstagramUserSummary user2 : usersImFollowing) {
                            Log.d(TAG, "User Name" + user2.getUsername() + " is followed by "+full_name +"!");
                            followingUserNameList.add(user2.getUsername());
                           Log.d(TAG, "user.getProfile_pic_url() " + user2.getProfile_pic_url() + " is followed by "+full_name +"!");
                            followingPicUrlList.add(user2.getProfile_pic_url());
                            Log.d(TAG, "user full name " + user2.getFull_name() + " is followed by "+full_name +"!");
                            followingFullNameList.add(user2.getFull_name());
                            Log.d(TAG, "user PK " + user2.getPk() + " is also followed by "+full_name +"!");
                            followingPkList.add(user2.getFull_name());

                        }
                        int no_of_following = ((InstagramGetUserFollowersResult) allImFollowing).getUsers().size() + 1;
                        Log.d(TAG, "no_of_following >> "+no_of_following);

                        //following ends here

                        Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                        it.putExtra("full_name", full_name);
                        it.putExtra("profile_pic_url", profile_pic_url);
                        it.putExtra("usernameInsta", usernameInsta);
                        it.putExtra("pk", pk);
                        it.putExtra("profile_pic_id", profile_pic_id);
                        it.putExtra("followersUserNameList", followersUserNameList);
                        it.putExtra("no_of_followers", no_of_followers);
                        it.putExtra("followersPicUrlList", followersPicUrlList);
                        it.putExtra("followersFullNameList", followersFullNameList);
                        it.putExtra("followersPkList", followersPkList);

                        it.putExtra("followingUserNameList", followingUserNameList);
                        it.putExtra("no_of_following", no_of_following);
                        it.putExtra("followingPicUrlList", followingPicUrlList);
                        it.putExtra("followingFullNameList", followingFullNameList);
                        it.putExtra("followingPkList", followingPkList);
                        it.putExtra("userNameStore", userNameStore);
                        it.putExtra("passWordStore", passWordStore);

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

    }

    private void setProgressValue(final int progress) {

        // set the progress
        simpleProgressBar.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
    }
}
