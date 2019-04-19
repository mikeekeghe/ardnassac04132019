package com.techline.buzzsocial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

class PersonListAdapterUnfollow extends ArrayAdapter<Person> {
//    public static final String MyPREFERENCES = "shared preferences";

    private static Context mContext;
//    private static SharedPreferences SP= PreferenceManager.getDefaultSharedPreferences(mContext);

    private static final String TAG = "PersonListAdapter";

    private int mResource;
    private int lastPosition = -1;
    private Context context;
    String pk;
    String user_name;
    ArrayList<String> UsersToUnfollowArrayList = new ArrayList<String>();
    //ViewHolder object
    PersonListAdapterUnfollow.ViewHolder holder;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView tvFull_name;
        TextView tvUser_name;
        ImageView ivImage;
        ImageButton ivImagebutton;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PersonListAdapterUnfollow(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;


    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the persons information
        String full_name = getItem(position).getFull_name();
       user_name = getItem(position).getUser_name();
        pk = getItem(position).getPk();
        String imgUrl = getItem(position).getImgURL();

        //create the view result for showing the animation
        final View result;




        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new PersonListAdapterUnfollow.ViewHolder();
            holder.tvFull_name = (TextView) convertView.findViewById(R.id.textView1);
            holder.tvUser_name = (TextView) convertView.findViewById(R.id.textView2);
            //holder.pk = (TextView) convertView.findViewById(R.id.textView3);
            holder.ivImage = (ImageView) convertView.findViewById(R.id.image);
            holder.ivImagebutton = (ImageButton) convertView.findViewById(R.id.btnFollow);
            holder.ivImagebutton.setTag(user_name);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (PersonListAdapterUnfollow.ViewHolder) convertView.getTag();
            result = convertView;
        }


       /* Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);*/
        lastPosition = position;

        holder.tvFull_name.setText(full_name);
        holder.tvUser_name.setText(user_name);
        //holder.pk.setText(pk);

        //create the imageloader object
        ImageLoader imageLoader = ImageLoader.getInstance();
        ;
        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(imgUrl, holder.ivImage, options);
        holder.ivImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UsersToUnfollowArrayList.add(getItem(position).getUser_name());

                Intent intent = new Intent(mContext.getApplicationContext(), FollowingActivity.class);

                intent.putExtra("flag", "UNFOLLOW");
                intent.putExtra("UsersToUnfollowArrayList", UsersToUnfollowArrayList);
                Log.d(TAG,"UsersToUnfollowArrayList" + UsersToUnfollowArrayList);
//                saveData();
                Toast.makeText(mContext, getItem(position).getUser_name(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
            }
        });
        return convertView;


    }

/*    private void saveData() {
        SharedPreferences.Editor editor = SP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(UsersToUnfollowArrayList);
        editor.putString("task list", json);
        editor.apply();
    }*/



    /**
     * Required for setting up the Universal Image loader Library
     */
    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}
