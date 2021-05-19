package com.example.sonymz1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sonymz1.Database.Database;
import com.example.sonymz1.HomeFragment;
import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.R;
import com.example.sonymz1.Section;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Jesper
 * Adapter for the mainrecycleview on main page/FirstFragment that contains sections.
 * sectionList holds representation of sections that holds challenges.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    List<Section> sectionList;
    private HomeFragment fragment;
    private ArrayList<Challenge> challengeList;


    public MainRecyclerAdapter(List<Section> sectionList, HomeFragment fragment) {
        this.sectionList = sectionList;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycleview_section, parent, false);
        
        return new ViewHolder(view);
    }
    /**
     * Method to connect connect childRecycleView to correct adapter.
     * and a on click method to be able to click correct challenge card in the sections.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Section section = sectionList.get(position);
        String sectionName = section.getSectionName();
        ArrayList<Challenge> items = section.getSectionItem();
        holder.sectionNameTextView.setText(sectionName);
        ChallengeAdapter childRecyclerAdapter = new ChallengeAdapter(items, fragment.requireActivity());
        holder.childRecyclerView.setAdapter(childRecyclerAdapter);
        holder.childRecyclerView.setNestedScrollingEnabled(false);

        childRecyclerAdapter.setOnItemClickListener(new ChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                challengeList =sectionList.get(0).getSectionItem();
                for (Challenge challenge: sectionList.get(1).getSectionItem()) {
                    challengeList.add(challenge);
                }
                Database db = Database.getInstance();
                for (Challenge challenge : db.getChallenges()){
                    if (challenge.equals(challengeList.get(position))){
                        db.setActiveChallenge(challenge);
                        break;
                    }
                }
               childRecyclerAdapter.notifyItemChanged(position);
                // Temp on click for test will change to navigate to specific challenge when it exists
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_FirstFragment_to_challengePageFragment);
            }
        });
    }
    /**
     * method to get amount of items in sectionList.
     * @return
     */

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    /**
     * Method to populate challenge lists.
     */

    /**
     * Method to hold views found in recycleview_section.XML
     */

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView sectionNameTextView;
        RecyclerView childRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionNameTextView = itemView.findViewById(R.id.sectionNameTextView);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
        }
    }

}
