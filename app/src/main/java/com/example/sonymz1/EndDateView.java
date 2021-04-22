package com.example.sonymz1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sonymz1.Components.DateComponent;

public class EndDateView extends Fragment {

    private DateComponent dateComponent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TODO change to endDate view
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void setDateComponent(DateComponent dateComponent){
        this.dateComponent = dateComponent;
    }
    public String getDate(){
        return dateComponent.getValue();
    }
}
