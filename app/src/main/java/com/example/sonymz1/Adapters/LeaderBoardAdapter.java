package com.example.sonymz1.Adapters;

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

import com.example.sonymz1.ChallengePageFragment;
import com.example.sonymz1.ChallengeViewModel;
import com.example.sonymz1.R;
import com.example.sonymz1.User;

import java.util.LinkedList;
import java.util.Map;

/**
 * Adapter for populating the recyclerview with 3 challengers where the user are in the leaderboard.
 *
 * @author Wendy Pau
 */
public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
    private Map<Integer,Integer> leaderBoard;
    private ChallengePageFragment context;
    private ChallengeViewModel vm;

    public LeaderBoardAdapter(ChallengePageFragment context, Map<Integer, Integer> leaderBoard) {
        vm = new ViewModelProvider(context).get(ChallengeViewModel.class);
        this.leaderBoard = leaderBoard;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = vm.getMainUser();
        LinkedList<Integer> leaderBoardList = new LinkedList<>(leaderBoard.keySet());
        int rank = leaderBoardList.indexOf(user.getId()) + 1;
        StringBuilder sb = new StringBuilder();

        if (rank == 1){ // if main user is first
            sb.append(rank);
            CardView cardView = (CardView) holder.itemView;
            cardView.setCardBackgroundColor(Color.WHITE);
        }
        else if (rank == leaderBoardList.size()){   // if main user is last
            switch (position) {
                case 0:
                    user = vm.getUsers().get(leaderBoardList.get(rank-3));
                    sb.append(rank-2);
                    break;
                case 1:
                    user = vm.getUsers().get(leaderBoardList.get(rank-2));
                    sb.append(rank-1);
                    break;
                case 2:
                    user = vm.getUsers().get(leaderBoardList.get(rank-1));
                    sb.append(rank);
                    CardView cardView = (CardView) holder.itemView;
                    cardView.setCardBackgroundColor(Color.WHITE);
                    break;
            }
        }
        else {
            //Get the challenger above and below the main user.
            switch (position) {
                case 0:
                    user = vm.getUsers().get(leaderBoardList.get(rank - 2));
                    sb.append(rank - 1);
                    break;
                case 1:
                    user = vm.getUsers().get(leaderBoardList.get(rank - 1));
                    sb.append(rank);
                    CardView cardView = (CardView) holder.itemView;
                    cardView.setCardBackgroundColor(Color.WHITE);
                    break;
                case 2:
                    user = vm.getUsers().get(leaderBoardList.get(rank));
                    sb.append(rank + 1);
                    break;
            }
        }

        //Give the right rank end thing.
        switch (sb.toString()) {
            case "1": sb.append("st"); break;
            case "2": sb.append("nd"); break;
            case "3": sb.append("rd"); break;
            default: sb.append("th"); break;
        }
        holder.rank.setText(sb.toString());
        holder.usernametxt.setText(user.getUsername());
        holder.progressTxt.setText(String.valueOf(leaderBoard.get(user.getId()))); // add unit
        holder.userImg.setImageResource(user.getProfilePic());
    }

    @Override
    public int getItemCount() {
        if (leaderBoard.size() == 1) {
            return 1;
        }
        else if (leaderBoard.size() == 2){
            return 2;
        }
        else return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView usernametxt, progressTxt, rank;
        ImageView userImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernametxt = itemView.findViewById(R.id.username);
            progressTxt = itemView.findViewById(R.id.userProgressTxt);
            rank = itemView.findViewById(R.id.rankTxt);
            userImg = itemView.findViewById(R.id.userImg);
        }
    }
}