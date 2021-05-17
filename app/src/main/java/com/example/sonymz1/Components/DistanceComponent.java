package com.example.sonymz1.Components;

import com.example.sonymz1.ScoreUpdateListener;

/**
 * @author Felix
 */
public class DistanceComponent implements ScoreComponent{
    private int currentDistance;
    private int goalDistance;
    private ScoreUpdateListener parent;
    private DistanceType type;


    public DistanceComponent(int goalDistance) {
        this.goalDistance = goalDistance;
        type = DistanceType.km;
    }
    public DistanceComponent(){

    }

    public void updateParent(int mainUserId){
        parent.updateScore(mainUserId,currentDistance);
    }

    @Override
    public int getGoalScore() {
        return goalDistance;
    }

    public int getScore() {
        return currentDistance;
    }

    public String getValue() {
        return String.valueOf(currentDistance);
    }

    public void addDistance(int mainUserId,int distance){
        currentDistance += distance;
        updateParent(mainUserId);
    }

    public void setGoalDistance(int goalDistance) {
        this.goalDistance = goalDistance;
    }

    public DistanceType getType() {
        return type;
    }

    public void setType(DistanceType type) {
        this.type = type;
    }

    public void setParent(ScoreUpdateListener scoreUpdateListener)
    {
        this.parent = scoreUpdateListener;
    }

    public void setCurrentDistance(int currentDistance) {
        this.currentDistance = currentDistance;
    }
}
