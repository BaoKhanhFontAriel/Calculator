package com.example.calculator;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_history);

        Button clearButton = findViewById(R.id.clearButton);

        recyclerView = findViewById(R.id.listViewHistory);

        HistoryAdapter historyAdapter = new HistoryAdapter(History.getInstance().getHistoryEntryList(), callback);

        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History.getInstance().clear();
                historyAdapter.notifyDataSetChanged();
            }
        });

    }

    HistoryAdapter.IEntryClicked callback = new HistoryAdapter.IEntryClicked() {
        @Override
        public void onItemClicked(int position) {
            HistoryEntry entry = ((HistoryAdapter)recyclerView.getAdapter()).getEntryInfo(position);
            Intent intent =  new Intent (getApplicationContext(), MainActivity.class);
            intent.putExtra("historyAnswer", String.valueOf(entry.getHistoryAnswer()));
            Log.d("TAG", "historyActivilty oncItemClicked is clicked" + entry.getHistoryAnswer());
            startActivity(intent);
        }
    };

}
