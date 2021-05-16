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
import androidx.cardview.widget.CardView;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sonymz1.Adapters.LeaderBoardAdapter;
import com.example.sonymz1.Adapters.ParticipantsAdapter;
import com.example.sonymz1.Adapters.SelectParticipantsAdapter;
import com.example.sonymz1.Database.DatabaseUserCallback;
import com.example.sonymz1.Database.OnlineDatabase;
import com.example.sonymz1.Database.UserListCallback;
import com.example.sonymz1.Model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wendy P, Jonathan S.
 */
public class ChallengePageFragment extends Fragment {
    private ChallengeViewModel vm;
    private CardView pedestal2, pedestal3;
    private ImageView userImg1, userImg2, userImg3, backBtn, challengeInfoImg, editBtnImg, editChallengeNameBtnImg, editChallengeDescriptionBtnImg, editChallengeCopyCodeBtnImg, editChallengeParticipantsBtn;
    private ImageView creatorOnlyBtn, backArrowCreator, addAdminBtn, removeAdminBtn;
    private TextView numAdmins;
    private TextView progressTxt1, progressTxt2, progressTxt3, moreBtn, challengeNameTxt, descriptionTxt, numOfParticipants, privacyTxt, progressBarTxt;
    private TextView infoCardName, infoCardDescription, infoCardParticipantsNum, infoCardPrivacy, infoCardCode, challengeHostView;
    private Button confirmNameChangeBtn, cancelNameChangeBtn, confirmDescriptionChangeBtn, cancelDescriptionChangeBtn, confirmRemoveP, cancelRemoveP, confirmAddA, cancelAddA, confirmRA, cancelRA;
    private Switch privacySwitch;
    private ProgressBar progressBar;
    private RecyclerView rvcLeaderBoard, rvcParticipants;
    private ConstraintLayout participantsView, editView, adminView, editNameView, editDescriptionView, removePView, creatorOnlyLayout, addAdminLayout, removeAdminLayout;
    private TextInputEditText nameChangeBox, descriptionChangeBox;
    private Button addScoreButton;
    private RecyclerView removePList, addAdminList, removeAdminList;
    private CheckBox allCheckRP, allCheckAA, allCheckRA;

    private ConstraintLayout root;

    private SelectParticipantsAdapter rpa, addAdminAdapter, removeAdminAdapter;

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
        setAndUpdateAll();

        if(!vm.mainUserIsAdmin()){
            editBtnImg.setVisibility(View.GONE);
            adminView.setVisibility(View.GONE);
        }
        if(!vm.mainUserIsCreator()){
            creatorOnlyBtn.setVisibility(View.GONE);
        }

        //Navigate from ChallengePage to AddingScorePage but atm just a placeholder
        view.findViewById(R.id.addScoreButton).setOnClickListener(
                view1 -> NavHostFragment.findNavController(ChallengePageFragment.this)
                .navigate(R.id.action_challengePageFragment_to_addingScorePage));

        //Checks if the difference between the height of the window and the activity's root view height
        //when the keyboard is open the difference is just over 1000, at least on my phone @Jonathan
        root.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int heightDiff = root.getRootView().getHeight() - root.getHeight();
            if(heightDiff > 1000){
                addScoreButton.setVisibility(View.GONE);
                editBtnImg.setVisibility(View.GONE);
                confirmNameChangeBtn.setVisibility(View.GONE);
                cancelNameChangeBtn.setVisibility(View.GONE);
                confirmDescriptionChangeBtn.setVisibility(View.GONE);
                cancelDescriptionChangeBtn.setVisibility(View.GONE);
            }
            else{
                addScoreButton.setVisibility(View.VISIBLE);
                editBtnImg.setVisibility(View.VISIBLE);
                confirmNameChangeBtn.setVisibility(View.VISIBLE);
                cancelNameChangeBtn.setVisibility(View.VISIBLE);
                confirmDescriptionChangeBtn.setVisibility(View.VISIBLE);
                cancelDescriptionChangeBtn.setVisibility(View.VISIBLE);
            }
        });

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
                removePView.setVisibility(View.GONE);
                creatorOnlyLayout.setVisibility(View.GONE);
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
            if(!(nameChangeBox.getText() == null || nameChangeBox.getText().toString().equals(""))){
                vm.setChallengeName(nameChangeBox.getText().toString());
                setInfoCard();
                editView.setVisibility(View.VISIBLE);
                editNameView.setVisibility((View.GONE));
                nameChangeBox.setText("");
            }
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
            if(!(descriptionChangeBox.getText() == null || descriptionChangeBox.getText().toString().equals(""))){
                vm.setDescription(descriptionChangeBox.getText().toString());
                setInfoCard();
                editView.setVisibility(View.VISIBLE);
                editDescriptionView.setVisibility((View.GONE));
                descriptionChangeBox.setText("");
            }
        });

        privacySwitch.setOnClickListener(view19 -> {
            vm.setPrivacy(!vm.isPrivate());
            setInfoCard();
        });

        editChallengeCopyCodeBtnImg.setOnClickListener(view110 -> {
            ClipboardManager copyPastaMaker = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Challenge code", vm.getCode());
            copyPastaMaker.setPrimaryClip(clip);
        });
        editChallengeParticipantsBtn.setOnClickListener(view114 -> {
            editView.setVisibility(View.GONE);
            removePView.setVisibility(View.VISIBLE);
        });

        allCheckRP.setOnClickListener(view111 ->{
            if(allCheckRP.isChecked()){
                rpa.selectAll();
            }
            else {
                rpa.unSelectAll();
            }
        });

        cancelRemoveP.setOnClickListener(view112 -> {
            editView.setVisibility(View.VISIBLE);
            removePView.setVisibility((View.GONE));
            rpa.unSelectAll();
            allCheckRP.setChecked(false);
        });
        confirmRemoveP.setOnClickListener(view113 -> {
            vm.removePlayers(rpa.getCheckedUserIDs());
            vm.removeAdmins(rpa.getCheckedUserIDs());
            setAndUpdateAll();
            editView.setVisibility(View.VISIBLE);
            removePView.setVisibility((View.GONE));
            rpa.unSelectAll();
            allCheckRP.setChecked(false);
        });

        creatorOnlyBtn.setOnClickListener(view115 -> {
            editView.setVisibility(View.GONE);
            creatorOnlyLayout.setVisibility(View.VISIBLE);
        });

        backArrowCreator.setRotation(180);
        backArrowCreator.setOnClickListener(view116 -> {
            editView.setVisibility(View.VISIBLE);
            creatorOnlyLayout.setVisibility(View.GONE);
        });

        addAdminBtn.setOnClickListener(view117 -> {
            addAdminLayout.setVisibility(View.VISIBLE);
            creatorOnlyLayout.setVisibility(View.GONE);
        });
        allCheckAA.setOnClickListener(view111 ->{
            if(allCheckAA.isChecked()){
                addAdminAdapter.selectAll();
            }
            else {
                addAdminAdapter.unSelectAll();
            }
        });

        cancelAddA.setOnClickListener(view112 -> {
            editView.setVisibility(View.VISIBLE);
            addAdminLayout.setVisibility((View.GONE));
            addAdminAdapter.unSelectAll();
            allCheckAA.setChecked(false);
        });
        confirmAddA.setOnClickListener(view113 -> {
            vm.addAdmins(addAdminAdapter.getCheckedUserIDs());
            setAndUpdateAll();
            editView.setVisibility(View.VISIBLE);
            addAdminLayout.setVisibility((View.GONE));
            addAdminAdapter.unSelectAll();
            allCheckAA.setChecked(false);
        });

        removeAdminBtn.setOnClickListener(view118 -> {
            removeAdminLayout.setVisibility(View.VISIBLE);
            creatorOnlyLayout.setVisibility(View.GONE);
        });
        allCheckRA.setOnClickListener(view111 ->{
            if(allCheckRA.isChecked()){
                removeAdminAdapter.selectAll();
            }
            else {
                removeAdminAdapter.unSelectAll();
            }
        });

        cancelRA.setOnClickListener(view112 -> {
            editView.setVisibility(View.VISIBLE);
            removeAdminLayout.setVisibility((View.GONE));
            removeAdminAdapter.unSelectAll();
            allCheckRA.setChecked(false);
        });
        confirmRA.setOnClickListener(view113 -> {
            vm.removeAdmins(removeAdminAdapter.getCheckedUserIDs());
            setAndUpdateAll();
            editView.setVisibility(View.VISIBLE);
            removeAdminLayout.setVisibility((View.GONE));
            removeAdminAdapter.unSelectAll();
            allCheckRA.setChecked(false);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setAndUpdateAll(){
        setPedestal();
        setLeaderBoard();
        setParticipants();
        setInfoCard();
        getUsers(users -> {
            setRemoveParticipants(users);
            setAddAdmin(users);
            setRemoveAdmin(users);
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
    //TODO FELIX FIX THIS PLZZ USE RIGHT DATABASE

    private void getUsers(UserListCallback callback){
        OnlineDatabase.getInstance().getUsers(callback);
    }
    private void setRemoveParticipants(ArrayList<User> users){

        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId() == vm.getCreatorId()){
                users.remove(i);
                break;
            }
        }
        removePList.setLayoutManager(new LinearLayoutManager(getContext()));
        rpa = new SelectParticipantsAdapter(users);
        removePList.setAdapter(rpa);
    }

    private void setAddAdmin(ArrayList<User> users){
        ArrayList<User> nonAdmins = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            if(!((vm.getAdmins().contains(users.get(i).getId())) || (users.get(i).getId() == vm.getCreatorId()))){
                nonAdmins.add(users.get(i));
            }
        }
        addAdminList.setLayoutManager(new LinearLayoutManager(getContext()));
        addAdminAdapter = new SelectParticipantsAdapter(nonAdmins);
        addAdminList.setAdapter(addAdminAdapter);
    }

    private void setRemoveAdmin(ArrayList<User> users){
        ArrayList<User> admins = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if(vm.getAdmins().contains(users.get(i).getId())){
                admins.add(users.get(i));
            }
        }
        removeAdminList.setLayoutManager(new LinearLayoutManager(getContext()));
        removeAdminAdapter = new SelectParticipantsAdapter(admins);
        removeAdminList.setAdapter(removeAdminAdapter);
    }

    /**
     * Populate the leaderBoard with participants.
     */
    private void setLeaderBoard(){
        rvcLeaderBoard.setLayoutManager(new LinearLayoutManager(getContext()));
        LeaderBoardAdapter leaderBoardAdapter = new LeaderBoardAdapter(requireActivity(),
                vm.getLeaderBoard().getValue());
        rvcLeaderBoard.setAdapter(leaderBoardAdapter);
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
            if (leaderBoardList.size() > 2) {
                setUserOnPedestal(userImg1,progressTxt1,
                        leaderBoardList.get(0).getKey(),leaderBoardList.get(0).getValue());
                setUserOnPedestal(userImg2,progressTxt2,
                        leaderBoardList.get(1).getKey(),leaderBoardList.get(1).getValue());
                setUserOnPedestal(userImg3,progressTxt3,
                        leaderBoardList.get(2).getKey(),leaderBoardList.get(2).getValue());
                pedestal2.setVisibility(View.VISIBLE);
                pedestal3.setVisibility(View.VISIBLE);
            }
            else if (leaderBoardList.size() == 2) {
                setUserOnPedestal(userImg1,progressTxt1,
                        leaderBoardList.get(0).getKey(),leaderBoardList.get(0).getValue());
                setUserOnPedestal(userImg2,progressTxt2,
                        leaderBoardList.get(1).getKey(),leaderBoardList.get(1).getValue());
                pedestal3.setVisibility(View.GONE);
            }else {
                setUserOnPedestal(userImg1,progressTxt1,
                        leaderBoardList.get(0).getKey(),leaderBoardList.get(0).getValue());
                pedestal2.setVisibility(View.GONE);
                pedestal3.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Set the ImageView and TextView for the pedestal.
     * @param img the ImageView to set
     * @param txt the TextView to set
     * @param userId the userid
     * @param score the users score
     */
    private void setUserOnPedestal(ImageView img, TextView txt, int userId, int score){
        OnlineDatabase.getInstance().getUser(userId, new DatabaseUserCallback() {
            @Override
            public void onCallback(User user) {
                img.setImageResource(user.getProfilePic());
            }
        });

        txt.setText(String.valueOf(score));
    }

    /**
     * Instantiate all the required views.
     * @param view the fragments view.
     */
    private void initializeViews(View view){
        pedestal2 = view.findViewById(R.id.pedestal2);
        pedestal3 = view.findViewById(R.id.pedestal3);
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

        removePList = view.findViewById(R.id.participantsScrollView);
        allCheckRP = view.findViewById(R.id.selectAllBox);
        confirmRemoveP = view.findViewById(R.id.confirmRemovalBtn);
        cancelRemoveP = view.findViewById(R.id.cancelRemovalBtn);
        removePView = view.findViewById(R.id.editInfoParticipantsView);
        editChallengeParticipantsBtn = view.findViewById(R.id.editChallengeParticipantsBtn);
        challengeHostView = view.findViewById(R.id.challengeHostView);

        creatorOnlyBtn = view.findViewById(R.id.creatorOnlySettingsBtn);
        creatorOnlyLayout = view.findViewById(R.id.creatorOnlyView);
        backArrowCreator = view.findViewById(R.id.backArrowCreator);
        addAdminBtn = view.findViewById(R.id.addAdminBtn);
        removeAdminBtn = view.findViewById(R.id.removeAdminBtn);
        numAdmins = view.findViewById(R.id.numAdminsView);

        addAdminLayout = view.findViewById(R.id.editAddAdminView);
        addAdminList = view.findViewById(R.id.addAdminRecyclerView);
        cancelAddA = view.findViewById(R.id.cancelAddAdminBtn);
        confirmAddA = view.findViewById(R.id.confirmAddAdminBtn);
        allCheckAA = view.findViewById(R.id.selectAllAddAdmin);

        removeAdminLayout = view.findViewById(R.id.editRemoveAdminView);
        removeAdminList = view.findViewById(R.id.removeAdminRecyclerView);
        cancelRA = view.findViewById(R.id.cancelRemoveAdminBtn);
        confirmRA = view.findViewById(R.id.confirmRemoveAdminBtn);
        allCheckRA = view.findViewById(R.id.selectAllRemoveAdmin);

        root = view.findViewById(R.id.challengePageRootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setInfoCard(){
        challengeNameTxt.setText(vm.getName());
        infoCardName.setText(vm.getName());

        descriptionTxt.setText(vm.getDescription());
        infoCardDescription.setText(vm.getDescription());

        numOfParticipants.setText(String.valueOf(vm.getLeaderBoard().getValue().size()));
        infoCardParticipantsNum.setText(String.valueOf(vm.getLeaderBoard().getValue().size()));

        numAdmins.setText(String.valueOf(vm.getNumOfAdmins()));

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
        vm.getCreatorName(user -> challengeHostView.setText(user.getUsername() + " (Host)"));


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