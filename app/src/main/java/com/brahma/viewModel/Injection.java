package com.brahma.viewModel;

import android.app.Application;
import android.content.Context;

import com.brahma.repository.UserRepository;

public class Injection {


    public static ViewModelfactory provideViewModelFactory(Application context) {
        UserRepository repository = new UserRepository(context);
        return new ViewModelfactory(repository);
    }
}
