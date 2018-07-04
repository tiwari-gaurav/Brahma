package com.brahma.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.brahma.utils.InjectorUtils;

public class VideoIntentService extends IntentService {
    private static final String LOG_TAG = VideoIntentService.class.getSimpleName();

    public VideoIntentService() {
        super("SunshineSyncIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(LOG_TAG, "Intent service started");
        VideoNetworkDataSource networkDataSource =
                InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchVideos();

    }
}
