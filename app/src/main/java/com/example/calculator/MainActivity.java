package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    double result;
    boolean isExpressionCalculated, isDotAlreadyInNumber;
    String inputText = "";
    TextView inputTextView;
    TextView answerTextView;
    FragmentManager fragmentManager = getSupportFragmentManager();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    View.OnClickListener numberButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // if old expression is calculated, begin new expression
            if (isExpressionCalculated) {
                isExpressionCalculated = false;
                inputText = "";
            }

            inputText = inputText + ((Button) v).getText();
            inputTextView.setText(inputText);
        }
    };


    View.OnClickListener onActionClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.d(TAG, "onClick: " + ((Button) v).getText());

            if (!inputText.isEmpty()) {
                char c = inputText.charAt(inputText.length() - 1);
                if (c > '9' || c < '0') {
                    Log.d(TAG, "cannot add input: not a number");
                    return;
                }
            }

            // begin new number
            isDotAlreadyInNumber = false;

            // Start new expression with result of old expression
            if (isExpressionCalculated) {
                inputText = String.valueOf(result);
                inputTextView.setText(inputText);
                isExpressionCalculated = false;
            }

            // Ensure x, / is not entered at beginning
            CharSequence c = ((Button) v).getText();
            if (inputText.isEmpty()) {
                if ("+".contentEquals(c) || "-".contentEquals(c)) {
                    Log.d(TAG, "onClick: worked");
                    inputText += ((TextView) v).getText();
                    inputTextView.setText(inputText);
                }
                return;
            } else {
                inputText += ((TextView) v).getText();
                inputTextView.setText(inputText);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button0).setOnClickListener(numberButtonListener);
        findViewById(R.id.button1).setOnClickListener(numberButtonListener);
        findViewById(R.id.button2).setOnClickListener(numberButtonListener);
        findViewById(R.id.button3).setOnClickListener(numberButtonListener);
        findViewById(R.id.button4).setOnClickListener(numberButtonListener);
        findViewById(R.id.button5).setOnClickListener(numberButtonListener);
        findViewById(R.id.button6).setOnClickListener(numberButtonListener);
        findViewById(R.id.button7).setOnClickListener(numberButtonListener);
        findViewById(R.id.button8).setOnClickListener(numberButtonListener);
        findViewById(R.id.button9).setOnClickListener(numberButtonListener);

        findViewById(R.id.buttonAdd).setOnClickListener(onActionClicked);
        findViewById(R.id.buttonDiv).setOnClickListener(onActionClicked);
        findViewById(R.id.buttonMultiply).setOnClickListener(onActionClicked);
        findViewById(R.id.buttonMinus).setOnClickListener(onActionClicked);

        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonEqual = findViewById(R.id.buttonEqual);
        Button buttonHistory = findViewById(R.id.historyButton);
        Button buttonDelete = findViewById(R.id.deleteButton);

        inputTextView = findViewById(R.id.textView1);
        answerTextView = findViewById(R.id.textView2);


        sharedPreferences = getSharedPreferences("mySharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // get the saved history
        int numberOfEntry = Integer.parseInt(sharedPreferences.getString("NUMBER_OF_INDEX", "0"));

        for (int i = 0; i <= numberOfEntry; i++) {
            String savedInput = sharedPreferences.getString("INPUT" + String.valueOf(i), "");
            double savedAnswer = Double.parseDouble(sharedPreferences.getString("ANSWER" + String.valueOf(i), "0"));
            History.getInstance().addEntry(new HistoryEntry(savedInput, savedAnswer));
        }
        ////////////////////
        buttonDot.setOnClickListener(v -> {
            Log.d(TAG, "button Dot is clicked, isDotAlreadyInNumber is: " + isDotAlreadyInNumber);
            if (!isDotAlreadyInNumber) {
                inputText = inputText + ".";
                inputTextView.setText(inputText);
                isDotAlreadyInNumber = true;
            }

            return;
        });

        buttonEqual.setOnClickListener(v -> {
            char c = inputText.charAt(inputText.length() - 1);
            if (c > '9' || c < '0') {
                Log.d(TAG, "cannot add input: not a number");
                return;
            }
            inputText = inputText.replace('x', '*');

            result = Calculate.evaluate(inputText);
            answerTextView.setText(String.valueOf(result));

            inputText = inputText.replace('*', 'x');
            History.getInstance().addEntry(new HistoryEntry(inputText, result));
            isExpressionCalculated = true;

            int entryIndex = History.getInstance().getHistoryEntryList().size() - 1;
            editor.putString("NUMBER_OF_INDEX", String.valueOf(entryIndex));

            for (int i = 0; i <= entryIndex; i++){
                String inputKey = History.getInstance().getHistoryEntryList().get(i).getHistoryInput();
                String answerKey = String.valueOf(History.getInstance().getHistoryEntryList().get(i).getHistoryAnswer());

                editor.putString("INPUT" + String.valueOf(i), inputKey);
                editor.putString("ANSWER" + String.valueOf(i), answerKey);
                editor.commit();
            }


        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HistoryDialog historyDialog = new HistoryDialog(getApplicationContext(), answerCallBack, clearCallback);

                historyDialog.show(fragmentManager, "history");
            }
        });

        buttonDelete.setOnClickListener(v -> {
            if (!isExpressionCalculated) {
                if (!inputText.isEmpty()) {
                    // delete the dot
                    if (inputText.charAt(inputText.length() - 1) == '.') {
                        isDotAlreadyInNumber = false;
                    }
                    inputText = inputText.substring(0, inputText.length() - 1);
                    inputTextView.setText(inputText);
                }
            } else {
                inputTextView.setText("");
                answerTextView.setText("");
                isExpressionCalculated = false;
                inputText = "";
            }
        });
    }

    // return the answer to inputText when click the entry in history dialog
    HistoryDialog.IHistoryEntryClicked answerCallBack = new HistoryDialog.IHistoryEntryClicked() {
        @Override
        public void OnItemClicked(int position) {
            if (!History.getInstance().getHistoryEntryList().isEmpty()) {

                inputText = String
                        .valueOf(History.getInstance().getHistoryEntryList().get(position).getHistoryAnswer());

                Log.d(TAG, "historydialog passvalue to main" + inputText);
                inputTextView.setText(inputText);
                isExpressionCalculated = false;
            }
        }
    };

    // clear saved history if clear button is pressed
    HistoryDialog.IClearClicked clearCallback = new HistoryDialog.IClearClicked() {
        @Override
        public void OnItemClicked(boolean isPressed) {
            if (isPressed){
                editor.clear();
                editor.commit();
            }
        }
    };
}