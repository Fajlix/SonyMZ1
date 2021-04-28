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
        /*this.challenge = new Challenge("Challenge");
        this.challenge.setDescription("Run 100km before any of the other challengers");
        this.users = db.getUserMap();
        this.mainUser = getUsers().get(2);
        this.leaderboard = new MutableLiveData<>();
        leaderboard.setValue(challenge.getLeaderBoard());

         */
    }

    /**
     * Method for communication between Model and View, in this case Challenge and CreateChallengeFragment
     */
    public void createChallenge() {
        challenge = new Challenge("ta på mig");
        blaBla();
        //challenge.setDescription(createChallengeFragment.getDescription());
        //challenge.setPrivate(createChallengeFragment.isPrivate());
        //TODO Add challengers
    }

    public void addPlayer(int playerId, int score){
        challenge.addPlayer(playerId, score);
        leaderboard.setValue(challenge.getLeaderBoard());
    }

    /**
     * Because we can add multiple users in the fragment but only one user at a time in the model
     *
     * @param playerIDs
     */
    private void addPlayers(int[] playerIDs) {
        for (int i = 0; i < playerIDs.length; i++) {
            challenge.addPlayer(playerIDs[i]);
        }
    }

    public MutableLiveData<Map<Integer, Integer>> getLeaderboard() { return leaderboard; }

    public User getUser(int index) {return getUsers().get(index);}

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

    public void blaBla()
    {
        System.out.println(challenge.getName());
    }
}
