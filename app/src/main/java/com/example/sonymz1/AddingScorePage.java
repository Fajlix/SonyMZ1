package com.example.sonymz1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 *
 * @author Elina W, Viktor W and Felix.
 */

public class AddingScorePage extends Fragment {

    private Button addScoreBtn, returnBtn;
    private TextView addScoreTxt;
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
        addScoreTxt = view.findViewById(R.id.addScoreTxt);

        //Navigate from ChallengePage to AddingScorePage but atm just a placeholder
        view.findViewById(R.id.returnBtn).setOnClickListener(
                view1 -> NavHostFragment.findNavController(AddingScorePage.this)
                        .navigate(R.id.action_addingScorePage_to_challengePageFragment));

        view.findViewById(R.id.addScoreBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = addScoreTxt.getText().toString();
                int score = Integer.parseInt(str);
                vm.addScore(score);

                NavHostFragment.findNavController(AddingScorePage.this)
                        .navigate(R.id.action_addingScorePage_to_challengePageFragment);
            }
        });
    }
}

