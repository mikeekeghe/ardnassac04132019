package com.techline.buzzsocial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "shared preferences";

    SharedPreferences SP;
    private static final String TAG = "HomeActivity";
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent it = new Intent(HomeActivity.this, UserMediFeedActivity.class);
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
                    Intent itDash = new Intent(HomeActivity.this, UserMediFeedActivity.class);
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
                    startActivity(itDash);
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };
    private Bundle extras;
    private String full_name="",profile_pic_url=""
            ,usernameInsta="",pk="",profile_pic_id="";
    TextView tvDisplay_name, tvUserName, tvLogout;
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

            userNameStore = extras.getString("userNameStore");
            passWordStore = extras.getString("passWordStore");



        } else {
            return;
        }

        tvDisplay_name = findViewById(R.id.tvDisplay_name);
        tvUserName = findViewById(R.id.tvUserName);
        ivThumb = findViewById(R.id.ivThumb);
        tvLogout = findViewById(R.id.tvFolCount);
        btnFollowers = findViewById(R.id.btnFollowers);
        btnUnFollowers = findViewById(R.id.btnUnFollowers);

        tvDisplay_name.setText(full_name);
        String StrFol  = no_of_followers + " followers " + "||"+no_of_following + " Fans who are not following you ";
        tvUserName.setText(StrFol);
        tvLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SP = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = SP.edit();
                editor.clear().commit();
                editor.apply();
                Intent itLogout = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(itLogout);
                finish();
            }
        });
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
