package com.example.sonymz1.Components;

/**
 * @author Felix
 */
public class DistanceComponent implements ScoreComponent {
    private int goalDistance;
    private DistanceType type;

    public DistanceComponent(int goalDistance) {
        this.goalDistance = goalDistance;
        type = DistanceType.km;
    }

    @Override
    public int getGoalScore() {
        return goalDistance;
    }
    public String getValue() {
        return String.valueOf(goalDistance);
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
}
