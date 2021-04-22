package com.example.sonymz1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Felix
 */
public class Challenge {
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
    private int mImageResource2;
    private int mImageResource3;

    public Challenge(String name, Map<Integer, Integer> leaderBoard,
                     ArrayList<ChallengeComponent> components, boolean isPrivate, String description, int mImageResource2) {
        this.name = name;
        this.leaderBoard = leaderBoard;
        this.components = components;
        this.isPrivate = isPrivate;
        this.description = description;
        this.mImageResource2 = mImageResource2;
    }
    public Challenge(String name, int mImageResource2, int mImageResource3) {
        this.name = name;
        this.leaderBoard = new HashMap<>();
        this.components = new ArrayList<>();
        this.isPrivate = false;
        this.description = "You are first!";
        this.mImageResource2 = mImageResource2;
        this.mImageResource3 = mImageResource3;
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
        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

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

    public void changeText(String text){
        description = text;
    }

    public int getmImageResource2() {
        return mImageResource2;
    }

    public int getmImageResource3() {
        return mImageResource3;
    }


}
