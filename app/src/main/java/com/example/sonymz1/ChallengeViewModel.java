package com.example.sonymz1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sonymz1.Components.CounterComponent;

import java.util.Map;

public class ChallengeViewModel extends ViewModel {

    private Challenge challenge;
    private User mainUser;
    private Map<Integer,User> users;
    private AllUsers db = new AllUsers();
    private MutableLiveData<Map<Integer, Integer>> leaderBoard;

    public ChallengeViewModel() {

        this.challenge = new Challenge("Challenge");
        this.challenge.setDescription("Run 100km before any of the other challengers");
        this.users = db.getUserMap();
        this.mainUser = getUsers().get(2);
        this.leaderBoard = new MutableLiveData<>();
        leaderBoard.setValue(challenge.getLeaderBoard());

        addFakeData();
        this.challenge.addComponent(new CounterComponent(this.leaderBoard.getValue().get(mainUser.getId()),this.challenge));
    }
    private void addFakeData(){
        addPlayer(getUser(1).getId(),6);
        addPlayer(getUser(2).getId(),5);
        addPlayer(getUser(3).getId(),10);
        addPlayer(getUser(4).getId(),4);
    }

    public void addPlayer(int playerId, int score){
        challenge.addPlayer(playerId, score);
        leaderBoard.setValue(challenge.getLeaderBoard());
    }

    public MutableLiveData<Map<Integer, Integer>> getLeaderBoard() { return leaderBoard; }

    public User getUser(int index) {return getUsers().get(index);}

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

    public int getNumOfPlayers(){
        return users.size();
    }

    public boolean isPrivate(){
        return challenge.isPrivate();
    }
}
