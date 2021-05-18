package com.example.sonymz1.Components;

import com.example.sonymz1.ScoreUpdateListener;

/**
 * @author Viktor J
 */
public class TimerComponent implements ScoreComponent{
    private int currentTimer;
    private int goalTimer;
    private ScoreUpdateListener parent;

    public TimerComponent(int goalTimer) {
        this.goalTimer = goalTimer;
    }

    public void updateParent(int mainUserId){
        parent.updateScore(mainUserId,currentTimer);
    }

    @Override
    public int getGoalScore() {
        return goalTimer;
    }

    public int getScore() {
        return currentTimer;
    }

    public String getValue() {
        return String.valueOf(currentTimer);
    }

    public void addTimer(int mainUserId,int timer){
        currentTimer += timer;
        updateParent(mainUserId);
    }

    public void setGoalTimer(int goalTimer) {
        this.goalTimer = goalTimer;
    }

    public void setParent(ScoreUpdateListener scoreUpdateListener)
    {
        this.parent = scoreUpdateListener;
    }

    public void setCurrentTimer(int currentTimer) {
        this.currentTimer = currentTimer;
    }
}