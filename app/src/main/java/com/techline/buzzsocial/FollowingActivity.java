package com.techline.buzzsocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FollowingActivity extends AppCompatActivity {


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
    private int no_of_following;

    ArrayList<Person> peopleList = new ArrayList<>();
    private String no_of_followers;
    int counter = 0;
    TextView tvData;


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

            no_of_following = extras.getInt("no_of_following");
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

        } else {
            return;
        }
        loadDataInListView(mListView);
        tvData = findViewById(R.id.tvData);
tvData.setText(no_of_following + " Fans who are not following you ");
    }

    private void loadDataInListView(ListView mListView) {
        //create people objects
        for (counter = 0; counter < followingPkList.size(); counter++) {
            Person personName = new Person((followingFullNameList.get(counter)),
                    (followingUserNameList.get(counter)), (followingPkList.get(counter)),
                    (followingPicUrlList.get(counter)));

            //add objects to arraylist
            peopleList.add(personName);
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
}
