package com.example.sonymz1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
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
import com.example.sonymz1.Components.TimerComponent;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CreateChallengeFragment extends Fragment {
    private TextInputEditText challengeDescriptionTextBox, challengeNameTextBox;
    private Switch privateSwitch;
    private Switch timerSwitch;
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
            TextView distanceError = view.findViewById(R.id.distanceError);
            Button buttonAddDistance = view.findViewById(R.id.buttonAddDistance);
            ConstraintLayout distancePopUp = view.findViewById(R.id.distancePopUp);
            ConstraintLayout distanceConstraint = view.findViewById(R.id.distanceConstraint);

            componentDistance.setOnClickListener(view3 -> {
                distancePopUp.bringToFront();
            });

            buttonAddDistance.setOnClickListener(view2 -> {
                if (edittextDistance.getText().toString().equals("")) {
                    distanceError.setText("The box can't be empty");
                }
                else {
                    challengeVM.addComponent(new CounterComponent(Integer.parseInt(edittextDistance.getText().toString())));
                    createChallengeFragment.bringToFront();
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
            TextView counterError = view.findViewById(R.id.counterError);
            Button buttonAddCounter = view.findViewById(R.id.buttonAddCounter);
            ConstraintLayout counterPopUp = view.findViewById(R.id.counterPopUp);
            ConstraintLayout counterConstraint = view.findViewById(R.id.counterConstraint);

            componentCounter.setOnClickListener(view3 -> {
                counterPopUp.bringToFront();
            });

            buttonAddCounter.setOnClickListener(view2 -> {
                if (edittextCounter.getText().toString().equals("")) {
                    counterError.setText("The box can't be empty");
                }
                else {
                    challengeVM.addComponent(new CounterComponent(Integer.parseInt(edittextCounter.getText().toString())));
                    createChallengeFragment.bringToFront();
                }
            });

            counterPopUp.setOnClickListener(view4 -> {
                createChallengeFragment.bringToFront();
            });

            counterConstraint.setOnClickListener(view4 -> {
                counterConstraint.setSoundEffectsEnabled(false);
            });
        }

        //TimerComponent
        {
            timerSwitch = view.findViewById(R.id.timerSwitch);
            TextView componentTimer = view.findViewById(R.id.timerComponent);
            EditText edittextTimer1 = view.findViewById(R.id.edittextTimer1);
            EditText edittextTimer2 = view.findViewById(R.id.edittextTimer2);
            Button buttonAddTimer = view.findViewById(R.id.buttonAddTimer);
            TextView textTimer1 = view.findViewById(R.id.textTimer1);
            TextView textTimer2 = view.findViewById(R.id.textTimer2);
            TextView textError = view.findViewById(R.id.timerError);
            ConstraintLayout timerPopUp = view.findViewById(R.id.timerPopUp);
            ConstraintLayout timerConstraint = view.findViewById(R.id.timerConstraint);

            componentTimer.setOnClickListener(view3 -> {
                timerPopUp.bringToFront();
            });

            timerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        textTimer1.setText("min");
                        textTimer2.setText("sec");
                    } else {
                        textTimer1.setText("h");
                        textTimer2.setText("min");
                    }
                }
            });

            buttonAddTimer.setOnClickListener(view2 -> {
                int totalSeconds = 0;
                String Text1 = edittextTimer1.getText().toString();
                String Text2 = edittextTimer2.getText().toString();

                if (!timerSwitch.isChecked()) {
                    if (Text1.equals("") && Text2.equals("")) {
                        textError.setText("Both boxes can't be empty");
                    } else if (!edittextTimer1.getText().equals("")) {
                        totalSeconds += Integer.parseInt(edittextTimer1.getText().toString()) * 3600;
                    } else if (!edittextTimer2.getText().equals("")) {
                        totalSeconds += Integer.parseInt(edittextTimer2.getText().toString()) * 60;
                    }
                    if (totalSeconds != 0) {
                        challengeVM.addComponent(new TimerComponent(totalSeconds));
                        createChallengeFragment.bringToFront();
                    }
                } else {
                    if (Text1.equals("") && Text2.equals("")) {
                        textError.setText("Both boxes can't be empty");
                    } else if (!edittextTimer1.getText().equals("")) {
                        totalSeconds += Integer.parseInt(edittextTimer1.getText().toString()) * 60;
                    } else if (!edittextTimer2.getText().equals("")) {
                        totalSeconds += Integer.parseInt(edittextTimer2.getText().toString());
                    }
                    challengeVM.addComponent(new TimerComponent(totalSeconds));
                    createChallengeFragment.bringToFront();
                }
            });

            timerPopUp.setOnClickListener(view4 -> {
                createChallengeFragment.bringToFront();
            });

            timerConstraint.setOnClickListener(view4 -> {
                timerConstraint.setSoundEffectsEnabled(false);
            });
        }

        view.findViewById(R.id.createButton).

                setOnClickListener(view1 ->

                {
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
