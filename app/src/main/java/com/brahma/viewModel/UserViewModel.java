package com.brahma.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.brahma.Room.User;
import com.brahma.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel{

    private UserRepository mUserRepository;
    private LiveData<List<User>> mUserInfo;



    public UserViewModel(UserRepository repository) {
        super();
        mUserRepository = repository;
        mUserInfo = mUserRepository.getmUserInfo();
    }


    public LiveData<List<User>> getUserInfo(){
        return mUserInfo;
    }
    public void insert(User user){
        mUserRepository.insert(user);
    }
}
