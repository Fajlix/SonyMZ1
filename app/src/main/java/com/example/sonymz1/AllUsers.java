package com.example.sonymz1;

import java.util.HashMap;
import java.util.Map;

public class AllUsers {

    private Map<Integer,User> userMap;

    public AllUsers() {
        this.userMap = new HashMap<>();
        userMap.put(1, new User("User 1", 1));
        userMap.put(2, new User("User 2", 2));
        userMap.put(3, new User("User 3", 3));
        userMap.put(4, new User("User 4", 4));
        userMap.put(5, new User("User 5", 5));
        userMap.put(6, new User("User 6", 6));
    }

    public Map<Integer, User> getUserMap() { return userMap; }
}
