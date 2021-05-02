package com.example.sonymz1;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Adapter for populating the recyclerview with all the participants.
 *
 * @author Wendy P.
 */
public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewHolder> {
    private Map<Integer,Integer> leaderBoard;
    private ChallengePageFragment context;
    private ChallengeViewModel vm;

    public ParticipantsAdapter(ChallengePageFragment context, Map<Integer, Integer> leaderBoard) {
        vm = new ViewModelProvider(context).get(ChallengeViewModel.class);
        this.leaderBoard = leaderBoard;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParticipantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participants, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantsAdapter.ViewHolder holder, int position) {
        List<Map.Entry<Integer, Integer>> leaderBoardList =
                new LinkedList<>(leaderBoard.entrySet());
        User challenger = vm.getUsers().get(leaderBoardList.get(position).getKey());
        holder.username.setText(challenger.getUsername());
        holder.scoreTxt.setText("Score: " + leaderBoardList.get(position).getValue()); // add unit
        holder.userImg.setImageResource(challenger.getProfilePic());

        //Changes cards background colour for top 3 challengers.
        CardView cardView = (CardView) holder.itemView;
        switch (position) {
            case 0:
                holder.rank.setText(1 + "st");
                holder.rank.setTextColor(Color.WHITE);
                holder.username.setTextColor(Color.WHITE);
                holder.scoreTxt.setTextColor(Color.WHITE);
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.first));
                break;
            case 1:
                holder.rank.setText(2 + "nd");
                holder.rank.setTextColor(Color.WHITE);
                holder.username.setTextColor(Color.WHITE);
                holder.scoreTxt.setTextColor(Color.WHITE);
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.second));
                break;
            case 2:
                holder.rank.setText(3 + "rd");
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.third));
                break;
            default: holder.rank.setText(position+1 + "th"); break;
        }

        //Set blue colour to mark where the user is.
        if (leaderBoardList.get(position).getKey() == vm.getMainUser().getId()){
            holder.rank.setTextColor(context.getResources().getColor(R.color.blue));
            holder.username.setTextColor(context.getResources().getColor(R.color.blue));
            holder.scoreTxt.setTextColor(context.getResources().getColor(R.color.blue));
        }
    }

    @Override
    public int getItemCount() { return leaderBoard.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username, scoreTxt, rank;
        ImageView userImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTxt);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            rank = itemView.findViewById(R.id.rankTxt2);
            userImg = itemView.findViewById(R.id.participantImg);
        }
    }
}
