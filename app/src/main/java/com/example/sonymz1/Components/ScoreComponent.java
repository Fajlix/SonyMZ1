package com.example.sonymz1.Components;

import com.example.sonymz1.ScoreUpdateListener;

public interface ScoreComponent extends ChallengeComponent {
    int getScore();
    void updateParent(int mainUserId);
    int getGoalScore();
    void setParent(ScoreUpdateListener scoreUpdateListener);
}
