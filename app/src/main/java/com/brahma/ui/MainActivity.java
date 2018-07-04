package com.brahma.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brahma.R;
import com.brahma.Room.User;
import com.brahma.Room.VideoEntity;
import com.brahma.utils.InjectorUtils;
import com.brahma.viewModel.UserViewModel;
import com.brahma.viewModel.VideoViewModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ImageView mUserImage;
    private TextView mUserName,mUserEmail;
    private UserViewModel mUserViewModel;
    private VideoViewModel mVideoViewModel;
    private UserViewModelFactory mViewModelFactory;
    private MainViewModelFactory mMainViewModelFactory;
    private RecyclerView mVideosRecyclerView;
    private VideoAdapter mVideoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mVideosRecyclerView = (RecyclerView)findViewById(R.id.videos_recycle);
        setSupportActionBar(toolbar);
        // initialize viewModel
        mViewModelFactory = InjectorUtils.provideUserDetailViewModelFactory(this.getApplication());
        mUserViewModel = ViewModelProviders.of(this,mViewModelFactory).get(UserViewModel.class);

        getVideoData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        mUserImage = (ImageView)headerView.findViewById(R.id.userImage);
        mUserName =(TextView)headerView.findViewById(R.id.user_name);
        mUserEmail = (TextView)headerView.findViewById(R.id.place);
       mUserViewModel.getUserInfo().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                Log.e("userInfo",users.get(0).getEmail());
                mUserName.setText(users.get(0).getUserName());
                mUserEmail.setText(users.get(0).getEmail());
            }
        });

            if(getIntent().getStringExtra("picUrl")!=null)
            Glide.with(this).load(getIntent().getStringExtra("picUrl")).centerCrop().into(mUserImage);

    }

    private void getVideoData() {
        mVideoAdapter = new VideoAdapter( R.layout.news_list_item, this);
        mVideosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mVideosRecyclerView.setAdapter(mVideoAdapter );
        MainViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(this.getApplicationContext());
        mVideoViewModel = ViewModelProviders.of(this, factory).get(VideoViewModel.class);
        mVideoViewModel.getmVideo().observe(this, new Observer<List<VideoEntity>>() {
            @Override
            public void onChanged(@Nullable List<VideoEntity> videoEntities) {


                if(videoEntities!=null) {
                    mVideoAdapter.swapVideos(videoEntities);
                    Toast.makeText(MainActivity.this, videoEntities.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
