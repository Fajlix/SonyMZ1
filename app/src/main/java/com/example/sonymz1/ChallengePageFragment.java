package com.example.sonymz1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChallengePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChallengePageFragment extends Fragment {

    private final Challenge challenge = new Challenge("Challenge");
    private AllUsers users = new AllUsers();
    private LeaderboardAdapter leaderboardAdapter;
    private ImageView userImg1, userImg2, userImg3;
    private TextView progressTxt1, progressTxt2, progressTxt3, moreBtn;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rvcLeaderboard;

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
        initializeViews(view);

        challenge.addPlayer(users.getUserMap().get(1).getId(),6);
        challenge.addPlayer(users.getUserMap().get(2).getId(),5);
        challenge.addPlayer(users.getUserMap().get(3).getId(),10);
        setPedestal();

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvcLeaderboard.setLayoutManager(linearLayoutManager);
        leaderboardAdapter = new LeaderboardAdapter(getActivity(),challenge.getLeaderBoard());
        rvcLeaderboard.setAdapter(leaderboardAdapter);

        if(challenge.getLeaderBoard().size() > 3){
            moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Test
                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();

                    // show participants view
                }
            });
        }else moreBtn.setVisibility(View.GONE);
    }

    /**
     * Set the top 3 users info on the pedestal.
     */
    private void setPedestal(){
        // Case if there are only 2 users??
        List<Map.Entry<Integer, Integer>> leaderboardList =
                new LinkedList<>(challenge.getLeaderBoard().entrySet());
        userImg1.setImageResource(users.getUserMap().get(leaderboardList.get(0).getKey()).getProfilePic());
        userImg2.setImageResource(users.getUserMap().get(leaderboardList.get(1).getKey()).getProfilePic());
        userImg3.setImageResource(users.getUserMap().get(leaderboardList.get(2).getKey()).getProfilePic());
        progressTxt1.setText(String.valueOf(leaderboardList.get(0).getValue()));
        progressTxt2.setText(String.valueOf(leaderboardList.get(1).getValue()));
        progressTxt3.setText(String.valueOf(leaderboardList.get(2).getValue()));
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
    }
}