package com.brahma.utils;

import android.content.Context;

import com.brahma.AppExecutors;
import com.brahma.Room.RoomDatabase;
import com.brahma.network.VideoNetworkDataSource;
import com.brahma.repository.BrahmaRepository;
import com.brahma.ui.MainViewModelFactory;
import com.brahma.ui.UserViewModelFactory;

public class InjectorUtils {


    public static BrahmaRepository provideRepository(Context context) {
        RoomDatabase database = RoomDatabase.getdatabase(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        VideoNetworkDataSource networkDataSource =
                VideoNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return BrahmaRepository.getInstance(database.userDao(),database.videoEntityDao(), executors, networkDataSource);
    }

    public static VideoNetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return VideoNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static UserViewModelFactory provideUserDetailViewModelFactory(Context context) {
        BrahmaRepository repository = provideRepository(context.getApplicationContext());
        return new UserViewModelFactory(repository);
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {
        BrahmaRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }
}
