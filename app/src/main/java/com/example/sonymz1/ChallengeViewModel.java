package com.example.sonymz1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;

/**
 * The viewmodel for challenges.
 *
 * @author Wendy Pau
 */
public class ChallengeViewModel extends ViewModel {

    private Challenge challenge;
    private User mainUser;
    private Map<Integer,User> users;
    private AllUsers db = AllUsers.getInstance();
    private MutableLiveData<Map<Integer, Integer>> leaderBoard = new MutableLiveData<>();

    public ChallengeViewModel() {
        this.challenge = new Challenge("Challenge"); // Temp set
        this.users = db.getUserMap();
        this.mainUser = getUsers().get(0); // Temp set
        challenge.addPlayer(mainUser.getId(),2); // Should be set in Challenge class
    }

    /**
     * Temporary method to add other challengers score.
     * @param playerId challengers id
     * @param score challengers score to add
     */
    public void addTestScore(int playerId, int score){
        challenge.addPlayer(playerId, score);
        leaderBoard.setValue(challenge.getLeaderBoard());
    }

    /**
     * Method for the main user to update their score.
     * @param score score to add
     */
    public void addScore(int score){
        challenge.addPlayer(mainUser.getId(), score);
        leaderBoard.setValue(challenge.getLeaderBoard());
    }

    public MutableLiveData<Map<Integer, Integer>> getLeaderBoard() { return leaderBoard; }

    public User getMainUser() { return mainUser; }

    public Map<Integer, User> getUsers() { return users; }
}
