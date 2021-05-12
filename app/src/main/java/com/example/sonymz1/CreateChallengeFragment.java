package com.example.sonymz1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sonymz1.Database.OnlineDatabase;
import com.example.sonymz1.Database.UserListCallback;
import com.example.sonymz1.Model.User;
import com.example.sonymz1.Components.DistanceComponent;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class CreateChallengeFragment extends Fragment {
    private TextInputEditText challengeDescriptionTextBox, challengeNameTextBox;
    private Switch privateSwitch;
    private ChallengeViewModel challengeVM;
    private EditText edittextDistance;
    private Button buttonAddDistance;
    private TextView componentDistance;
    private ConstraintLayout distancePopUp, createChallengeFragment;

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
        edittextDistance = view.findViewById(R.id.edittextDistance);
        componentDistance = view.findViewById(R.id.componentDistance);
        buttonAddDistance = view.findViewById(R.id.buttonAddDistance);
        distancePopUp = view.findViewById(R.id.distancePopUp);
        createChallengeFragment = view.findViewById(R.id.createChallengeFragment);

        componentDistance.setOnClickListener(view3 -> {
            distancePopUp.bringToFront();
        });

        buttonAddDistance.setOnClickListener(view2 -> {
            createChallengeFragment.bringToFront();
            challengeVM.addComponent(new DistanceComponent(Integer.parseInt(edittextDistance.getText().toString())));
        });

        view.findViewById(R.id.createButton).setOnClickListener(view1 -> {
            String name = challengeNameTextBox.getText().toString();
            String description = challengeDescriptionTextBox.getText().toString();
            boolean isPrivate = privateSwitch.isChecked();
            //TODO SHOULD DEFINETLY NOT EXIST

            OnlineDatabase.getInstance().getUsers(new UserListCallback() {
                @Override
                public void onCallback(ArrayList<User> users) {
                    int[] playerIds = new int[users.size()];
                    int index = 0;
                    for (User user : users) {
                        playerIds[index] = user.getId();
                        index++;
                    }
                    challengeVM.createChallenge(name, description, isPrivate, playerIds);

                    // Close the keyboard
                    view.clearFocus();

                    NavHostFragment.findNavController(CreateChallengeFragment.this)
                            .navigate(R.id.action_createChallengeFragment_to_challengePageFragment);
                }
            });
        });
    }

    public String getName() {
        return challengeNameTextBox.getText().toString();
    }

    public String getDescription() {
        return challengeDescriptionTextBox.getText().toString();
    }

    public Boolean isPrivate() {
        return privateSwitch.isChecked();
    }
}
