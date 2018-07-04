package com.brahma.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.brahma.Room.VideoEntity;
import com.brahma.repository.BrahmaRepository;

import java.util.List;

public class VideoViewModel extends ViewModel{

   private LiveData<List<VideoEntity>> mVideo;
   private BrahmaRepository mRepository;

    public VideoViewModel(BrahmaRepository repository){
        mRepository = repository;
        mVideo = mRepository.getmVideoDetails();
    }

    public LiveData<List<VideoEntity>> getmVideo() {
        return mVideo;
    }
}