package com.example.sonymz1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sonymz1.Components.CounterComponent;

public class CounterView extends Fragment {
    private CounterComponent counterComponent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TODO change to counter view
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void setCounterComponent(CounterComponent counterComponent){
        this.counterComponent = counterComponent;
    }
    public String getCount(){
        return counterComponent.getValue();
    }
    public void addToCounter(int count){
        //lisener update
        counterComponent.addCount(count);
    }

}
