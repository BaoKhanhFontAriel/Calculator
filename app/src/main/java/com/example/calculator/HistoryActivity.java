package com.example.calculator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        Button clearButton = findViewById(R.id.clearButton);

        RecyclerView viewHistoryLayout = findViewById(R.id.listViewHistory);



        HistoryAdapter historyAdapter = new HistoryAdapter(MainActivity.historyEntryList);

        viewHistoryLayout.setAdapter(historyAdapter);
        viewHistoryLayout.setLayoutManager(new LinearLayoutManager(this));

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.historyEntryList.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });

    }
}
