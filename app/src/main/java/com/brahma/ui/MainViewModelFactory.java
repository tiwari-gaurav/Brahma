package com.brahma.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.brahma.repository.BrahmaRepository;
import com.brahma.viewModel.VideoViewModel;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final BrahmaRepository mRepository;

    public MainViewModelFactory(BrahmaRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new VideoViewModel(mRepository);
    }
}
