package com.example.sonymz1.Components;

/**
 * @author Viktor J
 */
public class TimerComponent implements ScoreComponent{
    private int goalTimer;

    /**
     * Constructor for creating a component
     * @param goalTimer is the final total time parameter
     */
    public TimerComponent(int goalTimer) {
        this.goalTimer = goalTimer;
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