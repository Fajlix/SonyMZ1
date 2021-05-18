package com.example.sonymz1.Database;

import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.Model.User;

import java.util.ArrayList;

/**
 * @author Felix
 * Class representing the front of a database stored locally on a phone. This should store the challenges
 * that a user is a part of and also the currently active challenge meaning the challenge that is currently
 * displayed / the last challenge displayed.
 */
class LocalDatabase {
    private static LocalDatabase instance;
    private ArrayList<Challenge> challenges;
    private Challenge activeChallenge;
    private ArrayList<User> allUsers;
    private ArrayList<Challenge> allChallenges;
    private LocalDatabase(){
        if (challenges ==null){
            challenges = new ArrayList<>();
        }
    }

    public static LocalDatabase getInstance(){
        if (instance == null)
            instance = new LocalDatabase();
        return instance;
    }

    public ArrayList<Challenge> getAllChallenges() { return allChallenges; }

    public void setAllChallenges(ArrayList<Challenge> allChallenges) {
        this.allChallenges = allChallenges;
    }

    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }
    public void addChallenge(Challenge challenge){
        challenges.add(challenge);
    }

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }

    public void setChallenges(ArrayList<Challenge> challenges) {
        this.challenges = challenges;
    }
    public ArrayList<User> getAllUsers(){
        return allUsers;
    }

    public void removeChallenge(Challenge challenge){
        challenges.remove(challenge);
    }
    public void setActiveChallenge(Challenge challenge){
        this.activeChallenge = challenge;
    }
    public Challenge getActiveChallenge(){
        return activeChallenge;
    }
}
