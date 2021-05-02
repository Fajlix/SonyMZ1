package com.example.sonymz1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;

public class CreateChallengeFragment extends Fragment {
    private TextInputEditText challengeDescriptionTextBox, challengeNameTextBox;
    private Switch isPrivate;
    private ChallengeViewModel challengeVM;

    public CreateChallengeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    //    challengeVM = ViewModelProviders.of(this).get(ChallengeViewModel.class);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.create_challenge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      /*  challengeDescriptionTextBox = view.findViewById(R.id.challengeDescriptionTextBox);
        challengeNameTextBox = view.findViewById(R.id.challengeNameTextBox);
        isPrivate = view.findViewById(R.id.isPrivate);*/

        view.findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  challengeVM.createChallenge();

                NavHostFragment.findNavController(CreateChallengeFragment.this)
                        .navigate(R.id.action_createChallengeFragment_to_challengePageFragment);
            }
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
        return isPrivate.isChecked();
    }
}
