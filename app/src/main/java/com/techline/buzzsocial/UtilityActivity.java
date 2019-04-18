package com.techline.buzzsocial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramFollowRequest;
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.InstagramUnfollowRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsernameResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;

public class UtilityActivity extends AppCompatActivity {

    private static final String TAG = "UTILITY";
    private boolean answer = false;

    private Bundle extras;
    private String full_name="",profile_pic_url="",user_to_follow=""
            ,usernameInsta="",pk="",profile_pic_id="",user_to_unfollow="";
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
    ArrayList<String> UsersToFollowArrayList = new ArrayList<String>();
    ArrayList<String> UsersToUnfollowArrayList = new ArrayList<String>();
    private String no_of_following;
    private String userNameStore="", passWordStore="", flag="";
    private boolean status = false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            user_to_follow = extras.getString("user_to_follow");
            user_to_unfollow = extras.getString("user_to_unfollow");
            flag = extras.getString("flag");
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
            Log.d(TAG, "my user_to_follow from Extra is :" + user_to_follow);
            userNameStore = extras.getString("userNameStore");
            passWordStore = extras.getString("passWordStore");
            Log.d(TAG, "my userNameStore from Extra is :" + userNameStore);
            Log.d(TAG, "my followingPkList from Extra is :" + passWordStore);
            Log.d(TAG, "my flag from Extra is :" + flag);


        } else {
            return;
        }

        if (flag == "FOLLOW"){
            Log.d(TAG,"inside flag checker >> follow");
            // add username to UsersToFollowArrayList
            //UsersToFollowArrayList.add(user_to_follow);
            //status = followMyUser(userNameStore,passWordStore);
            //Log.d(TAG,"followMyUser status >> "+status);
            Intent intent = new Intent(mContext.getApplicationContext(), FollowersActivity.class);
            mContext.startActivity(intent);
            Toast.makeText(mContext, "Followed Succesfully", Toast.LENGTH_SHORT).show();
        }else if (flag == "UNFOLLOW"){
            Log.d(TAG,"inside flag checker >> follow");
//            UsersToUnfollowArrayList.add(user_to_unfollow);

//            status = unfollowMyUser(userNameStore,passWordStore);
//            Log.d(TAG,"unfollowMyUser status >> "+status);
            Intent intent = new Intent(mContext.getApplicationContext(), FollowingActivity.class);
            mContext.startActivity(intent);
            Toast.makeText(mContext, "Un-Followed Succesfully", Toast.LENGTH_SHORT).show();
        }else{

        }

    }

    private boolean unfollowMyUser(String userNameStore, String passWordStore) {

        //unfollow request
        Instagram4Android instagram = Instagram4Android.builder().username(userNameStore).password(passWordStore).build();
        InstagramSearchUsernameResult result = null;
        try {
            result = instagram.sendRequest(new InstagramSearchUsernameRequest(user_to_follow));
        } catch (IOException e) {
            e.printStackTrace();
        }
        InstagramUser user = result.getUser();
        try {
            instagram.sendRequest(new InstagramUnfollowRequest(user.getPk()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        answer = true;
        return answer;
    }

    public boolean followMyUser(String userNameStore, String passWordStore)

    {
        //follow request
        Instagram4Android instagram = Instagram4Android.builder().username(userNameStore).password(passWordStore).build();
        InstagramSearchUsernameResult result = null;
        try {
            result = instagram.sendRequest(new InstagramSearchUsernameRequest(user_to_unfollow));
        } catch (IOException e) {
            e.printStackTrace();
        }
        InstagramUser user = result.getUser();
        try {
            instagram.sendRequest(new InstagramFollowRequest(user.getPk()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        answer = true;
        return answer;
    }

}
