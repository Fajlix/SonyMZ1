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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    private ArrayList<ChallengeItem> challengeList;

    private TextView challengeName, progressTxt;
    private ImageView arrow, medal, backgroundPic;
    private RecyclerView recyclerView;
    private ChallengeAdapter rAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CardView card;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initiateView(view);

        createChallengeList();
        initiateView(view);
        buildRecyclerView();

        view.findViewById(R.id.addChallengeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_challengePageFragment);
            }
        });
    }

    private void createChallengeList() {
        challengeList = new ArrayList<>();
        challengeList.add(new ChallengeItem(R.drawable.arrow,R.drawable.run_challenge, R.drawable.medal,"Challange name","You are first!"));
        challengeList.add(new ChallengeItem(R.drawable.arrow,R.drawable.run_challenge, R.drawable.medal,"Challenge name","You are first!"));
        challengeList.add(new ChallengeItem(R.drawable.arrow,R.drawable.run_challenge, R.drawable.medal,"Challenge name","You are first!"));
        challengeList.add(new ChallengeItem(R.drawable.arrow,R.drawable.run_challenge, R.drawable.medal,"Challenge name","You are first!"));
        challengeList.add(new ChallengeItem(R.drawable.arrow,R.drawable.run_challenge, R.drawable.medal,"Challenge name","You are first!"));
    }

    private void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        rAdapter = new ChallengeAdapter(challengeList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        rAdapter.setOnItemClickListener(new ChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
             challengeList.get(position).changeText("Clicked");
             rAdapter.notifyItemChanged(position);
             NavHostFragment.findNavController(FirstFragment.this)
                     .navigate(R.id.action_FirstFragment_to_challengePageFragment);
            }
        });
    }

    private void initiateView(View view) {
        recyclerView = view.findViewById(R.id.rvc_list);
        challengeName = view.findViewById(R.id.challengeName);
        progressTxt = view.findViewById(R.id.progressTxt);
        arrow = view.findViewById(R.id.arrow);
        medal = view.findViewById(R.id.medal);
        backgroundPic = view.findViewById(R.id.backgroundPic);
    }



}