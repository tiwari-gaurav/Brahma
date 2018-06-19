package com.brahma.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;

@Database(entities = User.class,version = 1)

public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase{

    public abstract UserDao userDao();
     private static RoomDatabase INSTANCE;

     public static RoomDatabase getdatabase(final Context context){
         if(INSTANCE==null){
             synchronized (RoomDatabase.class){
                 if(INSTANCE==null){
                     INSTANCE= Room.databaseBuilder(context.getApplicationContext(),RoomDatabase.class,"user_database")
                             .build();
                 }
             }
         }
         return INSTANCE;
     }
}
