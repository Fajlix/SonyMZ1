package com.example.sonymz1.Components;

/**
 * @author Felix
 * Class representing a counter that can be used to track scores.
 */
public class CounterComponent implements ScoreComponent {
    private final int goalCount;

    public CounterComponent(int goalCount) {
        this.goalCount = goalCount;
    }

    @Override
    public int getGoalScore() {
        return goalCount;
    }

    @Override
    public String getValue() {
        return String.valueOf(getGoalScore());
    }
}
