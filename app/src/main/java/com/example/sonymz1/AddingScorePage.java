package com.example.sonymz1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sonymz1.Components.CounterComponent;
import com.example.sonymz1.Components.DistanceComponent;
import com.example.sonymz1.Components.ScoreComponent;
import com.example.sonymz1.Components.TimerComponent;
import com.google.android.material.textfield.TextInputEditText;

/**
 *
 * @author Elina W, Viktor W and Felix.
 */

public class AddingScorePage extends Fragment {

    private Button addScoreBtn, returnBtn;
    private TextView distanceActivity,distanceType, timerType1, timerType2;
    private ConstraintLayout distanceView, counterView, timerView;
    private TextInputEditText distanceInput,counterInput,timerInputFirst,timerInputSecond;
    private ChallengeViewModel vm;

    public AddingScorePage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adding_score_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(getActivity()).get(ChallengeViewModel.class);
        distanceInput = view.findViewById(R.id.inputDistanceTextBox);
        counterInput = view.findViewById(R.id.inputCounterTextBox);
        timerInputFirst = view.findViewById(R.id.inputTimerTextBoxFirst);
        timerInputSecond = view.findViewById(R.id.inputTimerTextBoxSecond);
        distanceActivity = view.findViewById(R.id.distanceActivity);
        distanceType = view.findViewById(R.id.distanceType);
        distanceView = view.findViewById(R.id.distanceView);
        counterView = view.findViewById(R.id.counterView);
        timerView = view.findViewById(R.id.timerView);
        timerType1 = view.findViewById(R.id.timerType1);
        timerType2 = view.findViewById(R.id.timerType2);

        //Navigate from ChallengePage to AddingScorePage but atm just a placeholder
        view.findViewById(R.id.returnBtn).setOnClickListener(
                view1 -> NavHostFragment.findNavController(AddingScorePage.this)
                        .navigate(R.id.action_addingScorePage_to_challengePageFragment));

        view.findViewById(R.id.addScoreBtn).setOnClickListener(view12 -> {
            String str = distanceInput.getText().toString();
            int score = Integer.parseInt(str);
            vm.addScore(score);

            NavHostFragment.findNavController(AddingScorePage.this)
                    .navigate(R.id.action_addingScorePage_to_challengePageFragment);
        });
        setInputType();
    }
    private int getScoreInput(){
        ScoreComponent component = vm.getScoreComponent();
        if (component.getClass().equals(DistanceComponent.class)){
            return Integer.parseInt(distanceInput.getText().toString());
        }
        else if (component.getClass().equals(CounterComponent.class)){
            return Integer.parseInt(counterInput.getText().toString());
        }
        else if (component.getClass().equals(TimerComponent.class)){

            if (((TimerComponent)component).HasSeconds()) {
                    return (Integer.parseInt(timerType1.getText().toString()))*60 +
                            Integer.parseInt(timerType2.getText().toString());
            }
            else{
                return (Integer.parseInt(timerType1.getText().toString()))*60*60 +
                        Integer.parseInt(timerType2.getText().toString())*60;
            }
        }
        return 0;
    }
    private void setInputType(){
        ScoreComponent component = vm.getScoreComponent();
        int distanceVisibility = View.GONE;
        int counterVisibility = View.GONE;
        int timerVisibility = View.GONE;
        if (component.getClass().equals(DistanceComponent.class)){
            distanceVisibility = View.VISIBLE;
            distanceType.setText(((DistanceComponent)vm.getScoreComponent()).getType().toString());

        }
        else if (component.getClass().equals(CounterComponent.class)){
            counterVisibility = View.VISIBLE;
        }
        else if (component.getClass().equals(TimerComponent.class)){
            timerVisibility = View.VISIBLE;
        }
        distanceView.setVisibility(distanceVisibility);
        counterView.setVisibility(counterVisibility);
        timerView.setVisibility(timerVisibility);
    }
}

