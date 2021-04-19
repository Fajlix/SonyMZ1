package com.example.sonymz1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ExampleViewHolder> {
    private ArrayList<ChallengeItem> mChallengeList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView1;
        public ImageView mImageView2;
        public ImageView mImageView3;
        public TextView mTextView1;
        public TextView mTextView2;
        

        public ExampleViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView1 = itemView.findViewById(R.id.arrow);
            mImageView2 = itemView.findViewById(R.id.backgroundPic);
            mImageView3 = itemView.findViewById(R.id.medal);
            mTextView1 = itemView.findViewById(R.id.challengeName);
            mTextView2 = itemView.findViewById(R.id.progressTxt);

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

    public ChallengeAdapter(ArrayList<ChallengeItem> challengeList){
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
          ChallengeItem currentItem = mChallengeList.get(position);

          holder.mImageView1.setImageResource(currentItem.getmImageResource1());
          holder.mImageView2.setImageResource(currentItem.getmImageResource2());
          holder.mImageView3.setImageResource(currentItem.getmImageResource3());
          holder.mTextView1.setText(currentItem.getmText1());
          holder.mTextView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mChallengeList.size();
    }
}
