package com.example.sonymz1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChallengePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author Wendy P, Jonathan S.
 */
public class ChallengePageFragment extends Fragment {
    private ChallengeViewModel vm;
    private ImageView userImg1, userImg2, userImg3, backBtn;
    private TextView progressTxt1, progressTxt2, progressTxt3, moreBtn;
    private RecyclerView rvcLeaderboard, rvcParticipants;
    private ConstraintLayout participantsView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChallengePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChallengePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChallengePageFragment newInstance(String param1, String param2) {
        ChallengePageFragment fragment = new ChallengePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(requireActivity()).get(ChallengeViewModel.class);
        initializeViews(view);

        vm.addPlayer(vm.getUser(1).getId(),6);
        vm.addPlayer(vm.getUser(2).getId(),5);
        vm.addPlayer(vm.getUser(3).getId(),10);
        vm.addPlayer(vm.getUser(4).getId(),4);

        setPedestal();
        setLeaderboard();
        setParticipants();
    }

    /**
     * Show all the participants
     */
    private void setParticipants(){
        rvcParticipants.setLayoutManager(new LinearLayoutManager(getContext()));
        ParticipantsAdapter participantsAdapter = new ParticipantsAdapter(this,
                vm.getLeaderboard().getValue());
        rvcParticipants.setAdapter(participantsAdapter);
    }

    /**
     * Populate the leaderboard with participants.
     */
    private void setLeaderboard(){
        rvcLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(this,
                vm.getLeaderboard().getValue());
        rvcLeaderboard.setAdapter(leaderboardAdapter);

        if(vm.getLeaderboard().getValue().size() > 3){
            moreBtn.setVisibility(View.VISIBLE);
            moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    participantsView.setVisibility(View.VISIBLE);
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    participantsView.setVisibility(View.GONE);
                }
            });
        }else moreBtn.setVisibility(View.GONE);
    }

    /**
     * Set the top 3 users info on the pedestal.
     */
    private void setPedestal(){
        vm.getLeaderboard().observe(getViewLifecycleOwner(), leaderboard -> {
            List<Map.Entry<Integer, Integer>> leaderboardList =
                    new LinkedList<>(leaderboard.entrySet());

            if (leaderboardList.size() > 2) {
                userImg1.setImageResource(vm.getUsers().get(leaderboardList.get(0).getKey()).getProfilePic());
                userImg2.setImageResource(vm.getUsers().get(leaderboardList.get(1).getKey()).getProfilePic());
                userImg3.setImageResource(vm.getUsers().get(leaderboardList.get(2).getKey()).getProfilePic());
                progressTxt1.setText(String.valueOf(leaderboardList.get(0).getValue()));
                progressTxt2.setText(String.valueOf(leaderboardList.get(1).getValue()));
                progressTxt3.setText(String.valueOf(leaderboardList.get(2).getValue()));
            }
            else if (leaderboardList.size() == 2) {
                userImg1.setImageResource(vm.getUsers().get(leaderboardList.get(0).getKey()).getProfilePic());
                userImg2.setImageResource(vm.getUsers().get(leaderboardList.get(1).getKey()).getProfilePic());
                progressTxt1.setText(String.valueOf(leaderboardList.get(0).getValue()));
                progressTxt2.setText(String.valueOf(leaderboardList.get(1).getValue()));
            }else {
                userImg1.setImageResource(vm.getUsers().get(leaderboardList.get(0).getKey()).getProfilePic());
                progressTxt1.setText(String.valueOf(leaderboardList.get(0).getValue()));
            }
        });
    }

    /**
     * Instantiate all the required views.
     * @param view the fragments view.
     */
    private void initializeViews(View view){
        userImg1 = view.findViewById(R.id.user_img1);
        userImg2 = view.findViewById(R.id.user_img2);
        userImg3 = view.findViewById(R.id.user_img3);
        progressTxt1 = view.findViewById(R.id.progressTxt1);
        progressTxt2 = view.findViewById(R.id.progressTxt2);
        progressTxt3 = view.findViewById(R.id.progressTxt3);
        rvcLeaderboard = view.findViewById(R.id.rvcLeaderboard);
        moreBtn = view.findViewById(R.id.moreBtn);
        participantsView = view.findViewById(R.id.particiantsView);
        backBtn = view.findViewById(R.id.backBtn);
        rvcParticipants = view.findViewById(R.id.rvcParticipants);
    }
}