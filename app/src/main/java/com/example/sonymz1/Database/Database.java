package com.example.sonymz1.Database;

import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.Model.User;

import java.util.ArrayList;

/**
 * class to represent the database to the rest of the app
 * @author Felix
 */
public class Database {
    /**
     * Database uses singleton to only have one instance through out the app
     * the database has a reference to the online and the local database and uses them both in
     * different cases.
     */
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

    /**
     * saves a challenge to the online and local database
     * @param challenge the challenge that gets saved
     */
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
        ArrayList<User> users = lDb.getAllUsers();
        users.add(user);
        lDb.setAllUsers(users);
        oDb.saveUser(user);
    }

    /**
     * gets all challenges that a user is a part of. if the data already exists in the local db it
     * will return that data, otherwise update the local db with the online db.
     * @param user the user for which the challenges will be returned.
     * @param callback a databaseCallback that will get notified when the data is available in the local db
     */
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

    /**
     * gets all the users in the db
     * @param callback a databaseCallback that will get notified when the data is available in the local db
     */
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

    /**
     * method that returns all the users in the local database, should only be called if the method
     * of return type void first was called and the callback onCallback method has been called
     * @return returns a arraylist of all users in the local db
     */
    public ArrayList<User> getAllUsers(){
        return lDb.getAllUsers();
    }
    /**
     * method that returns all the challenges in the local database, should only be called if the method
     * of return type void first was called and the callback onCallback method has been called
     * @return returns a arraylist of all challenges in the local db
     */
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
