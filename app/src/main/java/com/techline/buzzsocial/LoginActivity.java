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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
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
    private String usernameInsta="",profile_pic_url="",full_name="",
            pk="",profile_pic_id="",no_of_followers="",no_of_following =""
           ;

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

                        SP = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = SP.edit();
                        editor.putString("userNameStore", userNameStore);
                        editor.putString("passWordStore", passWordStore);
                        editor.apply();

                        InstagramLoggedUser logged_in_user = completeLoginResult.getLogged_in_user();
                        Log.d(TAG, "myStatus is " + myStatus);
                        String AllUsrMsg = "You are Logged in as " + logged_in_user;
//                        Toast.makeText(getApplicationContext(), AllUsrMsg, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "AllUsrMsg is " + AllUsrMsg);
                        full_name = completeLoginResult.getLogged_in_user().full_name;
                        Log.d(TAG, "full_name is " + full_name);

                        profile_pic_url = completeLoginResult.getLogged_in_user().profile_pic_url;
                        Log.d(TAG, "profile_pic_url is " + profile_pic_url);
                        usernameInsta = completeLoginResult.getLogged_in_user().username;
                        Log.d(TAG, "usernameInsta is " + usernameInsta);
                        longPk = completeLoginResult.getLogged_in_user().pk;
                        pk = String.valueOf(completeLoginResult.getLogged_in_user().pk);
                        Log.d(TAG, "pk is " + pk);
                         profile_pic_id = completeLoginResult.getLogged_in_user().profile_pic_id;
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
                         no_of_followers = String.valueOf(allMyFollowers.getUsers().size() + 1);
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
                        no_of_following = String.valueOf(((InstagramGetUserFollowersResult) allImFollowing).getUsers().size() + 1);
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

                    populatePreferences();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void populatePreferences() {
        SP = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        editor.putString("full_name", full_name);
        editor.putString("profile_pic_url", profile_pic_url);
        editor.putString("usernameInsta", usernameInsta);
        editor.putString("pk", pk);
        editor.putInt("longPk", (int) longPk);
        editor.putString("profile_pic_id", profile_pic_id);
        editor.putString("no_of_followers", no_of_followers);
        editor.putString("no_of_following", no_of_following);

        Gson gson_followersUserNameList = new Gson();
        String json_followersUserNameList = gson_followersUserNameList.toJson(followersUserNameList);
        editor.putString("followersUserNameList", json_followersUserNameList);

        Gson gson_followersPicUrlList = new Gson();
        String json_followersPicUrlList = gson_followersPicUrlList.toJson(followersPicUrlList);
        editor.putString("followersPicUrlList", json_followersPicUrlList);

        Gson gson_followersFullNameList = new Gson();
        String json_followersFullNameList = gson_followersFullNameList.toJson(followersFullNameList);
        editor.putString("followersFullNameList", json_followersFullNameList);

        Gson gson_followersPkList = new Gson();
        String json_followersPkList = gson_followersPkList.toJson(followersPkList);
        editor.putString("followersPkList", json_followersPkList);

        Gson gson_followingUserNameList = new Gson();
        String json_followingUserNameList = gson_followingUserNameList.toJson(followingUserNameList);
        editor.putString("followingUserNameList", json_followingUserNameList);

        Gson gson_followingPicUrlList = new Gson();
        String json_followingPicUrlList = gson_followingPicUrlList.toJson(followingPicUrlList);
        editor.putString("followingPicUrlList", json_followingPicUrlList);

        Gson gson_followingFullNameList = new Gson();
        String json_followingFullNameList = gson_followingFullNameList.toJson(followingFullNameList);
        editor.putString("followingFullNameList", json_followingFullNameList);

        Gson gson_followingPkList = new Gson();
        String json_followingPkList = gson_followingPkList.toJson(followingPkList);
        editor.putString("followingPkList", json_followingPkList);

        editor.apply();


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
             
                setProgressValue(progress + 10);
            }
        });
        thread.start();
    }

    @Override
    public void onResume(){
        super.onResume();
        SP = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String username = SP.getString("userNameStore",null);
        String password = SP.getString("passWordStore",null);
        if (username != null && password != null )
        {
            //username and password are present, do your stuff
            full_name = SP.getString("full_name",null);
            profile_pic_url = SP.getString("profile_pic_url",null);
            usernameInsta = SP.getString("usernameInsta",null);
             pk = SP.getString("pk",null);
             longPk = SP.getInt("longPk",0);
            profile_pic_id = SP.getString("profile_pic_id", null);
            no_of_followers = SP.getString("no_of_followers", null);
            no_of_following = SP.getString("no_of_following", null);

            Gson gson = new Gson();
            String json = SP.getString("followersUserNameList", null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            followersUserNameList = gson.fromJson(json, type);
            if (followersUserNameList == null) {
                followersUserNameList = new ArrayList<>();
            }

             gson = new Gson();
             json = SP.getString("task list", null);
             type = new TypeToken<ArrayList<String>>() {}.getType();
            followersPicUrlList = gson.fromJson(json, type);
            if (followersPicUrlList == null) {
                followersPicUrlList = new ArrayList<>();
            }

             gson = new Gson();
             json = SP.getString("task list", null);
             type = new TypeToken<ArrayList<String>>() {}.getType();
            followersFullNameList = gson.fromJson(json, type);
            if (followersFullNameList == null) {
                followersFullNameList = new ArrayList<>();
            }

             gson = new Gson();
             json = SP.getString("task list", null);
             type = new TypeToken<ArrayList<String>>() {}.getType();
            followersPkList = gson.fromJson(json, type);
            if (followersPkList == null) {
                followersPkList = new ArrayList<>();
            }

             gson = new Gson();
             json = SP.getString("task list", null);
             type = new TypeToken<ArrayList<String>>() {}.getType();
            followingUserNameList = gson.fromJson(json, type);
            if (followingUserNameList == null) {
                followingUserNameList = new ArrayList<>();
            }

             gson = new Gson();
             json = SP.getString("task list", null);
             type = new TypeToken<ArrayList<String>>() {}.getType();
            followingPicUrlList = gson.fromJson(json, type);
            if (followingPicUrlList == null) {
                followingPicUrlList = new ArrayList<>();
            }

             gson = new Gson();
             json = SP.getString("task list", null);
             type = new TypeToken<ArrayList<String>>() {}.getType();
            followingFullNameList = gson.fromJson(json, type);
            if (followingFullNameList == null) {
                followingFullNameList = new ArrayList<>();
            }

             gson = new Gson();
             json = SP.getString("task list", null);
             type = new TypeToken<ArrayList<String>>() {}.getType();
            followingPkList = gson.fromJson(json, type);
            if (followingPkList == null) {
                followingPkList = new ArrayList<>();
            }


        }
    }
}
