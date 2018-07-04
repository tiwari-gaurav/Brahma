package com.brahma.viewModel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.brahma.Room.User;
import com.brahma.repository.BrahmaRepository;

import java.util.List;

public class UserViewModel extends ViewModel{

    private BrahmaRepository mUserRepository;
    private LiveData<List<User>> mUserInfo;



    public UserViewModel(BrahmaRepository repository) {
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
