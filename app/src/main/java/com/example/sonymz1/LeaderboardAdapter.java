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

    public LeaderboardAdapter(Activity context, Map<Integer, Integer> leaderboard) {
        this.leaderboard = leaderboard;
        this.context = context;
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
        List<Map.Entry<Integer, Integer>> leaderboardList =
                new LinkedList<>(leaderboard.entrySet());
        User user = users.getUserMap().get(leaderboardList.get(position).getKey());
        holder.usernametxt.setText(user.getUsername());
        holder.progressTxt.setText(String.valueOf(leaderboardList.get(position).getValue())); // add unit
        holder.userImg.setImageResource(user.getProfilePic());
        switch (position) {
            case 0: holder.rank.setText(1 + "st"); break;
            case 1:
                holder.rank.setText(2 + "nd");
                CardView cardView = (CardView) holder.itemView;
                cardView.setCardBackgroundColor(Color.WHITE);
                break;
            case 2: holder.rank.setText(3 + "rd"); break;
            default: holder.rank.setText(position+1 + "th"); break;
        }
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
