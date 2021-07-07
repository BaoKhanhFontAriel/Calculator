package com.example.calculator;

public class HistoryEntry {
    private String historyInput;
    private double historyAnswer;


    public HistoryEntry(String historyInput, double historyAnswer) {
        this.historyInput = historyInput;
        this.historyAnswer = historyAnswer;
    }

    public String getHistoryInput() {
        return historyInput;
    }

    public double getHistoryAnswer() {
        return historyAnswer;
    }
}

