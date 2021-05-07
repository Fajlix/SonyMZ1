package com.example.sonymz1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonymz1.R;
import com.example.sonymz1.User;

import java.util.ArrayList;

/**
 * @author Jonathan
 */
public class RemoveParticipantsAdapter extends RecyclerView.Adapter<RemoveParticipantsAdapter.ViewHolder>{
    private ArrayList<User> users;
    private boolean isAllSelected = false;
    public RemoveParticipantsAdapter(ArrayList<User> users){
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.infocard_user, parent, false);
        return new RemoveParticipantsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.checkbox.setText(user.getUsername());
        if (!isAllSelected){
            holder.checkbox.setChecked(false);
        }
        else  holder.checkbox.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void selectAll(){
        isAllSelected = true;
        notifyDataSetChanged();
    }
    public void unSelectAll(){
        isAllSelected = false;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.removeCheckBox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkbox.setChecked(!checkbox.isChecked());
                }
            });
        }
    }
}
