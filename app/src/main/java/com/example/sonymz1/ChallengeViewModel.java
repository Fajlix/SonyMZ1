package com.example.sonymz1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sonymz1.Components.CounterComponent;
import com.example.sonymz1.Database.AllUsers;
import com.example.sonymz1.Database.LocalDatabase;
import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.Model.User;

import java.util.Map;

/**
 * A class responsible for the communication between the challenges and views.
 *
 * @author Felix ,Viktor J, Wendy Pau
 */
public class ChallengeViewModel extends ViewModel {

    private Challenge challenge;
    private User mainUser;
    private Map<Integer,User> usersMap;
    private AllUsers usersDB;
    private MutableLiveData<Map<Integer, Integer>> leaderBoard = new MutableLiveData<>();

    public ChallengeViewModel() {
        LocalDatabase.getInstance().setActiveChallenge(new Challenge("ChallengeTest"));
        this.challenge = LocalDatabase.getInstance().getActiveChallenge();
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
        //addPlayer(1, 20); It wont work on my setPedestal method
        setLeaderBoard();
        LocalDatabase.getInstance().addChallenge(challenge);
        //TODO Add challengers
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

    private void update(){ leaderBoard.setValue(challenge.getLeaderBoard()); }

    public void addScore(int score) {
        //TODO maybe fix?
        //We have to know what type of challenge it is so that we can add the right type of score
        CounterComponent scoreComp = (CounterComponent) (challenge.getComponents().get(0));
        scoreComp.addCount(mainUser.getId(), score);
        update();
    }

    public void setLeaderBoard() {
        if (challenge != null)
            leaderBoard.setValue(challenge.getLeaderBoard());
    }

    public void setMainUser(int mainUserID) {
        usersDB.setMainUser(mainUserID);
        mainUser = usersDB.getMainUser();
    }

    public void addMainUser(String username) {
        usersDB.addMainUser(username);
        mainUser = usersDB.getMainUser();
    }

    public AllUsers getUsersDB() { return usersDB; }

    public void setUsersDB(AllUsers usersDB) {
        this.usersDB = usersDB;
        this.usersMap = usersDB.getUserMap();
    }

    public User getMainUser() { return mainUser; }

    public MutableLiveData<Map<Integer, Integer>> getLeaderBoard() { return leaderBoard; }

    public Map<Integer, User> getUsersMap() { return usersMap; }

    public String getName(){ return challenge.getName(); }

    public String getDescription(){ return challenge.getDescription(); }

    public Boolean isPrivate() { return challenge.isPrivate(); }

    public int getNumOfPlayers(){ return usersMap.size(); }

    public int getMainUserScore(){ return challenge.getLeaderBoard().get(mainUser.getId()); }

    public int getEndGoal(){ return challenge.getGoalScore(); }

    public void setChallengeName(String name){ challenge.setName(name); }

    public void setDescription(String desc){ challenge.setDescription(desc); }

    public void setPrivacy(boolean aPrivate){ challenge.setPrivate(aPrivate); }

    public String getCode(){ return String.valueOf(challenge.getChallengeCode()); }
}
