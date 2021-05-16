package com.example.sonymz1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sonymz1.Components.ChallengeComponent;
import com.example.sonymz1.Components.CounterComponent;
import com.example.sonymz1.Components.DistanceComponent;
import com.example.sonymz1.Database.Database;
import com.example.sonymz1.Database.DatabaseCallback;
import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.Model.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * A class responsible for the communication between the challenges and views.
 *
 * @author Felix ,Viktor J, Wendy Pau, Jonathan
 */
public class ChallengeViewModel extends ViewModel {

    private Challenge challenge;
    private User mainUser;
    private MutableLiveData<Map<Integer, Integer>> leaderBoard = new MutableLiveData<>();
    private ArrayList<ChallengeComponent> components = new ArrayList<>();


    public ChallengeViewModel() {
        updateChallenge();
    }
    /**
     * sets the challenge that should be displayed by getting the current active challenge from the
     * database
     */
    public void updateChallenge(){
        this.challenge = Database.getInstance().getActiveChallenge();
        setLeaderBoard();
    }

    /**
     * Method for communication between Model and View, in this case Challenge and CreateChallengeFragment
     */
    public void createChallenge(String name, String description, boolean isPrivate, int[] playerIds) {
        challenge = new Challenge(name);
        challenge.setDescription(description);
        challenge.setPrivate(isPrivate);
        challenge.setCreatorId(mainUser.getId());
        addPlayers(playerIds);
        //addPlayer(1, 20); It wont work on my setPedestal method
        setLeaderBoard();
        addComponents();
        Database.getInstance().saveChallenge(challenge);
        Database.getInstance().setActiveChallenge(challenge);
        //TODO Add challengers
    }

    private void addComponents() {
        if (components.size()>0) {
            for (int i = 0; i < components.size(); i++) {
                challenge.addComponent(components.get(i));
            }
        }
        else
            challenge.addComponent(new DistanceComponent(50));
    }

    public void addComponent(ChallengeComponent component) {
        components.add(component);
    }

    /**
     * Temporary method to add other challengers score.
     *
     * @param playerId challengers id
     * @param score    challengers score to add
     */
    public void addTestScore(int playerId, int score) {
        challenge.addPlayer(playerId, score);
        leaderBoard.setValue(challenge.getLeaderBoard());
    }

    /**
     * Method for the main user to update their score.
     *
     * @param score score to add
     */
    public void addPlayer(int playerId, int score) {
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

    public void removePlayers(ArrayList<Integer> userIds){
        for (int i = 0; i < userIds.size(); i++) {
            challenge.removePlayer(userIds.get(i));
        }
        Database.getInstance().saveChallenge(challenge);
        setLeaderBoard();
    }

    private void update(){ leaderBoard.setValue(challenge.getLeaderBoard()); }

    public void addScore(int score) {
        //TODO maybe fix?
        //We have to know what type of challenge it is so that we can add the right type of score
        CounterComponent scoreComp = (CounterComponent) (challenge.getComponents().get(0));
        scoreComp.addCount(mainUser.getId(), score);
        if(challenge.checkIfGoalReached()){
            challenge.setFinished(true);
        }
        update();
    }

    public void setLeaderBoard() {
        if (challenge != null)
            leaderBoard.setValue(challenge.getLeaderBoard());
    }

    public void setMainUser(int mainUserID, DatabaseCallback callback) {
        Database.getInstance().getAllUsers(() -> {
            mainUser = Database.getInstance().getUser(mainUserID);
            callback.onCallback();
        });
    }

    //TODO This should not be here
    public User getMainUser() {
        return mainUser;
    }

    public MutableLiveData<Map<Integer, Integer>> getLeaderBoard() {
        return leaderBoard;
    }

    public String getName() {
        return challenge.getName();
    }

    public String getDescription() {
        return challenge.getDescription();
    }

    public Boolean isPrivate() {
        return challenge.isPrivate();
    }

    public int getMainUserScore() {
        return challenge.getLeaderBoard().get(mainUser.getId());
    }

    public int getEndGoal() {
        return challenge.getGoalScore();
    }

    public void setChallengeName(String name) {
        challenge.setName(name);
    }

    public void setDescription(String desc) {
        challenge.setDescription(desc);
    }

    public void setPrivacy(boolean aPrivate) {
        challenge.setPrivate(aPrivate);
    }

    public String getCode() {
        return String.valueOf(challenge.getChallengeCode());
    }

    public void newMainUser(String name,DatabaseCallback callback){

        Database.getInstance().getAllUsers(() -> {
            Random rand = new Random();
            int id = Math.abs(rand.nextInt());
            while(!checkUnique(Database.getInstance().getAllUsers(),id)){
                id = Math.abs(rand.nextInt());
            }
            mainUser = new User(name,id);
            Database.getInstance().saveUser(mainUser);
            callback.onCallback();
        });
    }
    public int getCreatorId(){return challenge.getCreatorId();}

    public User getCreatorName(){
        return Database.getInstance().getUser(getCreatorId());
    }
    private boolean checkUnique(ArrayList<User> users,int id){
        for (User user :
                users) {
            if (user.getId() == id){
                return false;
            }
        }
        return true;
    }

    public boolean mainUserIsAdmin(){
        return mainUser.getId() == getCreatorId() || challenge.getAdminIds().contains(mainUser.getId());
    }

    public int getNumOfAdmins() {
        if (challenge.getAdminIds()==null){
            return 0;
        }
        return challenge.getAdminIds().size();
    }

    public ArrayList<Integer> getAdmins() {
        return challenge.getAdminIds();
    }

    public boolean mainUserIsCreator() {
        return mainUser.getId() == getCreatorId();
    }

    public void addAdmins(ArrayList<Integer> checkedUserIDs) {
        for (int i = 0; i < checkedUserIDs.size(); i++) {
            challenge.addAdmin(checkedUserIDs.get(i));
        }
    }

    public void removeAdmins(ArrayList<Integer> checkedUserIDs) {
        for (int i = 0; i < checkedUserIDs.size(); i++) {
            challenge.removeAdmin(checkedUserIDs.get(i));
        }
    }
}
