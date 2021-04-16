package com.example.sonymz1;

import java.util.ArrayList;

public class Challenge {
    private String name;
    private ArrayList<Integer> playerIds;
    private ArrayList<ChallengeComponent> components;

    public Challenge(String name, ArrayList<Integer> playerIds, ArrayList<ChallengeComponent> components) {
        this.name = name;
        this.playerIds = playerIds;
        this.components = components;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getPlayerIds() {
        return playerIds;
    }

    public void addPlayer(int playerId) {
        this.playerIds.add(playerId);
    }

    public ArrayList<ChallengeComponent> getComponents() {
        return components;
    }

    public void addComponent(ChallengeComponent component) {
        this.components.add(component);
    }
}
