package com.example.sonymz1.Model;

import com.example.sonymz1.Components.ChallengeComponent;
import com.example.sonymz1.Components.ScoreComponent;
import com.example.sonymz1.R;
import com.example.sonymz1.ScoreUpdateListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Felix
 * Class representing a challenge and information about a challenge e.g users, their scores, chalenge
 * name etc.
 */
public class Challenge implements ScoreUpdateListener {
    /**
     *  name for challenge, leader Board with playerIds and score, challenge components, if challenge
     *  is private or not, string for challenge description, challenge code to join,
     *  int for challenge card background pic, int for medal pic.
     */
    private String name;
    private Map<Integer, Integer> leaderBoard;
    private ArrayList<ChallengeComponent> components;
    private boolean isPrivate;
    private boolean isFinished;
    private String description;
    private String challengeCode;
    private int creatorId;
    private int[] adminIds;
    private int challengeBackground;
    private int medal;

    public Challenge(String name, Map<Integer, Integer> leaderBoard,
                     ArrayList<ChallengeComponent> components, boolean isPrivate, String description, int challengeBackground) {
        this.name = name;
        this.leaderBoard = leaderBoard;
        this.components = components;
        this.isPrivate = isPrivate;
        this.description = description;
        this.challengeBackground = R.drawable.run_challenge;
        this.isFinished = false;
    }

    public Challenge(String name, int challengeBackground, int medal) {
        this.name = name;
        this.leaderBoard = new HashMap<>();
        this.components = new ArrayList<>();
        this.isPrivate = false;
        this.description = "";
        this.challengeCode = generateCode(4);
        this.challengeBackground = R.drawable.run_challenge;
        this.medal = medal;
        this.isFinished = false;
    }

    public Challenge(String name) {
        this.name = name;
        this.leaderBoard = new HashMap<>();
        this.components = new ArrayList<>();
        this.isPrivate = false;
        this.challengeCode = generateCode(4);
        this.description = "";
        this.challengeBackground = R.drawable.run_challenge;
        this.isFinished = false;
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

    public void removePlayer(int playerId){
        this.leaderBoard.remove(playerId);
    }
    /**
     *
     * @param length the length of the randomly generated code
     * @return returns a random string of characters A-Z of the length specified
     */
    private String generateCode(int length){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char)(r.nextInt(26) + 'A');
            sb.append(c);
        }
        return sb.toString();
    }
    public void setCreatorId(int creatorId){
        this.creatorId = creatorId;
    }
    public int getCreatorId(){
        return creatorId;
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
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
    public String getChallengeCode() {
        return challengeCode;
    }

    public void setName(String name){
        this.name = name;
    }

    public void changeText(String text){
        description = text;
    }

    public int getChallengeBackground() {
        return challengeBackground;
    }

    public int getMedal() {
        return medal;
    }

    @Override
    public void updateScore(int mainUserId,int score) {
        //TODO getCurrentuserID
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
    public int getGoalScore(){
        ScoreComponent scoreComponent = getScoreComponent();
        if (scoreComponent != null){
            return scoreComponent.getGoalScore();
        }
        return 0;

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
