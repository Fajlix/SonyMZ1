package com.example.sonymz1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonymz1.Challenge;
import com.example.sonymz1.R;

import java.util.ArrayList;
/**
 * @author Jesper
 */

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ExampleViewHolder> {
    /**
     * Adapter class for Challenge Recycler view
     * mChallengeList holds representation off challenges.
     */
    private ArrayList<Challenge> mChallengeList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView background;
        public ImageView medal;
        public TextView challengeName;
        public TextView progressText;
        

        public ExampleViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
          background = itemView.findViewById(R.id.backgroundPic);
            medal = itemView.findViewById(R.id.medal);
            challengeName = itemView.findViewById(R.id.challengeName);
            progressText = itemView.findViewById(R.id.progressTxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });






      }
    }

    public ChallengeAdapter(ArrayList<Challenge> challengeList){
       mChallengeList = challengeList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_card, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
          Challenge currentItem = mChallengeList.get(position);
          holder.challengeName.setText(currentItem.getName());
          holder.background.setImageResource(currentItem.getChallengeBackground());
          holder.medal.setImageResource(currentItem.getMedal());
          holder.progressText.setText(currentItem.getDescription());

    }

    /**
     * method to get amount of items in challengeList.
     * @return
     */
    @Override
    public int getItemCount() {
        return mChallengeList.size();
    }
}
