package com.example.sonymz1;

import java.util.List;
/**
 * @author Jesper
 * Class representing a section in the firstfragment recycle view.
 */

public class Section {
    private String sectionName;
    private List<String> sectionItem; // should hold challenge items

    public Section(String sectionName, List<String> sectionItem) {
        this.sectionName = sectionName;
        this.sectionItem = sectionItem;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<String> getSectionItem() {
        return sectionItem;
    }

    public void setSectionItem(List<String> sectionItem) {
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
