package com.example.sonymz1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sonymz1.Components.CounterComponent;
import com.example.sonymz1.Components.DateComponent;
import com.example.sonymz1.Components.DistanceComponent;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.invoke.ConstantCallSite;
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
        ConstraintLayout createChallengeFragment = view.findViewById(R.id.createChallengeFragment);

        //DateComponent
        {
            CalendarView calendarDate = view.findViewById(R.id.calendarDate);
            TextView componentDate = view.findViewById(R.id.componentDate);
            Button buttonAddDate = view.findViewById(R.id.buttonAddDate);
            ConstraintLayout datePopUp = view.findViewById(R.id.datePopUp);
            ConstraintLayout dateConstraint = view.findViewById(R.id.dateConstraint);

            componentDate.setOnClickListener(view4 -> {
                datePopUp.bringToFront();
            });

            buttonAddDate.setOnClickListener(view5 -> {
                createChallengeFragment.bringToFront();
                challengeVM.addComponent(new DateComponent(calendarDate.getDate()));
            });

            datePopUp.setOnClickListener(view4 -> {
                createChallengeFragment.bringToFront();
            });

            dateConstraint.setOnClickListener(view4 -> {
                dateConstraint.setSoundEffectsEnabled(false);
            });
        }

        //DistanceComponent
        {
            EditText edittextDistance = view.findViewById(R.id.edittextDistance);
            TextView componentDistance = view.findViewById(R.id.componentDistance);
            Button buttonAddDistance = view.findViewById(R.id.buttonAddDistance);
            ConstraintLayout distancePopUp = view.findViewById(R.id.distancePopUp);
            ConstraintLayout distanceConstraint = view.findViewById(R.id.distanceConstraint);

            componentDistance.setOnClickListener(view3 -> {
                distancePopUp.bringToFront();
            });

            buttonAddDistance.setOnClickListener(view2 -> {
                createChallengeFragment.bringToFront();
                if (edittextDistance.getText().toString() != null) {
                    challengeVM.addComponent(new DistanceComponent(Integer.parseInt(edittextDistance.getText().toString())));
                }
            });

            distancePopUp.setOnClickListener(view4 -> {
                createChallengeFragment.bringToFront();
            });

            distanceConstraint.setOnClickListener(view4 -> {
                distanceConstraint.setSoundEffectsEnabled(false);
            });
        }

        //CounterComponent
        {
            EditText edittextCounter = view.findViewById(R.id.edittextCounter);
            TextView componentCounter = view.findViewById(R.id.componentCounter);
            Button buttonAddCounter = view.findViewById(R.id.buttonAddCounter);
            ConstraintLayout counterPopUp = view.findViewById(R.id.counterPopUp);
            ConstraintLayout counterConstraint = view.findViewById(R.id.counterConstraint);

            componentCounter.setOnClickListener(view3 -> {
                counterPopUp.bringToFront();
            });

            buttonAddCounter.setOnClickListener(view2 -> {
                createChallengeFragment.bringToFront();
                if (edittextCounter.getText().toString() != null) {
                    challengeVM.addComponent(new CounterComponent(Integer.parseInt(edittextCounter.getText().toString())));
                }
            });

            counterPopUp.setOnClickListener(view4 -> {
                createChallengeFragment.bringToFront();
            });

            counterConstraint.setOnClickListener(view4 -> {
                counterConstraint.setSoundEffectsEnabled(false);
            });
        }

        view.findViewById(R.id.createButton).setOnClickListener(view1 -> {
            String name = challengeNameTextBox.getText().toString();
            String description = challengeDescriptionTextBox.getText().toString();
            boolean isPrivate = privateSwitch.isChecked();
            //TODO SHOULD DEFINETLY NOT EXIST

            Map<Integer, User> users = challengeVM.getUsers();
            int[] playerIds = new int[users.size()];
            int index = 0;
            for (Integer key : users.keySet()) {
                playerIds[index] = key;
                index++;
            }
            challengeVM.createChallenge(name, description, isPrivate, playerIds);

            NavHostFragment.findNavController(CreateChallengeFragment.this)
                    .navigate(R.id.action_createChallengeFragment_to_challengePageFragment);
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
