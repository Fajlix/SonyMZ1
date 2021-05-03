package com.example.sonymz1;

import java.util.ArrayList;

/**
 * @author Felix
 * Class representing the front of a database stored locally on a phone. This should store the challenges
 * that a user is a part of and also the currently active challenge meaning the challenge that is currently
 * displayed / the last challenge displayed.
 */
public class LocalDatabase {
    private static LocalDatabase instance;
    private ArrayList<Challenge> challenges;
    private Challenge activeChallenge;
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

    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }
    public void addChallenge(Challenge challenge){
        challenges.add(challenge);
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
