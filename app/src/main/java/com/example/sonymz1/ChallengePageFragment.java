package com.example.sonymz1;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wendy P, Jonathan S.
 */
public class ChallengePageFragment extends Fragment {
    private ChallengeViewModel vm;
    private ImageView userImg1, userImg2, userImg3, backBtn, challengeInfoImg, editBtnImg, editChallengeNameBtnImg, editChallengeDescriptionBtnImg, editChallengeCopyCodeBtnImg;
    private TextView progressTxt1, progressTxt2, progressTxt3, moreBtn, challengeNameTxt, descriptionTxt, numOfParticipants, privacyTxt, progressBarTxt;
    private TextView infoCardName, infoCardDescription, infoCardParticipantsNum, infoCardPrivacy, infoCardCode;
    private Button confirmNameChangeBtn, cancelNameChangeBtn, confirmDescriptionChangeBtn, cancelDescriptionChangeBtn;
    private Switch privacySwitch;
    private ProgressBar progressBar;
    private RecyclerView rvcLeaderBoard, rvcParticipants;
    private ConstraintLayout participantsView, editView, adminView, editNameView, editDescriptionView;
    private TextInputEditText nameChangeBox, descriptionChangeBox;
    private Button addScoreButton;



    public ChallengePageFragment() {
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
        return inflater.inflate(R.layout.fragment_challenge_page, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(requireActivity()).get(ChallengeViewModel.class);
        initializeViews(view);
        setPedestal();
        setLeaderBoard();
        setParticipants();
        setInfoCard();

        //Navigate from ChallengePage to AddingScorePage but atm just a placeholder
        view.findViewById(R.id.addScoreButton).setOnClickListener(
                view1 -> NavHostFragment.findNavController(ChallengePageFragment.this)
                .navigate(R.id.action_challengePageFragment_to_addingScorePage));
        //Should instead trigger editView, for now just for testing it instead navigates like the addScoreButton
        editBtnImg.setOnClickListener(view12 -> {
            if(adminView.getVisibility() == View.GONE){
                adminView.setVisibility(View.VISIBLE);
                editView.setVisibility(View.VISIBLE);
                editBtnImg.setRotation(90);
            }
            else{
                adminView.setVisibility(View.GONE);
                editNameView.setVisibility(View.GONE);
                editDescriptionView.setVisibility(View.GONE);
                editBtnImg.setRotation(0);
            }
        });
        editChallengeNameBtnImg.setOnClickListener(view13 -> {
            editView.setVisibility(View.GONE);
            editNameView.setVisibility((View.VISIBLE));
        });
        cancelNameChangeBtn.setOnClickListener(view14 -> {
            editView.setVisibility(View.VISIBLE);
            editNameView.setVisibility((View.GONE));
            nameChangeBox.setText("");
        });
        confirmNameChangeBtn.setOnClickListener(view15 -> {
            vm.setChallengeName(nameChangeBox.getText().toString());
            setInfoCard();
            editView.setVisibility(View.VISIBLE);
            editNameView.setVisibility((View.GONE));
            nameChangeBox.setText("");
        });

        editChallengeDescriptionBtnImg.setOnClickListener(view16 -> {
            editView.setVisibility(View.GONE);
            editDescriptionView.setVisibility((View.VISIBLE));
        });
        cancelDescriptionChangeBtn.setOnClickListener(view17 -> {
            editView.setVisibility(View.VISIBLE);
            editDescriptionView.setVisibility((View.GONE));
            descriptionChangeBox.setText("");
        });
        confirmDescriptionChangeBtn.setOnClickListener(view18 -> {
            vm.setDescription(descriptionChangeBox.getText().toString());
            setInfoCard();
            editView.setVisibility(View.VISIBLE);
            editDescriptionView.setVisibility((View.GONE));
            descriptionChangeBox.setText("");
        });

        privacySwitch.setOnClickListener(view19 -> {
            if(vm.isPrivate()){
                vm.setPrivacy(false);
            }
            else{
                vm.setPrivacy(true);
            }
            setInfoCard();
        });

        editChallengeCopyCodeBtnImg.setOnClickListener(view110 -> {
            ClipboardManager copyPastaMaker = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Challenge code", vm.getCode());
            copyPastaMaker.setPrimaryClip(clip);
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
     * Populate the leaderBoard with participants.
     */
    private void setLeaderBoard(){
        rvcLeaderBoard.setLayoutManager(new LinearLayoutManager(getContext()));
        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(this,
                vm.getLeaderBoard().getValue());
        if(vm.getLeaderBoard().getValue().size() > 3){
            moreBtn.setVisibility(View.VISIBLE);
            System.out.println(vm.getLeaderBoard().getValue().get(vm.getMainUser().getId()));
            moreBtn.setOnClickListener(v -> {
                participantsView.setVisibility(View.VISIBLE);
                addScoreButton.setVisibility(View.GONE);
            });

            backBtn.setOnClickListener(v -> {
                participantsView.setVisibility(View.GONE);
                addScoreButton.setVisibility(View.VISIBLE);
            });
        }else moreBtn.setVisibility(View.GONE);
    }

    /**
     * Set the top 3 users info on the pedestal.
     */
    private void setPedestal(){
        vm.getLeaderBoard().observe(getViewLifecycleOwner(), leaderBoard -> {
            List<Map.Entry<Integer, Integer>> leaderBoardList =
                    new LinkedList<>(leaderBoard.entrySet());
            if (leaderBoardList.size()>0) {

                if (leaderBoardList.size() > 2) {
                    userImg1.setImageResource(vm.getUsers().get(leaderBoardList.get(0).getKey()).getProfilePic());
                    userImg2.setImageResource(vm.getUsers().get(leaderBoardList.get(1).getKey()).getProfilePic());
                    userImg3.setImageResource(vm.getUsers().get(leaderBoardList.get(2).getKey()).getProfilePic());
                    progressTxt1.setText(String.valueOf(leaderBoardList.get(0).getValue()));
                    progressTxt2.setText(String.valueOf(leaderBoardList.get(1).getValue()));
                    progressTxt3.setText(String.valueOf(leaderBoardList.get(2).getValue()));
                } else if (leaderBoardList.size() == 2) {
                    userImg1.setImageResource(vm.getUsers().get(leaderBoardList.get(0).getKey()).getProfilePic());
                    userImg2.setImageResource(vm.getUsers().get(leaderBoardList.get(1).getKey()).getProfilePic());
                    progressTxt1.setText(String.valueOf(leaderBoardList.get(0).getValue()));
                    progressTxt2.setText(String.valueOf(leaderBoardList.get(1).getValue()));
                } else {
                    userImg1.setImageResource(vm.getUsers().get(leaderBoardList.get(0).getKey()).getProfilePic());
                    progressTxt1.setText(String.valueOf(leaderBoardList.get(0).getValue()));
                }
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
        rvcLeaderBoard = view.findViewById(R.id.rvcLeaderboard);
        moreBtn = view.findViewById(R.id.moreBtn);
        participantsView = view.findViewById(R.id.particiantsView);
        backBtn = view.findViewById(R.id.backBtn);
        rvcParticipants = view.findViewById(R.id.rvcParticipants);
        addScoreButton = view.findViewById(R.id.addScoreButton);

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
        editChallengeDescriptionBtnImg = view.findViewById(R.id.editChallengeDescriptionBtn);
        privacySwitch = view.findViewById(R.id.privacySwitch);
        editChallengeCopyCodeBtnImg = view.findViewById(R.id.editChallengeCopyCodeBtn);

        editNameView = view.findViewById(R.id.editInfoNameView);
        nameChangeBox = view.findViewById(R.id.nameChangeTextInput);
        confirmNameChangeBtn = view.findViewById(R.id.confirmChallengeNameChangeBtn);
        cancelNameChangeBtn = view.findViewById(R.id.cancelChallengeNameChangeBtn);

        editDescriptionView = view.findViewById(R.id.editInfoDescriptionView);
        descriptionChangeBox = view.findViewById(R.id.descriptionChangeTextInput);
        confirmDescriptionChangeBtn = view.findViewById(R.id.confirmDescriptionChangeBtn);
        cancelDescriptionChangeBtn = view.findViewById(R.id.cancelDescriptionChangeBtn);

        infoCardName = view.findViewById(R.id.editChallengeNameView);
        infoCardDescription = view.findViewById(R.id.editChallengeDescriptionView);
        infoCardParticipantsNum = view.findViewById(R.id.editChallengeParticipantsNumView);
        infoCardPrivacy = view.findViewById(R.id.editChallengePrivacyView);
        infoCardCode = view.findViewById(R.id.editChallengeCodeView);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setInfoCard(){
        challengeNameTxt.setText(vm.getName());
        infoCardName.setText(vm.getName());

        descriptionTxt.setText(vm.getDescription());
        infoCardDescription.setText(vm.getDescription());

        numOfParticipants.setText(String.valueOf(vm.getNumOfPlayers()));
        infoCardParticipantsNum.setText(String.valueOf(vm.getNumOfPlayers()));

        if(vm.isPrivate()){
            privacyTxt.setText("Private");
            infoCardPrivacy.setText("Private");
            privacySwitch.setChecked(true);
        }
        else{
            privacyTxt.setText("Public");
            infoCardPrivacy.setText("Public");
            privacySwitch.setChecked(false);
        }

        infoCardCode.setText(vm.getCode());

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