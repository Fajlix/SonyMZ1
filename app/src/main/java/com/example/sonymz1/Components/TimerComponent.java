package com.example.sonymz1.Components;

/**
 * @author Viktor J
 */
public class TimerComponent implements ScoreComponent{
    private int goalTimer;
    private boolean hasSeconds;

    /**
     * Constructor for creating a component
     * @param goalTimer is the final total time parameter
     */
    public TimerComponent(int goalTimer) {
        this.goalTimer = goalTimer;
    }

    public boolean HasSeconds() {
        return hasSeconds;
    }

    public void setHasSeconds(boolean hasSeconds) {
        this.hasSeconds = hasSeconds;
    }

    @Override
    public int getGoalScore() {
        return goalTimer;
    }
    public String getValue() {
        return String.valueOf(goalTimer);
    }
    public void setGoalTimer(int goalTimer) {
        this.goalTimer = goalTimer;
    }
}