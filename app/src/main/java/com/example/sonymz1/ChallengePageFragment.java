package com.example.sonymz1;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

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
    private ImageView userImg1, userImg2, userImg3, backBtn, challengeInfoImg, editBtnImg, editChallengeNameBtnImg;
    private TextView progressTxt1, progressTxt2, progressTxt3, moreBtn, challengeNameTxt, descriptionTxt, numOfParticipants, privacyTxt, progressBarTxt;
    private TextView infoCardName, infoCardDescription, infoCardParticipantsNum, infoCardPrivacy, infoCardCode;
    private Button confirmNameChangeBtn, cancelNameChangeBtn;
    private ProgressBar progressBar;
    private RecyclerView rvcLeaderboard, rvcParticipants;
    private ConstraintLayout participantsView, editView, adminView, editNameView;
    private TextInputEditText nameChangeBox;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(requireActivity()).get(ChallengeViewModel.class);
        initializeViews(view);
        setPedestal();
        setLeaderboard();
        setParticipants();
        setInfoCard();

        //Navigate from ChallengePage to AddingScorePage but atm just a placeholder
        view.findViewById(R.id.addScoreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ChallengePageFragment.this)
                        .navigate(R.id.action_challengePageFragment_to_addingScorePage);
            }
        });
        //Should instead trigger editView, for now just for testing it instead navigates like the addScoreButton
        editBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adminView.getVisibility() == View.GONE){
                    adminView.setVisibility(View.VISIBLE);
                    editView.setVisibility(View.VISIBLE);
                    editBtnImg.setRotation(90);
                }
                else{
                    adminView.setVisibility(View.GONE);
                    editNameView.setVisibility(View.GONE);
                    editBtnImg.setRotation(0);
                }
            }
        });
        editChallengeNameBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editView.setVisibility(View.GONE);
                editNameView.setVisibility((View.VISIBLE));
            }
        });
        cancelNameChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editView.setVisibility(View.VISIBLE);
                editNameView.setVisibility((View.GONE));
                nameChangeBox.setText("");
            }
        });
        confirmNameChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.setChallengeName(nameChangeBox.getText().toString());
                setInfoCard();
                editView.setVisibility(View.VISIBLE);
                editNameView.setVisibility((View.GONE));
                nameChangeBox.setText("");
            }
        });
    }

    /**
     * Show all the participants
     */
    private void setParticipants(){
        rvcParticipants.setLayoutManager(new LinearLayoutManager(getContext()));
        ParticipantsAdapter participantsAdapter = new ParticipantsAdapter(this,
                vm.getLeaderBoard().getValue());
        rvcParticipants.setAdapter(participantsAdapter);
    }

    /**
     * Populate the leaderboard with participants.
     */
    private void setLeaderboard(){
        rvcLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(this,
                vm.getLeaderBoard().getValue());
        rvcLeaderboard.setAdapter(leaderboardAdapter);

        if(vm.getLeaderBoard().getValue().size() > 3){
            moreBtn.setVisibility(View.VISIBLE);
            System.out.println(vm.getLeaderBoard().getValue().get(vm.getMainUser().getId()));
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
        vm.getLeaderBoard().observe(getViewLifecycleOwner(), leaderboard -> {
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

        //-------------------------------------------------------
        challengeNameTxt = view.findViewById(R.id.challengeNameView);
        descriptionTxt = view.findViewById(R.id.descriptionTextView);
        challengeInfoImg = view.findViewById(R.id.challengeInfoImgView);
        numOfParticipants = view.findViewById(R.id.playerNumView);
        privacyTxt = view.findViewById(R.id.privacyTextView);
        progressBar = view.findViewById(R.id.progressBarView);
        progressBarTxt = view.findViewById(R.id.progressBarTextView);
        editBtnImg = view.findViewById(R.id.editBtnView);
        editView = view.findViewById(R.id.editInfoView);
        adminView = view.findViewById(R.id.editChallengeAdminView);
        editChallengeNameBtnImg = view.findViewById(R.id.editChallengeNameBtn);
        editNameView = view.findViewById(R.id.editInfoNameView);
        nameChangeBox = view.findViewById(R.id.nameChangeTextInput);
        confirmNameChangeBtn = view.findViewById(R.id.confirmChallengeNameChangeBtn);
        cancelNameChangeBtn = view.findViewById(R.id.cancelChallengeNameChangeBtn);
        infoCardName = view.findViewById(R.id.editChallengeNameView);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setInfoCard(){
        challengeNameTxt.setText(vm.getName());
        infoCardName.setText(vm.getName());

        descriptionTxt.setText(vm.getDescription());
        numOfParticipants.setText(String.valueOf(vm.getNumOfPlayers()));
        if(vm.isPrivate()){
            privacyTxt.setText("Private");
        }
        else{
            privacyTxt.setText("Public");
        }
        progressBarSetup();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void progressBarSetup(){
        //Give progressbar the right color
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(0, 172, 255)));

        //Setup for progressbar if there is a goal to track
        if(vm.getEndGoal() > 0){
            progressBarTxt.setText(vm.getMainUserScore() + "/" + vm.getEndGoal());

            progressBar.setMax(vm.getEndGoal());
            progressBar.setProgress(vm.getMainUserScore());
        }
        //If there is no goal to track
        else{
            progressBar.setVisibility(View.GONE);
            progressBarTxt.setVisibility(View.GONE);
        }
    }
}