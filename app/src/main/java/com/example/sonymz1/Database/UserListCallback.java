package com.example.sonymz1.Database;

import com.example.sonymz1.Model.User;

import java.util.ArrayList;

interface UserListCallback {
    void onCallback(ArrayList<User> users);
}
