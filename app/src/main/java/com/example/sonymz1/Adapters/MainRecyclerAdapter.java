package com.example.sonymz1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.R;
import com.example.sonymz1.Section;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    List<Section> sectionList;

    public MainRecyclerAdapter(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycleview_section, parent, false);
        
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Section section = sectionList.get(position);
        String sectionName = section.getSectionName();
        ArrayList<Challenge> items = section.getSectionItem();
       


        holder.sectionNameTextView.setText(sectionName);

        ChallengeAdapter childRecyclerAdapter = new ChallengeAdapter(items);
        holder.childRecyclerView.setAdapter(childRecyclerAdapter);
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

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
