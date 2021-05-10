package com.example.sonymz1.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonymz1.ChallengePageFragment;
import com.example.sonymz1.ChallengeViewModel;
import com.example.sonymz1.Database.DatabaseUserCallback;
import com.example.sonymz1.Database.OnlineDatabase;
import com.example.sonymz1.R;
import com.example.sonymz1.Model.User;

import java.util.LinkedList;
import java.util.Map;

/**
 * Adapter for populating the recyclerview. It contains the main user and the other 2
 * challengers near the main user.
 *
 * @author Wendy Pau
 */
public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
    private Map<Integer,Integer> leaderBoard;
    private ChallengeViewModel vm;

    public LeaderBoardAdapter(FragmentActivity fragActivity, Map<Integer, Integer> leaderBoard) {
        vm = new ViewModelProvider(fragActivity).get(ChallengeViewModel.class);
        this.leaderBoard = leaderBoard;
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
        int userId = vm.getMainUser().getId();
        LinkedList<Integer> leaderBoardList = new LinkedList<>(leaderBoard.keySet());
        int rank = leaderBoardList.indexOf(userId + 1);
        StringBuilder sb = new StringBuilder();
        System.out.println(rank + "yes");
        if (rank>0) {
            // If the main user is first, get the 2 challengers below
            if (rank == 1) {
                switch (position) {
                    case 0:
                        sb.append(rank);
                        CardView cardView = (CardView) holder.itemView;
                        cardView.setCardBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        userId = leaderBoardList.get(rank);
                        sb.append(rank + 1);
                        break;
                    case 2:
                        userId = leaderBoardList.get(rank + 1);
                        sb.append(rank + 2);
                        break;
                }
            }
            // If the main user is in the middle of the leader board then get the users
            // above and below the main user
            else if (rank == leaderBoardList.size()) {   // if main user is last
                switch (position) {
                    case 0:
                        userId = leaderBoardList.get(rank - 3);
                        sb.append(rank - 2);
                        break;
                    case 1:
                        userId = leaderBoardList.get(rank - 2);
                        sb.append(rank - 1);
                        break;
                    case 2:
                        userId = leaderBoardList.get(rank - 1);
                        sb.append(rank);
                        CardView cardView = (CardView) holder.itemView;
                        cardView.setCardBackgroundColor(Color.WHITE);
                        break;
                }
            }
            // If the challenger is last then get the 2 challengers above
            else {
                switch (position) {
                    case 0:
                        userId = leaderBoardList.get(rank - 2);
                        sb.append(rank - 1);
                        break;
                    case 1:
                        userId = leaderBoardList.get(rank - 1);
                        sb.append(rank);
                        CardView cardView = (CardView) holder.itemView;
                        cardView.setCardBackgroundColor(Color.WHITE);
                        break;
                    case 2:
                        userId = leaderBoardList.get(rank);
                        sb.append(rank + 1);
                        break;
                }
            }

            //Give the right rank end thing.
            switch (sb.toString()) {
                case "1":
                    sb.append("st");
                    break;
                case "2":
                    sb.append("nd");
                    break;
                case "3":
                    sb.append("rd");
                    break;
                default:
                    sb.append("th");
                    break;
            }
            OnlineDatabase.getInstance().getUser(userId, new DatabaseUserCallback() {
                @Override
                public void onCallback(User user) {
                    holder.rank.setText(sb.toString());
                    holder.usernametxt.setText(user.getUsername());
                    holder.progressTxt.setText(String.valueOf(leaderBoard.get(user.getId()))); // add unit
                    holder.userImg.setImageResource(user.getProfilePic());
                }
            });
        }
    }

    /**
     * Sets how many items that populates the recyclerview.
     * @return
     */
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
