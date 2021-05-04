package com.example.sonymz1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A temporary class that holds all the users ("fake database").
 *
 * @author Wendy Pau
 */
public class AllUsers {
    private Map<Integer,User> userMap;
    private User mainUser;

    public AllUsers() {
        this.userMap = new HashMap<>();
        //fillWithUsers();
    }

    /**
     * Temporary method to fill the map with users.
     */
    public void fillWithUsers(){
        addTestUser("User 1",0);
        addUser("User 2");
        addUser("User 3");
        addUser("User 4");
        addUser("User 5");
        addUser("User 6");
    }

    public void addTestUser(String name, int id){
        User user = new User(name,id);
        userMap.put(user.getId(),user);
    }

    /**
     * Add the user in the map and give the user an unique id.
     * @param name
     */
    public void addUser(String name){
        User user = new User(name,getUniqueID());
        userMap.put(user.getId(),user);
    }

    public void addMainUser(String name){
        int id = getUniqueID();
        mainUser = new User(name,id);
        userMap.put(id,mainUser);
    }

    /**
     * Genereate a random unique positive number that doesn´t exist in the map of users already.
     * @return a unique positive id
     */
    private int getUniqueID(){
        Random rand = new Random();
        int id = Math.abs(rand.nextInt());
        while (userMap.get(id) != null){
            id = Math.abs(rand.nextInt());
        }
        return id;
    }

    public User getMainUser() { return mainUser; }

    public void setMainUser(int mainUserID) { this.mainUser = userMap.get(mainUserID); }

    public Map<Integer, User> getUserMap() { return userMap; }
}
