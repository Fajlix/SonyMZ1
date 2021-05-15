package com.example.sonymz1.Database;

import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.Model.User;

import java.util.ArrayList;

public class Database {
    private static Database instance;
    OnlineDatabase oDb;
    LocalDatabase lDb;

    private Database() {
        oDb = OnlineDatabase.getInstance();
        lDb = LocalDatabase.getInstance();
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }
    public void saveChallenge(Challenge challenge){
        oDb.saveChallenge(challenge);
        lDb.addChallenge(challenge);
        lDb.setActiveChallenge(challenge);
    }
    public void setActiveChallenge(Challenge challenge){
        lDb.setActiveChallenge(challenge);
    }
    public Challenge getActiveChallenge(){
        return lDb.getActiveChallenge();
    }
    public void saveUser(User user){
        oDb.saveUser(user);
    }
    public void getChallenges(int user, DatabaseCallback callback){
        if (lDb.getChallenges()!= null && lDb.getChallenges().size()>0){
            callback.onCallback();
        }
        else {
            oDb.getChallenges(user, challenges -> {
                lDb.setChallenges(challenges);
                callback.onCallback();
            });
        }
    }
    public void getAllUsers(DatabaseCallback callback){
        if(lDb.getAllUsers()!= null && lDb.getAllUsers().size()>0){
            callback.onCallback();
        }
        else{
            oDb.getAllUsers(users -> {
                lDb.setAllUsers(users);
                callback.onCallback();
            });
        }
    }
    public ArrayList<User> getAllUsers(){
        return lDb.getAllUsers();
    }
    public ArrayList<Challenge> getChallenges(){
        return lDb.getChallenges();
    }
    public User getUser(int userId){
        for (User user : lDb.getAllUsers()) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }


}
