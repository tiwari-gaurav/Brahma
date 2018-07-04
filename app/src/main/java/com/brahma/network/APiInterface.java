package com.brahma.network;

import com.brahma.Room.VideoEntity;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APiInterface {
    @GET("/brahma/videos/")
    Call<List<VideoEntity>> getVideos();
}
