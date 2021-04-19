package com.example.sonymz1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    private TextView challengeName, progressTxt;
    private ImageView arrow, medal, backgroundPic;
    private CardView card;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initiateView(view);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

       card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_challengePageFragment);
            }
        });
    }

    private void initiateView(View view) {
        challengeName = view.findViewById(R.id.challengeName);
        progressTxt = view.findViewById(R.id.progressTxt);
        arrow = view.findViewById(R.id.arrow);
        medal = view.findViewById(R.id.medal);
        backgroundPic = view.findViewById(R.id.backgroundPic);
        card = view.findViewById(R.id.card);
    }



}