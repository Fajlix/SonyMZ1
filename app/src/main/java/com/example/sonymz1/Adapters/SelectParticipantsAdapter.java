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
public class SelectParticipantsAdapter extends RecyclerView.Adapter<SelectParticipantsAdapter.ViewHolder>{
    private ArrayList<User> users;
    private ArrayList<Integer> checkedUserIDs = new ArrayList<>();
    private boolean isAllSelected = false;
    public SelectParticipantsAdapter(ArrayList<User> users){
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.infocard_user, parent, false);
        return new SelectParticipantsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        System.out.println(users.toString());
        holder.checkbox.setText(user.getUsername());
        holder.userId = user.getId();
        if (!isAllSelected){
            holder.checkbox.setChecked(false);
        }
        else  {
            holder.checkbox.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void selectAll(){
        isAllSelected = true;
        checkedUserIDs = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            checkedUserIDs.add(users.get(i).getId());
        }
        notifyDataSetChanged();
    }
    public void unSelectAll(){
        isAllSelected = false;
        checkedUserIDs = new ArrayList<>();
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getCheckedUserIDs() {

        return checkedUserIDs;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkbox;
        Integer userId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.removeCheckBox);
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(checkbox.isChecked()){
                        checkedUserIDs.add(userId);
                    }
                    else{
                        checkedUserIDs.remove(userId);
                    }
                }
            });
        }
    }
}
