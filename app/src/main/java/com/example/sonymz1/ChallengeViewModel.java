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
        this.challenge.setDescription("Run 100km before any of the other challengers");
        this.users = db.getUserMap();
        this.mainUser = getUsers().get(2);
        this.leaderboard = new MutableLiveData<>();
        leaderboard.setValue(challenge.getLeaderBoard());


    }

    /**
     * Method for communication between Model and View, in this case Challenge and CreateChallengeFragment
     */
    public void createChallenge(String name, String description,boolean isPrivate, int[] playerIds) {
        challenge = new Challenge(name);
        challenge.setDescription(description);
        challenge.setPrivate(isPrivate);

        addPlayers(playerIds);
        //TODO Add challengers
    }

    public void addPlayer(int playerId, int score){
        challenge.addPlayer(playerId, score);
        leaderboard.setValue(challenge.getLeaderBoard());
    }

    /**
     * Because we can add multiple users in the fragment but only one user at a time in the model
     *
     * @param playerIDs an array of integers which all represents a player
     */
    private void addPlayers(int[] playerIDs) {
        for (int playerID : playerIDs) {
            challenge.addPlayer(playerID);
        }
    }


    public MutableLiveData<Map<Integer, Integer>> getLeaderBoard() { return leaderboard; }

    public User getUser(int index) {return getUsers().get(index);}

    //TODO This should not be here
    public User getMainUser() { return mainUser; }

    public Map<Integer, User> getUsers() { return users; }

    public String getName(){
        return challenge.getName();
    }

    public String getDescription(){
        return challenge.getDescription();
    }

    public Boolean isPrivate() {
        return challenge.isPrivate();
    }
}
