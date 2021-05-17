package com.example.sonymz1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sonymz1.Adapters.ExploreAdapter;
import com.example.sonymz1.Database.Database;

import org.jetbrains.annotations.NotNull;


public class ExploreFragment extends Fragment {

    private RecyclerView rvc;
    private EditText searchInput;
    private TextView resultTxt;

    public ExploreFragment() {
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
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvc = view.findViewById(R.id.rvc);
        searchInput = view.findViewById(R.id.search_input);
        resultTxt = view.findViewById(R.id.results_txt);

        rvc.setLayoutManager(new LinearLayoutManager(getContext()));
        ExploreAdapter exploreAdapter = new ExploreAdapter(Database.getInstance().getChallenges(),this);
        rvc.setAdapter(exploreAdapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.equals("") || s.toString().isEmpty()){
                    resultTxt.setText("All Challenges" );
                }else {
                    resultTxt.setText("Search results for: '" + s.toString() + " '" );
                }

                exploreAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}