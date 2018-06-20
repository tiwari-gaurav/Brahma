package com.brahma.viewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.brahma.repository.UserRepository;

public class ViewModelfactory extends ViewModelProvider.NewInstanceFactory{
    private final UserRepository mRepository;

    public ViewModelfactory(UserRepository repository){
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserViewModel(mRepository);
    }
}
