package com.example.sonymz1;

import com.example.sonymz1.Model.Challenge;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Jesper
 * Class representing a section in the firstfragment recycle view.
 */

public class Section {
    private String sectionName;
    private ArrayList<Challenge> sectionItem; // should hold challenge items

    public Section(String sectionName, ArrayList<Challenge> sectionItem) {
        this.sectionName = sectionName;
        this.sectionItem = sectionItem;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<Challenge> getSectionItem() {
        return sectionItem;
    }

    public void setSectionItem(ArrayList<Challenge> sectionItem) {
        this.sectionItem = sectionItem;
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionName='" + sectionName + '\'' +
                ", sectionItem=" + sectionItem +
                '}';
    }
}
