package com.example.sonymz1.Model;

import com.example.sonymz1.R;

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
    private int profilePic;
    private List<User> friends;

    public User(String username, int id) {
        this.username = username;
        this.profilePic = R.drawable.logo;
        this.id = id;
    }

    public void setUsername(String username) { this.username = username; }

    public void setProfilePic(int profilePic) { this.profilePic = profilePic; }

    public void setFriends(List<User> friends) { this.friends = friends; }

    public String getUsername() { return username; }

    public int getId() { return id; }

    public int getProfilePic() { return profilePic; }

    public List<User> getFriends() { return friends; }
}