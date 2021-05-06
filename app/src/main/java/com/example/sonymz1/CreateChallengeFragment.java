package com.example.sonymz1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
public class CreateChallengeFragment extends Fragment {
    private TextInputEditText challengeDescriptionTextBox, challengeNameTextBox;
    private Switch privateSwitch;
    private ChallengeViewModel challengeVM;

    public CreateChallengeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_challenge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        challengeVM = new ViewModelProvider(requireActivity()).get(ChallengeViewModel.class);

        challengeDescriptionTextBox = view.findViewById(R.id.challengeDescriptionTextBox);
        challengeNameTextBox = view.findViewById(R.id.challengeNameTextBox);
        privateSwitch = view.findViewById(R.id.isPrivate);

        view.findViewById(R.id.createButton).setOnClickListener(view1 -> {
             String name = challengeNameTextBox.getText().toString();
             String description = challengeDescriptionTextBox.getText().toString();
             boolean isPrivate = privateSwitch.isChecked();
             //TODO SHOULD DEFINETLY NOT EXIST

            Map<Integer, User> users = challengeVM.getUsers();
            int[] playerIds = new int[users.size()];
            int index = 0;
            for(Integer key : users.keySet()){
                playerIds[index] = key;
                index++;
            }
            try {
                challengeVM.createChallenge(name, description, isPrivate, playerIds);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            NavHostFragment.findNavController(CreateChallengeFragment.this)
                    .navigate(R.id.action_createChallengeFragment_to_challengePageFragment);
        });
    }

    public String getName ()
    {
        return challengeNameTextBox.getText().toString();
    }

    public String getDescription ()
    {
        return challengeDescriptionTextBox.getText().toString();
    }

    public Boolean isPrivate ()
    {
        return privateSwitch.isChecked();
    }
}
