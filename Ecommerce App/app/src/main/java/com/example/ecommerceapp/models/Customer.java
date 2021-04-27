package com.example.ecommerceapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int CustID;
    @NonNull
    private String CutName;
    @NonNull
    private String Username;
    @NonNull
    private String Password;
    @NonNull
    private String Gender;
    @NonNull
    private String Birthdate;
    @NonNull
    private String Job;
    private String img_path;

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }



    public Customer() {
    }

    public int getCustID() {
        return CustID;
    }

    @NonNull
    public String getCutName() {
        return CutName;
    }

    public void setCutName(@NonNull String cutName) {
        CutName = cutName;
    }

    @NonNull
    public String getUsername() {
        return Username;
    }

    public void setUsername(@NonNull String username) {
        Username = username;
    }

    @NonNull
    public String getPassword() {
        return Password;
    }

    public void setPassword(@NonNull String password) {
        Password = password;
    }

    @NonNull
    public String getGender() {
        return Gender;
    }

    public void setGender(@NonNull String gender) {
        Gender = gender;
    }

    @NonNull
    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(@NonNull String birthdate) {
        Birthdate = birthdate;
    }

    @NonNull
    public String getJob() {
        return Job;
    }

    public void setJob(@NonNull String job) {
        Job = job;
    }
}
