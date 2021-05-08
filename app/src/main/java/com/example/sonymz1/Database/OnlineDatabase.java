package com.example.sonymz1.Database;

import androidx.annotation.NonNull;

import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class OnlineDatabase {
    private static OnlineDatabase instance;
    DatabaseReference challengeRef;
    DatabaseReference usersRef;

    private OnlineDatabase() {
        challengeRef = FirebaseDatabase.getInstance().getReference().child("Challenges");
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public static OnlineDatabase getInstance() {
        if (instance == null)
            instance = new OnlineDatabase();
        return instance;
    }

    public ArrayList<Challenge> getChallenges(int user) {
        ArrayList<Challenge> challenges = new ArrayList<>();
        challengeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(snapshot.getValue());
                    Challenge challenge = gson.fromJson(jsonStr, Challenge.class);
                    if (challenge.getLeaderBoard().containsKey(user)) {
                        challenges.add(challenge);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return challenges;
    }

    public void saveChallenge(Challenge challenge) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(challenge);
        Map map = gson.fromJson(jsonStr, Map.class);
        challengeRef.child(challenge.getChallengeCode()).setValue(map);
    }
    public void saveUsers(Map<Integer, User> userMap){
        for (User user : userMap.values()) {
            saveUser(user);
        }
    }
    public void saveUser(User user){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(user);
        Map map = gson.fromJson(jsonStr, Map.class);
        usersRef.child(String.valueOf(user.getId())).setValue(map);
    }

    public void removeChallenge(Challenge challenge) {
        challengeRef.child(challenge.getChallengeCode()).removeValue();
    }

    /*public void setActiveChallenge(Challenge challenge) {
        this.activeChallenge = challenge;
    }

    public Challenge getActiveChallenge() {
        return activeChallenge;
    }

     */
}
