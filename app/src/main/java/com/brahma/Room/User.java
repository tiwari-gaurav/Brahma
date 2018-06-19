package com.brahma.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users")
public class User {


   public  User(){

    }


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userid")
    private int id;

    @ColumnInfo(name = "username")
    private String userName;

    @ColumnInfo(name = "email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String name, String email){
        this.userName = name;
        this.email = email;
    }



}
