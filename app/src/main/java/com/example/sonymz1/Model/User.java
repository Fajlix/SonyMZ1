package com.example.sonymz1.Model;

import com.example.sonymz1.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Holds the user data. When a new user is created, a default profile pic is set. The user
 * can change their profile pic later in the settings.
 *
 * @author Wendy Pau
 */
public class User {
    private String username;
    private final int id;
    private List<User> friends;

    public User(String username, int id) {
        this.username = username;
        this.id = id;
        this.friends = new ArrayList<>();
    }

    public void setUsername(String username) { this.username = username; }


    public void setFriends(List<User> friends) { this.friends = friends; }

    public String getUsername() { return username; }

    public int getId() { return id; }

    public List<User> getFriends() { return friends; }
}