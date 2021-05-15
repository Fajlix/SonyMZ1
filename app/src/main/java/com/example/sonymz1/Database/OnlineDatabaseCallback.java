package com.example.sonymz1.Database;

import com.example.sonymz1.Model.Challenge;

import java.util.ArrayList;

interface OnlineDatabaseCallback {
    void onCallback(ArrayList<Challenge> challenges);
}
