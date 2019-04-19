package com.techline.buzzsocial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramGetRecentActivityRequest;
import dev.niekirk.com.instagram4android.requests.payload.StatusResult;

public class UserMediFeedActivity extends AppCompatActivity {
    private static final String TAG = "MediaActivity";
    private TextView mTextMessage;
    SharedPreferences SP;
    public static final String MyPREFERENCES = "MyPrefs";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent it = new Intent(UserMediFeedActivity.this, HomeActivity.class);
                    it.putExtra("full_name", full_name);
                    it.putExtra("profile_pic_url", profile_pic_url);
                    it.putExtra("usernameInsta", usernameInsta);
                    it.putExtra("pk", pk);
                    it.putExtra("profile_pic_id", profile_pic_id);
                    it.putExtra("followersUserNameList", followersUserNameList);
                    it.putExtra("no_of_followers", no_of_followers);
                    it.putExtra("followersPicUrlList", followersPicUrlList);;
                    it.putExtra("followersFullNameList", followersFullNameList);
                    it.putExtra("followersPkList", followersPkList);
                    it.putExtra("followingUserNameList", followingUserNameList);
                    it.putExtra("no_of_following", no_of_following);
                    it.putExtra("followingPicUrlList", followingPicUrlList);;
                    it.putExtra("followingFullNameList", followingFullNameList);
                    it.putExtra("followingPkList", followingPkList);
                    it.putExtra("userNameStore", userNameStore);
                    it.putExtra("passWordStore", passWordStore);
                    it.putExtra("flag", "FOLLOW");
                    startActivity(it);
                    return true;
                case R.id.navigation_dashboard:
                    Intent itDash = new Intent(UserMediFeedActivity.this, HomeActivity.class);
                    itDash.putExtra("full_name", full_name);
                    itDash.putExtra("profile_pic_url", profile_pic_url);
                    itDash.putExtra("usernameInsta", usernameInsta);
                    itDash.putExtra("pk", pk);
                    itDash.putExtra("profile_pic_id", profile_pic_id);
                    itDash.putExtra("followersUserNameList", followersUserNameList);
                    itDash.putExtra("no_of_followers", no_of_followers);
                    itDash.putExtra("followersPicUrlList", followersPicUrlList);;
                    itDash.putExtra("followersFullNameList", followersFullNameList);
                    itDash.putExtra("followersPkList", followersPkList);
                    itDash.putExtra("followingUserNameList", followingUserNameList);
                    itDash.putExtra("no_of_following", no_of_following);
                    itDash.putExtra("followingPicUrlList", followingPicUrlList);;
                    itDash.putExtra("followingFullNameList", followingFullNameList);
                    itDash.putExtra("followingPkList", followingPkList);
                    itDash.putExtra("userNameStore", userNameStore);
                    itDash.putExtra("passWordStore", passWordStore);
                    itDash.putExtra("flag", "FOLLOW");
                    startActivity(itDash);                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };
    private Bundle extras;
    private String full_name="",profile_pic_url=""
            ,usernameInsta="",pk="",profile_pic_id="";
    TextView tvDisplay_name, tvUserName, tvFolCount;
    ImageView ivThumb;
    ImageButton btnFollowers, btnUnFollowers;
    ArrayList<String> followersUserNameList = new ArrayList<String>();
    ArrayList<String> followersPicUrlList = new ArrayList<String>();
    ArrayList<String> followersPkList = new ArrayList<String>();
    ArrayList<String> followersFullNameList = new ArrayList<String>();
    private String no_of_followers;

    ArrayList<String> followingUserNameList = new ArrayList<String>();
    ArrayList<String> followingPicUrlList = new ArrayList<String>();
    ArrayList<String> followingPkList = new ArrayList<String>();
    ArrayList<String> followingFullNameList = new ArrayList<String>();
    private String no_of_following;
    private String userNameStore="", passWordStore="";
    private String getLogedInUsername;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_medi_feed);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        extras = getIntent().getExtras();
        if (extras != null) {

            full_name = extras.getString("full_name");
            profile_pic_url = extras.getString("profile_pic_url");
            usernameInsta = extras.getString("usernameInsta");
            pk = extras.getString("pk");
            profile_pic_id = extras.getString("profile_pic_id");
            no_of_followers = String.valueOf(extras.getString("no_of_followers"));
            followersUserNameList = extras.getStringArrayList("followersUserNameList");
            followersPicUrlList = extras.getStringArrayList("followersPicUrlList");
            followersFullNameList = extras.getStringArrayList("followersFullNameList");
            followersPkList = extras.getStringArrayList("followersPkList");
            no_of_following = String.valueOf(extras.getString("no_of_following"));
            followingUserNameList = extras.getStringArrayList("followingUserNameList");
            followingPicUrlList = extras.getStringArrayList("followingPicUrlList");
            followingFullNameList = extras.getStringArrayList("followingFullNameList");
            followingPkList = extras.getStringArrayList("followingPkList");

            SP = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            userNameStore = SP.getString("userNameStore",null);
            passWordStore = SP.getString("passWordStore",null);
            Log.d(TAG, "my userNameStore from Extra is :" + userNameStore);
            Log.d(TAG, "my passWordStore from Extra is :" + passWordStore);
        } else {
            return;
        }

        tvDisplay_name = findViewById(R.id.tvDisplay_name);

        ivThumb = findViewById(R.id.ivThumb);

        tvDisplay_name.setText(full_name);

        //tvPk.setText(pk);

        Glide.with(this)
                .load(profile_pic_url)
                .into(ivThumb);

        try {
            boolean returnedData = loadAllUserMedia();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean loadAllUserMedia() throws IOException {
        //get User Media request
        Instagram4Android instagram = Instagram4Android.builder().username(userNameStore).password(passWordStore).build();

        Log.d(TAG, "my userNameStore  is :" + userNameStore);
        Log.d(TAG, "my passWordStore  is :" + passWordStore);
        Log.d(TAG, "Before setup");
        instagram.setup();
        Log.d(TAG, "Before login");
        instagram.login();
        StatusResult result = null;

        try {
            result = instagram.sendRequest(new InstagramGetRecentActivityRequest());

            Log.d(TAG, "statusResult.toString() >>"+ result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "after follow request");
        boolean answer = true;
        return answer;
    }
}
