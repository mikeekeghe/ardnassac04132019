package com.techline.buzzsocial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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
            followersFullNameList = extras.getStringArrayList("followersFullNameList");
            followersPkList = extras.getStringArrayList("followersPkList");
            no_of_following = String.valueOf(extras.getInt("no_of_following"));
            followingUserNameList = extras.getStringArrayList("followingUserNameList");
            followingPicUrlList = extras.getStringArrayList("followingPicUrlList");
            followingFullNameList = extras.getStringArrayList("followingFullNameList");
            followingPkList = extras.getStringArrayList("followingPkList");
            Log.d(TAG, "my full_name from Extra is :" + full_name);
            Log.d(TAG, "my profile_pic_url from Extra is :" + profile_pic_url);
            Log.d(TAG, "my usernameInsta from Extra is :" + usernameInsta);
            Log.d(TAG, "my pk from Extra is :" + pk);
            Log.d(TAG, "my profile_pic_id from Extra is :" + profile_pic_id);
            Log.d(TAG, "my no_of_followers from Extra is :" + no_of_followers);
            Log.d(TAG, "my followersUserNameList from Extra is :" + followersUserNameList);
            Log.d(TAG, "my followersPicUrlList from Extra is :" + followersPicUrlList);
            Log.d(TAG, "my followersFullNameList from Extra is :" + followersFullNameList);
            Log.d(TAG, "my followersPkList from Extra is :" + followersPkList);
            Log.d(TAG, "my no_of_following from Extra is :" + no_of_following);
            Log.d(TAG, "my followingUserNameList from Extra is :" + followingUserNameList);
            Log.d(TAG, "my followingPicUrlList from Extra is :" + followingPicUrlList);
            Log.d(TAG, "my followingFullNameList from Extra is :" + followingFullNameList);
            Log.d(TAG, "my followingPkList from Extra is :" + followingPkList);
            userNameStore = extras.getString("userNameStore");
            passWordStore = extras.getString("passWordStore");
            Log.d(TAG, "my userNameStore from Extra is :" + userNameStore);
            Log.d(TAG, "my passWordStore from Extra is :" + passWordStore);


        } else {
            return;
        }

        tvDisplay_name = findViewById(R.id.tvDisplay_name);
        tvUserName = findViewById(R.id.tvUserName);
        ivThumb = findViewById(R.id.ivThumb);
        tvFolCount = findViewById(R.id.tvFolCount);
        btnFollowers = findViewById(R.id.btnFollowers);
        btnUnFollowers = findViewById(R.id.btnUnFollowers);

        tvDisplay_name.setText(full_name);
        tvUserName.setText(usernameInsta);
        tvFolCount.setText(no_of_followers);
        //tvPk.setText(pk);

        Glide.with(this)
                .load(profile_pic_url)
                .into(ivThumb);

        btnFollowers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(HomeActivity.this, FollowersActivity.class);
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
                finish();
            }
        });
  btnUnFollowers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(HomeActivity.this, FollowingActivity.class);
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
                it.putExtra("flag", "UNFOLLOW");
                startActivity(it);
                finish();
            }
        });


        //action bar starts
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(full_name);

        ImageView topimageview =  mCustomView
                .findViewById(R.id.imageView1);
        topimageview.setImageURI(Uri.parse(profile_pic_url));


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        //ction bar ends
    }

    public void onLogout(View view) {
    }


}
