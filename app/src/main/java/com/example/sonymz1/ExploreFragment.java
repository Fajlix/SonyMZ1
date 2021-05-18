package com.example.sonymz1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import com.example.sonymz1.Model.Challenge;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The page for exploring all the challenges and joining them.
 *
 * @author Wendy Pau
 */
public class ExploreFragment extends Fragment {

    private RecyclerView rvc;
    private ExploreAdapter exploreAdapter;
    private EditText searchInput;
    private TextView resultTxt;
    private Toolbar toolbar;

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

        initializeViews(view);
        customizeToolbar();
        populateRecyclerView();

        // filter the recyclerview after the users input
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // sets the text after the results.
                if (s.equals("") || s.toString().isEmpty()) resultTxt.setText("All Challenges" );
                else resultTxt.setText("Search results for: '" + s.toString() + " '" );

                exploreAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity)getActivity()).resetToolbar();
    }

    /**
     * Populates the recyclerview with all challenges.
     */
    private void populateRecyclerView() {
        rvc.setLayoutManager(new LinearLayoutManager(getContext()));
        ExploreFragment fragment = this;
        Database.getInstance().getAllChallenges(() -> {
            List<Challenge> mChallenges = Database.getInstance().getAllChallenges();
            exploreAdapter = new ExploreAdapter(mChallenges, fragment);
            rvc.setAdapter(exploreAdapter);
        });
    }

    private void customizeToolbar(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(R.drawable.explore_toolbar);
        toolbar.setLogo(R.color.transparent);
    }

    /**
     * Find all views for the variables.
     * @param view the fragments view
     */
    private void initializeViews(View view){
        rvc = view.findViewById(R.id.rvc);
        searchInput = view.findViewById(R.id.search_input);
        resultTxt = view.findViewById(R.id.results_txt);
    }
}