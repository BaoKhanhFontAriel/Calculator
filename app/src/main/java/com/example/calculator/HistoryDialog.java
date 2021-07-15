package com.example.calculator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import static android.service.controls.ControlsProviderService.TAG;

public class HistoryDialog extends DialogFragment {
    private Button clearButton;
    private Context context;
    private HistoryDialog historyDialog;
    private RecyclerView recyclerView;



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        historyDialog = new HistoryDialog(context, answerCallback, clearCallback);
        View historyView = inflater.inflate(R.layout.list_view_history, container);
        recyclerView = historyView.findViewById(R.id.listViewHistory);
        clearButton = historyView.findViewById(R.id.clearButton);

        HistoryAdapter historyAdapter = new HistoryAdapter(History.getInstance().getHistoryEntryList(), callback);

        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History.getInstance().clear();
                getDialog().dismiss();
                clearCallback.OnItemClicked(true);
                historyAdapter.notifyDataSetChanged();
            }
        });

        return historyView;
    }

    HistoryAdapter.IEntryClicked callback = new HistoryAdapter.IEntryClicked() {
        @Override
        public void onItemClicked(int position) {
            answerCallback.OnItemClicked(position);
            Log.d(TAG, "onItemClicked: " + answerCallback);
            getDialog().dismiss();

        }
    };

    public HistoryDialog(Context context, IHistoryEntryClicked answerCallback, IClearClicked clearCallback) {
        this.context = context;
        this.answerCallback = answerCallback;
        this.clearCallback = clearCallback;

    }

    public interface IHistoryEntryClicked {
        void OnItemClicked(int position);
    }

    private IHistoryEntryClicked answerCallback;

    public interface IClearClicked {
        void OnItemClicked(boolean isPressed);
    }

    private IClearClicked clearCallback;
}

