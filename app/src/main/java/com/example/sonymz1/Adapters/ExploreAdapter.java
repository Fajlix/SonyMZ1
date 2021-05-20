package com.example.sonymz1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonymz1.ChallengeViewModel;
import com.example.sonymz1.Database.Database;
import com.example.sonymz1.ExploreFragment;
import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.R;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for creating the list items in the recyclerview in ExploreFragment.
 *
 * @author Wendy Pau
 */
public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> implements Filterable {

    private List<Challenge> mChallenges;
    private List<Challenge> mChallengesFiltered;
    private ExploreFragment fragment;

    /**
     * ExploreAdapter constructor
     * @param mChallenges list of all challenges
     * @param fragment the ExploreFragment fragment
     */
    public ExploreAdapter(List<Challenge> mChallenges, ExploreFragment fragment) {
        this.mChallenges = mChallenges;
        this.mChallengesFiltered = mChallenges;
        this.fragment = fragment;
    }

    @NotNull
    @Override
    public ExploreAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_challenge_card, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExploreAdapter.ViewHolder holder, int position) {
        holder.challengeTitle.setText(mChallengesFiltered.get(position).getName());
        holder.challengeTitle2.setText(mChallengesFiltered.get(position).getName());
        holder.coverImg.setBackgroundResource(R.drawable.default_background);
        String hostname = Database.getInstance().getUser(mChallengesFiltered
                .get(position).getCreatorId())
                .getUsername();
        holder.hostName.setText(hostname);

        holder.enterBtn.setOnClickListener(v -> {
            String str = holder.codeInput.getText().toString();
            if (str != null || str.equals("")){
                // if the code input equals the challenge code then enter the challenge and
                // add the user
                Challenge challenge = mChallengesFiltered.get(position);
                if (str.equals(challenge.getChallengeCode())) {
                    Database.getInstance().setActiveChallenge(challenge);
                    ChallengeViewModel vm = new ViewModelProvider(fragment.requireActivity())
                            .get(ChallengeViewModel.class);
                    challenge.addPlayer(Database.getInstance().getMainUser().getId());
                    Database.getInstance().saveChallenge(challenge);
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_ExploreFragment_to_challengePageFragment);
                }
                else {
                    holder.codeInput.setError("Wrong code");
                }
            }
            else{
                holder.codeInput.setError("Fill in the code");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChallengesFiltered.size();
    }

    /**
     * Filter the list of all the challenges.
     * @return list that is filtered
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()){
                    mChallengesFiltered = mChallenges;
                }
                else {
                    List<Challenge> lstFiltered = new ArrayList<>();

                    // check if the input is a challenge code.
                    Challenge challenge = containsCode(Key);
                    if (challenge != null) {
                        lstFiltered.add(challenge);
                    }
                    // filter the list of challenges
                    else {
                        for (Challenge row: mChallenges) {
                            if (row.getName().toLowerCase().contains(Key.toLowerCase())){
                                lstFiltered.add(row);
                            }
                        }
                    }

                    mChallengesFiltered = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mChallengesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mChallengesFiltered = (List<Challenge>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    /**
     * Check if the input is a challenge code.
     * @param Key The user input.
     * @return If it is a challenge code, return the challenge. If not, return null.
     */
    private Challenge containsCode(String Key){
        if (Key.length() == 4) {
            for (Challenge row: mChallenges) {
                if (row.getChallengeCode().toLowerCase().contains(Key.toLowerCase())){
                    return row;
                }
            }
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView challengeTitle, challengeTitle2, hostName;
        ImageView coverImg;
        Button enterBtn;
        ConstraintLayout challenge_container, code_container;
        EditText codeInput;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            challengeTitle = itemView.findViewById(R.id.challenge_title);
            challengeTitle2 = itemView.findViewById(R.id.challenge_title2);
            hostName = itemView.findViewById(R.id.host_txt);
            enterBtn = itemView.findViewById(R.id.enter_btn);
            coverImg = itemView.findViewById(R.id.cover_img);
            challenge_container = itemView.findViewById(R.id.challenge_view);
            code_container = itemView.findViewById(R.id.code_view);
            codeInput = itemView.findViewById(R.id.codeTxt);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (code_container.getVisibility() == View.GONE){
                code_container.setVisibility(View.VISIBLE);
                challengeTitle.setVisibility(View.GONE);
                hostName.setVisibility(View.GONE);
            }
            else{
                code_container.setVisibility(View.GONE);
                challengeTitle.setVisibility(View.VISIBLE);
                hostName.setVisibility(View.VISIBLE);
            }
        }
    }
}
