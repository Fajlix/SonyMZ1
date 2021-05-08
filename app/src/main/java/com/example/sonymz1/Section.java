package com.example.sonymz1;

import java.util.List;

public class Section {
    private String sectionName;
    private List<String> sectionItem;

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
