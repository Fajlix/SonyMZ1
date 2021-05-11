package com.example.sonymz1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonymz1.ChallengeViewModel;
import com.example.sonymz1.MainActivity;
import com.example.sonymz1.Model.Challenge;
import com.example.sonymz1.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Jesper
 * Class for adapter for the child recycler view on the mainpage/FirstFragment that contains the challenges
 * represented in cards.
 */

public class  ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ExampleViewHolder> {
    /**
     * Adapter class for Challenge Recycler view
     * mChallengeList holds representation off challenges.
     */
    private ArrayList<Challenge> mChallengeList;
    private OnItemClickListener mListener;
    private FragmentActivity fragmentActivity;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView background;
        public ImageView medal;
        public ImageView finishedOverlay;
        public TextView finishedText;
        public TextView challengeName;
        public TextView progressText;

        /**
         * method to hold views for the challenge card.
         * also contains listener for clicks on challenge cards.
         */

        public ExampleViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
          background = itemView.findViewById(R.id.backgroundPic);
            medal = itemView.findViewById(R.id.medal);
            challengeName = itemView.findViewById(R.id.challengeName);
            progressText = itemView.findViewById(R.id.progressTxt);
            finishedOverlay = itemView.findViewById(R.id.finishedOverlay);
            finishedText = itemView.findViewById(R.id.finishedText);

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

    public ChallengeAdapter(ArrayList<Challenge> challengeList, FragmentActivity fragmentActivity){
       mChallengeList = challengeList;
       this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_card, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }
    /**
     * method to handle views, also decides if to show finished view for challenge or not.
     * decides what text to show on the challenge card depending on
     * placement in tha challenge for main user.
     *
     */
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
          Challenge currentItem = mChallengeList.get(position);
          holder.challengeName.setText(currentItem.getName());
          holder.background.setImageResource(currentItem.getChallengeBackground());
          holder.medal.setImageResource(currentItem.getMedal());

          List<Map.Entry<Integer, Integer>> list =
                  new LinkedList<>(mChallengeList.get(position).getLeaderBoard().entrySet());
          ChallengeViewModel vm = new ViewModelProvider(fragmentActivity).get(ChallengeViewModel.class);
          int mainUserScore = vm.getMainUserScore();
          int mainUserIndex = list.indexOf(vm.getMainUser().getId());
          if(mainUserIndex == -1){ // list starts at -1
            holder.progressText.setText("You are first!");

         }else{
                int userBeforeIndex = list.indexOf(vm.getMainUser().getId())-1;
                int userBeforeScore =list.get(userBeforeIndex).getValue();
                int score = userBeforeScore - mainUserScore;
                holder.progressText.setText("You are "+score+" behind challenger "+userBeforeIndex+2);

        }
                if (currentItem.isFinished()){
                holder.finishedOverlay.setVisibility(View.VISIBLE);
                holder.finishedText.setVisibility(View.VISIBLE);
                holder.progressText.setText("You finished in place "+mainUserIndex+2+"!");

                 }else{
                    holder.finishedOverlay.setVisibility(View.INVISIBLE);
                    holder.finishedText.setVisibility(View.INVISIBLE);
          }
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
