package com.example.sonymz1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sonymz1.Components.CounterComponent;
import com.example.sonymz1.Components.DistanceComponent;

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
        setLeaderBoard();
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
    private void update(){
        leaderBoard.setValue(challenge.getLeaderBoard());
    }
    public Map<Integer, User> getUsers() { return users; }
    public void addScore(int score) {
        //TODO maybe fix?
        //We have to know what type of challenge it is so that we can add the right type of score
        CounterComponent scoreComp = (CounterComponent) (challenge.getComponents().get(0));
        scoreComp.addCount(mainUser.getId(), score);
        update();
    }

    public String getName(){
        return challenge.getName();
    }

    public String getDescription(){
        return challenge.getDescription();
    }

    public Boolean isPrivate() {
        return challenge.isPrivate();
    }
    public void setLeaderBoard() {
        if (challenge != null)
            leaderBoard.setValue(challenge.getLeaderBoard());
    }
    public int getNumOfPlayers(){
        return users.size();
    }

    public int getMainUserScore(){
        return challenge.getLeaderBoard().get(mainUser.getId());
    }

    public int getEndGoal(){
        return challenge.getGoalScore();
    }

    public void setChallengeName(String name){
        challenge.setName(name);
    }

    public void setDescription(String desc){
        challenge.setDescription(desc);
    }

    public void setPrivacy(boolean aPrivate){
        challenge.setPrivate(aPrivate);
    }

    public String getCode(){
        return String.valueOf(challenge.getChallengeCode());
    }
}
