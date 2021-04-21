package com.example.sonymz1;

public class CreateChallengeViewModel {
    private Challenge challenge;

    public CreateChallengeViewModel() {
    }

    public void createChallenge(String challengeName, String challengeDescription, boolean isPrivate, int[] playerIDs)
    {
        challenge = new Challenge(challengeName);
        challenge.setDescription(challengeDescription);
        challenge.setPrivate(isPrivate);
        addPlayers(playerIDs);
    }

    private void addPlayers (int[] playerIDs)
    {
        for (int i = 0; i < playerIDs.length; i++) {
            challenge.addPlayer(playerIDs[i]);
        }
    }

     public String getChallengeDescription()
     {
         return challenge.getDescription();
     }
}