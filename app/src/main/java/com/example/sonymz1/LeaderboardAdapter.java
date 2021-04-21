package com.example.sonymz1;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Adapter for populating the recyclerview for the leaderboard.
 *
 * TODO Fix so that the logged in user is shown in the leaderboard.
 * @author Wendy
 */
public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    private Map<Integer,Integer> leaderboard;
    private Activity context;
    private AllUsers users;
    private User user;

    public LeaderboardAdapter(Activity context, Map<Integer, Integer> leaderboard, User user) {
        this.leaderboard = leaderboard;
        this.context = context;
        this.user = user;
        this.users = new AllUsers();
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
        LinkedList<Integer> leaderboardList = new LinkedList<>(leaderboard.keySet());
        int rank = leaderboardList.indexOf(user.getId()) + 1;
        StringBuilder sb = new StringBuilder();
        //Get the challenger above and below the user.
        switch (position) {
            case 0:
                user = users.getUserMap().get(leaderboardList.get(rank-2));
                sb.append(rank-1);
                break;
            case 1:
                user = users.getUserMap().get(leaderboardList.get(rank));
                sb.append(rank+1);
                CardView cardView = (CardView) holder.itemView;
                cardView.setCardBackgroundColor(Color.WHITE);
                break;
            case 2:
                user = users.getUserMap().get(leaderboardList.get(rank));
                sb.append(rank+1);
                break;
        }

        //Give right rank end thing.
        switch (sb.toString()) {
            case "1": sb.append("st"); break;
            case "2": sb.append("nd"); break;
            case "3": sb.append("rd"); break;
            default: sb.append("th"); break;
        }
        holder.rank.setText(sb.toString());
        holder.usernametxt.setText(user.getUsername());
        holder.progressTxt.setText(String.valueOf(leaderboard.get(user.getId()))); // add unit
        holder.userImg.setImageResource(user.getProfilePic());
    }

    @Override
    public int getItemCount() {
        return 3;
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
