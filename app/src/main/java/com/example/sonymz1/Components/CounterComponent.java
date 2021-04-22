package com.example.sonymz1.Components;

import com.example.sonymz1.ScoreUpdateListener;

public class CounterComponent implements ScoreComponent {
    private int currentCount;
    private final ScoreUpdateListener parent;


    public CounterComponent(int currentCount, ScoreUpdateListener parent) {
        this.currentCount = currentCount;
        this.parent = parent;
    }
    public void updateParent(){
        parent.updateScore(currentCount);
    }

    public int getScore() {
        return currentCount;
    }
    public String getValue() {
        return String.valueOf(currentCount);
    }
    public void addCount(int count){
        currentCount += count;
        updateParent();
    }
}
