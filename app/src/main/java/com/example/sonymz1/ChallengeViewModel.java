package com.example.sonymz1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;

public class ChallengeViewModel extends ViewModel {

    private Challenge challenge;
    private User mainUser;
    private Map<Integer,User> users;
    private AllUsers db = new AllUsers();
    private MutableLiveData<Map<Integer, Integer>> leaderboard;

    public ChallengeViewModel() {
        this.challenge = new Challenge("Challenge");
        this.users = db.getUserMap();
        this.mainUser = getUsers().get(2);
        this.leaderboard = new MutableLiveData<>();
        leaderboard.setValue(challenge.getLeaderBoard());
    }

    public void addPlayer(int playerId, int score){
        challenge.addPlayer(playerId, score);
        leaderboard.setValue(challenge.getLeaderBoard());
    }

    public MutableLiveData<Map<Integer, Integer>> getLeaderboard() { return leaderboard; }

    public User getUser(int index) {return getUsers().get(index);}

    public User getMainUser() { return mainUser; }

    public Map<Integer, User> getUsers() { return users; }
}
