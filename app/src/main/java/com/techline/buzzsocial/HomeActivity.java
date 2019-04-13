package com.techline.buzzsocial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowersRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramGetUserFollowersResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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
    ArrayList<String> followersUserNameList = new ArrayList<String>();
    ArrayList<String> followersPicUrlList = new ArrayList<String>();
    private String no_of_followers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
            no_of_followers = String.valueOf(extras.getInt("no_of_followers"));
            followersUserNameList = extras.getStringArrayList("followersUserNameList");
            followersPicUrlList = extras.getStringArrayList("followersPicUrlList");
            Log.d(TAG, "my full_name from Extra is :" + full_name);
            Log.d(TAG, "my profile_pic_url from Extra is :" + profile_pic_url);
            Log.d(TAG, "my usernameInsta from Extra is :" + usernameInsta);
            Log.d(TAG, "my pk from Extra is :" + pk);
            Log.d(TAG, "my profile_pic_id from Extra is :" + profile_pic_id);
            Log.d(TAG, "my no_of_followers from Extra is :" + no_of_followers);
            Log.d(TAG, "my followersUserNameList from Extra is :" + followersUserNameList);
            Log.d(TAG, "my followersPicUrlList from Extra is :" + followersPicUrlList);

        } else {
            return;
        }

        tvDisplay_name = findViewById(R.id.tvDisplay_name);
        tvUserName = findViewById(R.id.tvUserName);
        ivThumb = findViewById(R.id.ivThumb);
        tvFolCount = findViewById(R.id.tvFolCount);

        tvDisplay_name.setText(full_name);
        tvUserName.setText(usernameInsta);
        tvFolCount.setText(no_of_followers);
        //tvPk.setText(pk);

        Glide.with(this)
                .load(profile_pic_url)
                .into(ivThumb);

    }

    public void onLogout(View view) {
    }


}
