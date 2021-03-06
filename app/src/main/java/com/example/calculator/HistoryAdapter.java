package com.example.calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {


    private List<HistoryEntry> historyList =  new ArrayList<>();

    public HistoryAdapter(List<HistoryEntry> historyList, IEntryClicked callback) {
        this.historyList = historyList;
        this.callback = callback;
    }

    public HistoryEntry getEntryInfo(int pos){
        return historyList.get(pos);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View historyView = inflater.inflate(R.layout.list_item_history_layout, parent, false);

        // Return a new holder instance
        return new HistoryViewHolder(historyView);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull HistoryAdapter.HistoryViewHolder holder, int position) {
        HistoryEntry entry = historyList.get(position);
        holder.line1.setText(entry.getHistoryInput());
        holder.line2.setText("= " + String.valueOf(entry.getHistoryAnswer()));
    }


    @Override
    public int getItemCount() {
        if (historyList.size() > 20){
            historyList.remove(0);
        }
        return historyList.size();
    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView line1;
        public TextView line2;


        public HistoryViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            line1 = itemView.findViewById(R.id.historyInput);
            line2 = itemView.findViewById(R.id.historyAnswer);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            callback.onItemClicked(getAdapterPosition());
        }
    }

    private IEntryClicked callback;

    public interface IEntryClicked {
        void onItemClicked(int position);

    }
}
