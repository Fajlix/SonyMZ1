package com.example.sonymz1.Database;

import com.example.sonymz1.Model.Challenge;

import java.util.ArrayList;

public interface DatabaseChallengeListCallback {
    void onCallback(ArrayList<Challenge> challenges);
}
