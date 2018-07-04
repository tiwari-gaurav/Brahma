package com.brahma.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.brahma.repository.BrahmaRepository;
import com.brahma.viewModel.UserViewModel;

public class UserViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final BrahmaRepository mRepository;

    public UserViewModelFactory(BrahmaRepository repository){
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserViewModel(mRepository);
    }
}
