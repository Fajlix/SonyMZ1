package com.example.sonymz1;

import java.util.List;

/**
 * @author Wendy
 */
public class User {
    private String username;
    private final int id;
    private int profilePic;
    private List<User> friends;

    public User(String username, int id) {
        this.username = username;
        this.id = id;
        profilePic = R.drawable.logo;
    }

    public void setUsername(String username) { this.username = username; }

    public void setProfilePic(int profilePic) { this.profilePic = profilePic; }

    public void setFriends(List<User> friends) { this.friends = friends; }

    public String getUsername() { return username; }

    public int getId() { return id; }

    public int getProfilePic() { return profilePic; }

    public List<User> getFriends() { return friends; }
}