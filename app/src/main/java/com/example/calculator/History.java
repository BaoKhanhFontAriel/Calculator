package com.example.calculator;

import java.util.ArrayList;

public class History {

    /////////////////////////////////
    private ArrayList<HistoryEntry> historyEntryList = new ArrayList<>();

    //////////////////////////////////
    private static History instance;

    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }
    /////////////////////////////////
   ///////////////////////////////////////////////////////////////

    public void addEntry(HistoryEntry entry) {
        historyEntryList.add(entry);

    }

    public ArrayList<HistoryEntry> getHistoryEntryList() {


        return historyEntryList;
    }


    public void clear() {
           historyEntryList.clear();
    }


}
