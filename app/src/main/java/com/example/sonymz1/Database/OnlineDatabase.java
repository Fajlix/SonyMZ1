package com.example.sonymz1.Database;

import androidx.annotation.NonNull;

import com.example.sonymz1.Components.ChallengeComponent;
import com.example.sonymz1.Components.CounterComponent;
import com.example.sonymz1.Components.DateComponent;
import com.example.sonymz1.Components.DistanceComponent;
import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Felix
 */
class OnlineDatabase {
    private static OnlineDatabase instance;
    DatabaseReference challengeRef;
    DatabaseReference usersRef;
    /**
     * Online Database uses singleton to only have one instance through out the app
     * the database has a reference to two nodes in a firebase database. One for users and one for
     * challenges
     */
    private OnlineDatabase() {
        challengeRef = FirebaseDatabase.getInstance().getReference().child("Challenges");
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public static OnlineDatabase getInstance() {
        if (instance == null)
            instance = new OnlineDatabase();
        return instance;
    }

    /**
     * gets the challenges from firebase and calls the onCallback method with the list when the data has been
     * received
     * @param user the user for which the challenges will be returned.
     * @param callback a databaseCallback that will get notified when the data is received
     */
    public void getChallenges(int user, OnlineDatabaseCallback callback) {
        challengeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                ArrayList<Challenge> challenges = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Gson gson = new Gson();
                    ComponentFactory componentFactory = new ComponentFactory();
                    JsonObject jsonObject = gson.fromJson(gson.toJson(snapshot.getValue()),JsonObject.class);
                    JsonElement comps = jsonObject.remove("components");
                    JsonArray jArr = new JsonArray();
                    if (comps!= null) {
                        jArr = comps.getAsJsonArray();
                    }
                    Challenge challenge = gson.fromJson(jsonObject, Challenge.class);
                    for (JsonElement element : jArr){
                        challenge.addComponent(componentFactory.getComponent("DISTANCE", element));
                    }
                    if (challenge.getLeaderBoard().containsKey(user)) {
                        challenges.add(challenge);
                    }
                }
                callback.onCallback(challenges);
                challengeRef.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    /**
     * gets the users from firebase and calls the onCallback method with the list when the data has been
     * received
     * @param userCallback a userCallback that will get notified when the data is received
     */
    public void getAllUsers(UserListCallback userCallback){
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                ArrayList<User> users = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(snapshot.getValue());
                    users.add(gson.fromJson(jsonStr, User.class));
                }
                userCallback.onCallback(users);
                usersRef.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    /**
     * Saves a challenge to firebase
     * @param challenge the challenge that gets saved
     */

    public void saveChallenge(Challenge challenge) {
        ComponentFactory factory = new ComponentFactory();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(gson.toJson(challenge),JsonObject.class);
        ArrayList<JsonChallengeComponent> components = new ArrayList<>();
        for (ChallengeComponent component :
                challenge.getComponents()) {
            components.add(new JsonChallengeComponent(factory.getType(component),component));
        }
        jsonObject.remove("components");
        JsonArray jsonArray = gson.toJsonTree(components).getAsJsonArray();
        jsonObject.add("components", jsonArray);
        String jsonStr = jsonObject.toString();
        Map map = gson.fromJson(jsonStr, Map.class);
        challengeRef.child(challenge.getChallengeCode()).setValue(map);
    }

    /**
     * will save a map of users in firebase
     * @param userMap map of the users to be saved
     */
    public void saveUsers(Map<Integer, User> userMap){
        for (User user : userMap.values()) {
            saveUser(user);
        }
    }

    /**
     * saves a user to firebase
     * @param user the user that gets saved
     */
    public void saveUser(User user){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(user);
        Map map = gson.fromJson(jsonStr, Map.class);
        usersRef.child(String.valueOf(user.getId())).setValue(map);
    }
    public void removeChallenge(Challenge challenge) {
        challengeRef.child(challenge.getChallengeCode()).removeValue();
    }

    /**
     * class that represents a challengeComponent to Json in order to save it correctly
     */
    private class JsonChallengeComponent{
        private final String type;
        private final ChallengeComponent component;
        public JsonChallengeComponent(String type, ChallengeComponent component){
            this.type = type;
            this.component = component;
        }

        public ChallengeComponent getComponent() {
            return component;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * helper class to get and save different components.
     */
    private static class ComponentFactory{
        private static String distance= "DISTANCE";
        private static String date = "DATE";
        private static String counter= "COUNTER";

        /**
         * returns a new component from a json element and a type string
         * @param type the type represented using a string
         * @param element the json element containing a component in json format
         * @return returns a new challengeComponent
         */
        private ChallengeComponent getComponent(String type, JsonElement element){
            if (type == null){
                return null;
            }
            if (type.equalsIgnoreCase(distance)){
                return new Gson().fromJson(element,DistanceComponent.class);
            }
            else if (type.equalsIgnoreCase(date)){
                return new Gson().fromJson(element,DateComponent.class);
            }
            else if (type.equalsIgnoreCase(counter)){
                return new Gson().fromJson(element,CounterComponent.class);
            }
            return null;
        }

        /**
         * gelper method to get the type string for a component
         * @param component the component that the type will reperesent
         * @return returns a string containing the type in string format
         */
        public String getType(ChallengeComponent component){
            if (component ==null){
                return null;
            }
            if (component.getClass().equals(DistanceComponent.class)){
                return distance;
            }
            else if(component.getClass().equals(DateComponent.class)){
                return date;
            }
            else if (component.getClass().equals(CounterComponent.class)){
                return counter;
            }
            return null;
        }
    }
}
