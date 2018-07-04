package com.brahma.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;


@Dao
public interface VideoEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<VideoEntity> videoEntity);

    @Query("SELECT * from video_details")
    LiveData<List<VideoEntity>> getVideoDetails();

    @Query("DELETE FROM video_details")
    void deleteOldWeather();
}
