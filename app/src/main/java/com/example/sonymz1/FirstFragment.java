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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonymz1.Adapters.ChallengeAdapter;
import com.example.sonymz1.Database.DatabaseChallengeListCallback;
import com.example.sonymz1.Database.LocalDatabase;
import com.example.sonymz1.Database.OnlineDatabase;
import com.example.sonymz1.Model.Challenge;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    private ArrayList<Challenge> challengeList;
    private TextView challengeName, progressTxt, welcomeTxt;
    private ImageView medal, backgroundPic;
    private RecyclerView recyclerView;
    private ChallengeAdapter rAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CardView card;

    ChallengeViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(getActivity()).get(ChallengeViewModel.class);
        initiateView(view);
        createChallengeList();
        //buildRecyclerView();



        view.findViewById(R.id.addChallengeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.findViewById(R.id.addChallengeButton).setOnClickListener(view1 ->
                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.action_FirstFragment_to_createChallengeFragment));
            }
        });
    }

    /**
     * Method to populate challengeList
     */
    private void createChallengeList() {
        LocalDatabase db = LocalDatabase.getInstance();
        challengeList = db.getChallenges();
        OnlineDatabase.getInstance().getChallenges(vm.getMainUser().getId(), new DatabaseChallengeListCallback() {
            @Override
            public void onCallback(ArrayList<Challenge> challenges) {
                challengeList = challenges;
                welcomeTxt.setText("Welcome " + vm.getMainUser().getUsername());
                buildRecyclerView();
            }
        });
    }

    /**
     * method to setup recyclerview that contains challengecards.
     */
    private void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        rAdapter = new ChallengeAdapter(challengeList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        rAdapter.setOnItemClickListener(new ChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OnlineDatabase.getInstance().getChallenges(vm.getMainUser().getId(), new DatabaseChallengeListCallback() {
                    @Override
                    public void onCallback(ArrayList<Challenge> challenges) {
                        for (Challenge challenge : challenges) {
                            if (challenge.getChallengeCode().equals(challengeList.get(position).getChallengeCode())) {
                                LocalDatabase.getInstance().setActiveChallenge(challenge);
                                break;
                            }
                        }
                        rAdapter.notifyItemChanged(position);
                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.action_FirstFragment_to_challengePageFragment);

                    }
                });



                // Temp on click for test will change to navigate to specific challenge when it exists

            }
        });
    }

    /**
     * method to initiate views.
     *
     * @param view
     */

    private void initiateView(View view) {
        welcomeTxt = view.findViewById(R.id.welcomeText);
        recyclerView = view.findViewById(R.id.rvc_list);
        challengeName = view.findViewById(R.id.challengeName);
        progressTxt = view.findViewById(R.id.progressTxt);
        medal = view.findViewById(R.id.medal);
        backgroundPic = view.findViewById(R.id.backgroundPic);
    }
}