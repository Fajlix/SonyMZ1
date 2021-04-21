package com.example.sonymz1;

/**
 * @author Viktor
 */
public class CreateChallengeViewModel {
    private Challenge challenge;

    public CreateChallengeViewModel() {
    }

    /**
     * Method for communication between Model and View, in this case Challenge and CreateChallengeFragment
     * @param challengeName The name of the challenge
     * @param challengeDescription A short description of hte challenge
     * @param isPrivate A boolean to say if the challenge is private or not
     * @param playerIDs A unique int for each user
     */
    public void createChallenge(String challengeName, String challengeDescription, boolean isPrivate, int[] playerIDs)
    {
        challenge = new Challenge(challengeName);
        challenge.setDescription(challengeDescription);
        challenge.setPrivate(isPrivate);
        addPlayers(playerIDs);
    }

    /**
     * Because we can add multiple users in the fragment but only one user at a time in the model
     * @param playerIDs
     */
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