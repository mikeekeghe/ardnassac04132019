package com.techline.buzzsocial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramFollowRequest;
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsernameResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;

public class FollowingActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "shared preferences";

    SharedPreferences SP;
    ImageButton btnFollow;

    private static final String TAG = "FOLLOWING";
    private Bundle extras;
    private String full_name = "", profile_pic_url = "", usernameInsta = "", pk = "", profile_pic_id = "";
    ArrayList<String> followersUserNameList = new ArrayList<String>();
    ArrayList<String> followersPicUrlList = new ArrayList<String>();
    ArrayList<String> followersPkList = new ArrayList<String>();
    ArrayList<String> followersFullNameList = new ArrayList<String>();

    ArrayList<String> followingUserNameList = new ArrayList<String>();
    ArrayList<String> followingPicUrlList = new ArrayList<String>();
    ArrayList<String> followingPkList = new ArrayList<String>();
    ArrayList<String> followingFullNameList = new ArrayList<String>();
    ArrayList<String> UsersToUnFollowArrayList = new ArrayList<String>();

    private String no_of_following;

    ArrayList<Person> peopleList = new ArrayList<>();
    private String no_of_followers;
    int counter = 0;
    TextView tvData;
    private Context mContext;
    private String userNameStore="", passWordStore="", flag="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        ListView mListView = (ListView) findViewById(R.id.listView);

        extras = getIntent().getExtras();
        if (extras != null) {

            full_name = extras.getString("full_name");
            profile_pic_url = extras.getString("profile_pic_url");
            usernameInsta = extras.getString("usernameInsta");
            pk = extras.getString("pk");
            profile_pic_id = extras.getString("profile_pic_id");
            no_of_followers = extras.getString("no_of_followers");
            followersUserNameList = extras.getStringArrayList("followersUserNameList");
            followersPicUrlList = extras.getStringArrayList("followersPicUrlList");
            followersFullNameList = extras.getStringArrayList("followersFullNameList");
            followersPkList = extras.getStringArrayList("followersPkList");

            no_of_following = extras.getString("no_of_following");
            followingUserNameList = extras.getStringArrayList("followingUserNameList");
            UsersToUnFollowArrayList = extras.getStringArrayList("UsersToUnfollowArrayList");
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
            flag = extras.getString("flag");
            UsersToUnFollowArrayList = extras.getStringArrayList("UsersToUnfollowArrayList");
            Log.d(TAG, "my userNameStore from Extra is :" + userNameStore);
            Log.d(TAG, "my passWordStore from Extra is :" + passWordStore);
            Log.d(TAG, "my flag from Extra is :" + flag);
            Log.d(TAG, "my UsersToUnFollowArrayList from Extra is :" + UsersToUnFollowArrayList);

        } else {
            return;
        }
        loadDataInListView(mListView);
        tvData = findViewById(R.id.tvData);
tvData.setText(no_of_following + " Fans who are not following you ");
    }

    private void loadDataInListView(ListView mListView) {
        //create people objects
        if (followingPkList !=null){
            for (counter = 0; counter < followingPkList.size(); counter++) {
                Person personName = new Person((followingFullNameList.get(counter)),
                        (followingUserNameList.get(counter)), (followingPkList.get(counter)),
                        (followingPicUrlList.get(counter)));

                //add objects to arraylist
                peopleList.add(personName);
        }

        }
        //use new arrayList to populate listview
        PersonListAdapterUnfollow adapter = new PersonListAdapterUnfollow(this, R.layout.adapter_view_layout_2, peopleList);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        Intent it = new Intent(FollowingActivity.this, HomeActivity.class);
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
        it.putExtra("followingPicUrlList", followingPicUrlList);;
        it.putExtra("followingFullNameList", followingFullNameList);
        it.putExtra("followingPkList", followingPkList);

        startActivity(it);
        finish();

    }

    public void onClickDone(View view) {
        Log.d(TAG,"inside onClicDoneButon");
        boolean status =false;
        loadData();
        for (int x = 0; x < UsersToUnFollowArrayList.size(); x++) {
            String user_to_unfollow = UsersToUnFollowArrayList.get(x);
            status = unfollowMyUser(userNameStore, passWordStore,user_to_unfollow);
        }
        Log.d(TAG,"inside status >>"+ status);

        Toast.makeText(mContext, "unfollowed Succesfully", Toast.LENGTH_SHORT).show();

    }

    private void loadData() {
        SharedPreferences.Editor editor = SP.edit();
        Gson gson = new Gson();
        String json = SP.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        UsersToUnFollowArrayList = gson.fromJson(json, type);
        if (UsersToUnFollowArrayList == null) {
            UsersToUnFollowArrayList = new ArrayList<>();
        }
    }

    public boolean unfollowMyUser(String userNameStore, String passWordStore, String user_to_unfollow)

    {
        boolean answer =false;
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

    public void onClickFollowButonSwitcher(View view) {
        btnFollow = findViewById(R.id.btnFollow);
        if (flag == "FOLLOW"){
            Log.d(TAG,"inside flag checker >> follow");
            btnFollow.setImageResource(R.drawable.btn_unfollow);
            Toast.makeText(mContext, "Followed Succesfully", Toast.LENGTH_SHORT).show();
        }else if (flag == "UNFOLLOW"){
            Log.d(TAG,"inside flag checker >> follow");
            btnFollow.setImageResource(R.drawable.btn_follow);
            Toast.makeText(mContext, "Un-Followed Succesfully", Toast.LENGTH_SHORT).show();
        }else{

        }
    }

}
