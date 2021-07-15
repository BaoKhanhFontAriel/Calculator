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
    boolean isHistoryDisabled;


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
        setSavedHistory();
        ////////////////////
        buttonDot.setOnClickListener(v -> {
            setDotButton();
        });

        buttonEqual.setOnClickListener(v -> {
            setEqualButton();
            buttonHistory.setEnabled(true);
        });


        buttonHistory.setEnabled(false);

        buttonDelete.setOnClickListener(v -> {
            setDeleteButton();
        });

        buttonHistory.setOnClickListener(v -> setHistoryButton());
    }

    public void setSavedHistory() {
        int numberOfEntry = Integer.parseInt(sharedPreferences.getString("NUMBER_OF_INDEX", "0"));

        for (int i = 1; i <= numberOfEntry; i++) {
            String savedInput = sharedPreferences.getString("INPUT" + String.valueOf(i), "");
            double savedAnswer = Double.parseDouble(sharedPreferences.getString("ANSWER" + String.valueOf(i), "0"));
            History.getInstance().addEntry(new HistoryEntry(savedInput, savedAnswer));
        }
    }

    public void setDotButton() {
        Log.d(TAG, "button Dot is clicked, isDotAlreadyInNumber is: " + isDotAlreadyInNumber);
        if (!isDotAlreadyInNumber) {
            inputText = inputText + ".";
            inputTextView.setText(inputText);
            isDotAlreadyInNumber = true;
        }

        return;
    }

    public void setEqualButton() {
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
        isHistoryDisabled = false;

        int entryNumber = History.getInstance().getHistoryEntryList().size();
        editor.putString("NUMBER_OF_INDEX", String.valueOf(entryNumber));

        for (int i = 1; i <= entryNumber; i++) {
            String inputKey = History.getInstance().getHistoryEntryList().get(i - 1).getHistoryInput();
            String answerKey = String.valueOf(History.getInstance().getHistoryEntryList().get(i - 1).getHistoryAnswer());

            editor.putString("INPUT" + String.valueOf(i), inputKey);
            editor.putString("ANSWER" + String.valueOf(i), answerKey);
            editor.commit();
        }
    }

    public void setHistoryButton() {
        HistoryDialog historyDialog = new HistoryDialog(getApplicationContext(), answerCallBack, clearCallback);
        historyDialog.show(fragmentManager, "history");
    }

    public void disableHistoryButton(View v) {
        ((Button) v).setEnabled(false);
    }

    public void setDeleteButton() {
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
    }

    // return the answer to inputText when click the entry in history dialog
    HistoryDialog.IHistoryEntryClicked answerCallBack = new HistoryDialog.IHistoryEntryClicked() {
        @Override
        public void OnItemClicked(int position) {
            char c = inputText.charAt(inputText.length() - 1);
            if (c == 'x' || c == '/' || c == '+' || c == '-'){
                inputText += String
                        .valueOf(History.getInstance().getHistoryEntryList().get(position).getHistoryAnswer());
            } else {
                inputText = String
                        .valueOf(History.getInstance().getHistoryEntryList().get(position).getHistoryAnswer());
            }

            inputTextView.setText(inputText);
            isExpressionCalculated = false;

        }
    };

    // clear saved history if clear button is pressed
    HistoryDialog.IClearClicked clearCallback = new HistoryDialog.IClearClicked() {
        @Override
        public void OnItemClicked(boolean isPressed) {
            if (isPressed) {
                editor.clear();
                editor.commit();
                isHistoryDisabled = true;
            }
        }
    };
}