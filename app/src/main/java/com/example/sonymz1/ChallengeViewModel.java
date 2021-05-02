package com.example.sonymz1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;

/**
 * @author Felix ,Viktor J
 * A class responsible for the communication between the challenges and views.
 */
public class ChallengeViewModel extends ViewModel {

    private Challenge challenge;
    private User mainUser;
    private Map<Integer,User> users;
    private AllUsers db = new AllUsers();
    private MutableLiveData<Map<Integer, Integer>> leaderBoard;

    public ChallengeViewModel() {
        this.challenge = LocalDatabase.getInstance().getActiveChallenge();
        this.users = db.getUserMap();
        this.mainUser = getUsers().get(2);
        this.leaderBoard = new MutableLiveData<>();
        if (challenge !=null){
        leaderBoard.setValue(challenge.getLeaderBoard());
        }
    }

    /**
     * Method for communication between Model and View, in this case Challenge and CreateChallengeFragment
     */
    public void createChallenge(String name, String description,boolean isPrivate, int[] playerIds) {
        challenge = new Challenge(name);
        challenge.setDescription(description);
        challenge.setPrivate(isPrivate);
        challenge.setCreatorId(mainUser.getId());
        addPlayers(playerIds);
        addPlayer(1,20);
        setLeaderBoard();
        LocalDatabase.getInstance().addChallenge(challenge);
        //TODO Add challengers
    }

    public void addPlayer(int playerId, int score){
        challenge.addPlayer(playerId, score);
        setLeaderBoard();
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
        setLeaderBoard();
    }


    public MutableLiveData<Map<Integer, Integer>> getLeaderBoard() { return leaderBoard; }

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
    public void setLeaderBoard(){
        if (challenge !=null){
            leaderBoard.setValue(challenge.getLeaderBoard());
        }
    }
}
