package com.example.calculator;

import java.util.ArrayList;
import java.util.List;

public class HistoryEntry {
    private String historyInput;
    private double historyAnswer;


    public HistoryEntry(String s, double result) {
        historyInput = s;
        historyAnswer = result;
    }

    public String getHistoryInput() {
        return historyInput;
    }

    public double getHistoryAnswer() {
        return historyAnswer;
    }

//    public List<HistoryEntry> getHistoryList() {
//        return historyList;
//    }
//
//    public void setHistoryList(HistoryEntry he) {
//        this.historyList.add(he);
//    }
//
//    List<HistoryEntry> historyList;
}


