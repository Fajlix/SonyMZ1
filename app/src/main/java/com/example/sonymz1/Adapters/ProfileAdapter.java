package com.example.sonymz1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonymz1.ChallengePageFragment;
import com.example.sonymz1.ChallengeViewModel;
import com.example.sonymz1.Database.Database;
import com.example.sonymz1.Model.User;
import com.example.sonymz1.ProfilePageFragment;
import com.example.sonymz1.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Adapter for adding friends to the recyclerview on the profile page.
 *
 * @author Elina W and Viktor W.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{
    private List<User> friends;
    private ProfilePageFragment fragment;
    private ChallengeViewModel vm;

    public ProfileAdapter(ProfilePageFragment fragment, List<User> friends) {
        vm = new ViewModelProvider(fragment.requireActivity()).get(ChallengeViewModel.class);
        this.friends = friends;
        this.fragment = fragment;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username, friendIdNumber;
        ImageView friendImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.Username);
            friendIdNumber = itemView.findViewById(R.id.friendIdNumber);
            friendImg = itemView.findViewById(R.id.friendImg);
        }
    }


    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends, parent,false);
        return new ProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        holder.username.setText(friends.get(position).getUsername());
        holder.friendIdNumber.setText(String.valueOf(friends.get(position).getId()));
        holder.friendImg.setImageResource(R.drawable.logo);
    }

    @Override
    public int getItemCount() {
       if (friends == null){
           return 0;
       }
        return friends.size();
    }
}
