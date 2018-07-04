package com.brahma.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.brahma.AppExecutors;
import com.brahma.Room.VideoEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoNetworkDataSource {
    private static final String LOG_TAG = VideoNetworkDataSource.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static VideoNetworkDataSource sInstance;
    private final Context mContext;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<List<VideoEntity>> mDownloadedVideo;
    private final AppExecutors mExecutors;

    private VideoNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedVideo = new MutableLiveData<List<VideoEntity>>();
    }

    /**
     * Get the singleton for this class
     */
    public static VideoNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new VideoNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public LiveData<List<VideoEntity>> getmDownloadedVideo() {
        return mDownloadedVideo;
    }

    /**
     * Starts an intent service to fetch the weather.
     */
    public void startFetchVideoService() {
        Intent intentToFetch = new Intent(mContext, VideoIntentService.class);
        mContext.startService(intentToFetch);
        Log.d(LOG_TAG, "Service created");
    }

    public void fetchVideos() {
        Log.d(LOG_TAG, "Fetch weather started");
        mExecutors.networkIO().execute(() -> {
            APiInterface apiService = ApiClient.getClient().create(APiInterface.class);
            Call<List<VideoEntity>> call = apiService.getVideos();
            call.enqueue(new Callback<List<VideoEntity>>() {
                @Override
                public void onResponse(Call<List<VideoEntity>> call, Response<List<VideoEntity>> response) {
                    mDownloadedVideo.postValue(response.body());
                }

                @Override
                public void onFailure(Call<List<VideoEntity>> call, Throwable t) {

                }


            });

        });

    }
}
