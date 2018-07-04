package com.brahma.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "video_details")

public class VideoEntity {

    @Ignore

    public VideoEntity( String title, String description, String state, String city, ArrayList<String> category, ArrayList<String> videoUrl, String duration, String image_url, String location, ArrayList<String> tag) {

        this.category = category;
        this.city = city;
        this.description = description;
        this.duration = duration;
        this.image_url = image_url;
        this.location = location;
        this.state = state;
        this.tag = tag;
        this.title = title;
        this.videoUrl = videoUrl;

    }

    public VideoEntity(int videoId, String title, String description, String state, String city, ArrayList<String> category, ArrayList<String> videoUrl, String duration, String image_url, String location, ArrayList<String> tag) {
        this.videoId = videoId;
        this.category = category;
        this.city = city;
        this.description = description;
        this.duration = duration;
        this.image_url = image_url;
        this.location = location;
        this.state = state;
        this.tag = tag;
        this.title = title;
        this.videoUrl = videoUrl;


    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int videoId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "category")
    private ArrayList<String> category;

    @ColumnInfo(name = "url")
    private ArrayList<String> videoUrl;

    @ColumnInfo(name = "duration")
    private String duration;

    @ColumnInfo(name = "image_url")
    private String image_url;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "tag")
    private ArrayList<String> tag;

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public ArrayList<String> getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(ArrayList<String> videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }
}
