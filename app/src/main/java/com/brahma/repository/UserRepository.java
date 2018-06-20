package com.brahma.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.brahma.Room.RoomDatabase;
import com.brahma.Room.User;
import com.brahma.Room.UserDao;

import java.util.List;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> mUserInfo;

    public UserRepository(Application appplication){
        RoomDatabase db = RoomDatabase.getdatabase(appplication);
        mUserDao = db.userDao();
        mUserInfo = mUserDao.getUser();

    }


   public LiveData<List<User>> getmUserInfo(){
        return mUserInfo;
    }
    public void insert(User user){
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User,Void,Void> {

        private UserDao mAsyncTaskDao;
        public insertAsyncTask(UserDao mUserDao) {
            mAsyncTaskDao = mUserDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;

        }
    }
}
