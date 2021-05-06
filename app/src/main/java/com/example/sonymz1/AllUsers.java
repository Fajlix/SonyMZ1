package com.example.sonymz1;

import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A temporary class that holds all the users ("fake database").
 *
 * @author Wendy Pau
 */
public class AllUsers {
    private static AllUsers instance;
    private Map<Integer,User> userMap;

    public AllUsers() {
        this.userMap = new HashMap<>();
        fillWithUsers();
    }

    public static AllUsers getInstance(){
        if (AllUsers.instance == null){
            AllUsers.instance = new AllUsers();
        }
        return AllUsers.instance;
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
        saveUsers(userMap);

    }
    public void saveData(User user){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(user);
        Map map = gson.fromJson(jsonStr, Map.class);
        System.out.println(jsonStr);
        FirebaseDatabase.getInstance().getReference().child("Challenges").child(String.valueOf(user.getId())).setValue(map);
    }
    public void saveUsers(Map<Integer,User> userMap){
        Gson gson = new Gson();
        for (int key : userMap.keySet()) {
            String jsonStr = gson.toJson(userMap.get(key));
            Map map = gson.fromJson(jsonStr, Map.class);
            System.out.println(jsonStr);
            FirebaseDatabase.getInstance().getReference().child("Users").child(String.valueOf(userMap.get(key).getId())).setValue(map);
        }
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

    public Map<Integer, User> getUserMap() { return userMap; }
}
