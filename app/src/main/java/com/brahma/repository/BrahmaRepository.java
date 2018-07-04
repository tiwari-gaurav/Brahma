package com.brahma.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.brahma.AppExecutors;
import com.brahma.Room.RoomDatabase;
import com.brahma.Room.User;
import com.brahma.Room.UserDao;
import com.brahma.Room.VideoEntity;
import com.brahma.Room.VideoEntityDao;
import com.brahma.network.VideoNetworkDataSource;

import java.util.Date;
import java.util.List;

public class BrahmaRepository {

    private static final String LOG_TAG = BrahmaRepository.class.getSimpleName();

    private UserDao mUserDao;
    private VideoEntityDao mVideoEntityDao;
    private LiveData<List<User>> mUserInfo;
    private LiveData<List<VideoEntity>>mVideoDetails;
    private final AppExecutors mExecutors;
    private final VideoNetworkDataSource mVideoNetworkDataSource;
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static BrahmaRepository sInstance;

    public BrahmaRepository(UserDao userDao,VideoEntityDao videoEntityDao,AppExecutors executors, VideoNetworkDataSource videoNetworkDataSource){
        mUserDao = userDao;
        mVideoEntityDao = videoEntityDao;
        mExecutors=executors;
        mVideoNetworkDataSource = videoNetworkDataSource;

        mUserInfo = mUserDao.getUser();
        mVideoDetails = mVideoEntityDao.getVideoDetails();

        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        LiveData<List<VideoEntity>> networkData = mVideoNetworkDataSource.getmDownloadedVideo();
        networkData.observeForever(newVideosFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deletes old historical data
                deleteOldData();
                Log.d(LOG_TAG, "Old videos deleted");
                // Insert our new weather data into Sunshine's database
                mVideoEntityDao.bulkInsert(newVideosFromNetwork);
                Log.d(LOG_TAG, "New values inserted");
            });
        });

    }


    public synchronized static BrahmaRepository getInstance(
            UserDao userDao,VideoEntityDao videoEntityDao,AppExecutors executors, VideoNetworkDataSource videoNetworkDataSource) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new BrahmaRepository(userDao,videoEntityDao,executors, videoNetworkDataSource);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }


    /**
     * Deletes old weather data because we don't need to keep multiple days' data
     */
    private void deleteOldData() {

        mVideoEntityDao.deleteOldWeather();
    }


    public LiveData<List<User>> getmUserInfo(){
        return mUserInfo;
    }

    public LiveData<List<VideoEntity>> getmVideoDetails() {
        initializeData();
        return mVideoDetails;
    }

    public void insert(User user){
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User,Void,Void> {

        private UserDao mAsyncTaskDao;
        public insertAsyncTask(UserDao mUserDao) {
            mAsyncTaskDao = mUserDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;

        }
    }

    private void startFetchVideoService() {
        mVideoNetworkDataSource.startFetchVideoService();
    }

    private synchronized void initializeData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.

        mExecutors.diskIO().execute(() -> {

                startFetchVideoService();

        });
    }

}
