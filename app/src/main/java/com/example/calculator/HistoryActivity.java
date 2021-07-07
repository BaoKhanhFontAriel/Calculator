package com.example.calculator;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_history);

        RecyclerView viewHistoryLayout = findViewById(R.id.listViewHistory);
        //Button clearButton = findViewById(R.id.clearButton);

        ArrayList<HistoryEntry> historyList = new ArrayList<>();
//        historyList.add(new HistoryEntry( "1+2",3));
//        historyList.add(new HistoryEntry( "1+2",3));
        MainActivity mainActivity = new MainActivity();
        historyList = mainActivity.getHistoryList();
        HistoryAdapter historyAdapter = new HistoryAdapter(historyList);

        viewHistoryLayout.setAdapter(historyAdapter);
        viewHistoryLayout.setLayoutManager(new LinearLayoutManager(this));


    }
}
