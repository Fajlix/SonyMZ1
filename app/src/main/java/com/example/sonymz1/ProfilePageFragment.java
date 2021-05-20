package com.example.sonymz1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sonymz1.Adapters.ParticipantsAdapter;
import com.example.sonymz1.Adapters.ProfileAdapter;
import com.example.sonymz1.Model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Elina W, Viktor W
 */
public class ProfilePageFragment extends Fragment {

    private ChallengeViewModel vm;
    private RecyclerView rvcfriendList;

    public ProfilePageFragment() {
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
        return inflater.inflate(R.layout.fragment_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(ChallengeViewModel.class);
        initializeViews(view);
        rvcfriendList.setLayoutManager(new LinearLayoutManager(getContext()));

        /* Test
        vm.getMainUser().getFriends().add(new User("Friend 1", 0));
        vm.getMainUser().getFriends().add(new User("Friend 2", 1));
         */

        ProfileAdapter profileAdapter = new ProfileAdapter(this,
                vm.getMainUser().getFriends());
        rvcfriendList.setAdapter(profileAdapter);
    }

    private void initializeViews(View view){
        rvcfriendList = view.findViewById(R.id.rvcfriendList);
    }
}