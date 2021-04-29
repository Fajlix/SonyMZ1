package com.example.sonymz1;

import com.example.sonymz1.Components.ChallengeComponent;
import com.example.sonymz1.Components.ScoreComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Felix
 * Class representing a challenge and information about a challenge e.g users, their scores, chalenge
 * name etc.
 */
public class Challenge implements ScoreUpdateListener{
    /**
     * name for challenge, leader Board with playerIds and score, challenge components, if challenge
     *  is private or not, string for challenge description, challenge code to join
     */
    private String name;
    private Map<Integer, Integer> leaderBoard;
    private ArrayList<ChallengeComponent> components;
    private boolean isPrivate;
    private String description;
    private int challengeCode;


    public Challenge(String name, Map<Integer, Integer> leaderBoard,
                     ArrayList<ChallengeComponent> components, boolean isPrivate, String description) {
        this.name = name;
        this.leaderBoard = leaderBoard;
        this.components = components;
        this.isPrivate = isPrivate;
        this.description = description;
    }

    public Challenge(String name) {
        this.name = name;
        this.leaderBoard = new HashMap<>();
        this.components = new ArrayList<>();
        this.isPrivate = false;
        this.description = "";
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Integer> getLeaderBoard() {
        return sortByValue(leaderBoard);
    }

    public void addPlayer(int playerId) {
        this.leaderBoard.put(playerId,0);
    }

    public void addPlayer(int playerId, int score) {
        this.leaderBoard.put(playerId, score);
    }

    /**
     *
     * @param unsortedMap the unsorted leader board
     * @return returns sorted version of unsortedMap by comparing values not keys Map(k,v)
     */
    private static Map<Integer, Integer> sortByValue(Map<Integer, Integer> unsortedMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Integer, Integer>> list =
                new LinkedList<>(unsortedMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        return sortedMap;
    }

    public ArrayList<ChallengeComponent> getComponents() {
        return components;
    }

    public void addComponent(ChallengeComponent component) {
        this.components.add(component);
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getDescription() {
        return description;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void updateScore(int mainUserId,int score) {
        //TODO getcurrentuserID
        leaderBoard.put(mainUserId,score);

    }
    public boolean checkIfGoalReached(){
        ScoreComponent scoreComponent = getScoreComponent();
        if (scoreComponent != null){
            int bestUser = leaderBoard.keySet().iterator().next();
            int currentScore = leaderBoard.get(bestUser);
            return currentScore >= scoreComponent.getGoalScore();
        }
        return false;
    }
    //Just returns the scoreComponent of all the components
    private ScoreComponent getScoreComponent(){
        for (ChallengeComponent cc :
                components) {
            if (cc instanceof ScoreComponent){
                return (ScoreComponent) cc;
            }
        }
        return null;
    }
}
