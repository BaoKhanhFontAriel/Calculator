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
    Button buttonHistory;
    boolean isExpressionHasOperator = false;
    ArrayList<String> calList = new ArrayList<>();
    String numberText  = "";


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

            if (!inputText.isEmpty()) {
                char c = inputText.charAt(inputText.length() - 1);
                if (c > '9' || c < '0') {
                    Log.d(TAG, "cannot add input: not a number");
                    return;
                }
            }


            // first number ended after press operator, new number does not have dot
            isDotAlreadyInNumber = false;

            // stop add more operator if the expression has 2 numbers
            if (isExpressionHasOperator){
                return;
            }

            // Operator button is pressed after equal button
            // Start new expression with result of old expression
            if (isExpressionCalculated) {
                inputText = String.valueOf(result);
                inputTextView.setText(inputText);
                isExpressionCalculated = false;
                isExpressionHasOperator = true;
            }

            // Ensure x, / is not entered at beginning of expression
            CharSequence c = ((Button) v).getText();
            if (inputText.isEmpty()) {
                if ("+".contentEquals(c) || "-".contentEquals(c)) {
                    inputText += ((TextView) v).getText();
                    inputTextView.setText(inputText);
                }
                return;
            } else {
                inputText += ((TextView) v).getText();
                inputTextView.setText(inputText);
                isExpressionHasOperator = true;
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
        buttonHistory = findViewById(R.id.historyButton);
        Button buttonDelete = findViewById(R.id.deleteButton);

        inputTextView = findViewById(R.id.textView1);
        answerTextView = findViewById(R.id.textView2);


        sharedPreferences = getSharedPreferences("mySharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getHistoryButtonState();
        getSavedHistory();

        buttonDot.setOnClickListener(v -> {
            setDotButton();
        });

        buttonEqual.setOnClickListener(v -> {
            setEqualButton();
            buttonHistory.setEnabled(true);
        });

        buttonDelete.setOnClickListener(v -> {
            setDeleteButton();
        });

        buttonHistory.setOnClickListener(v -> setHistoryButton());
    }

    public double getResult(String s){
        String numberText ="";
        for (int i = 0; i < s.length(); i++){
            if (i == 0 && (s.charAt(i) == '+' || s.charAt(i) == '-'  || s.charAt(i) == '/'  || s.charAt(i) == 'x' )){
                numberText += s.charAt(i);
            }
            else if ((s.charAt(i) <= '9' && s.charAt(i) >= '0') || s.charAt(i) == '.'){
                numberText += s.charAt(i);
            }
            else {
                calList.add(numberText);
                calList.add(String.valueOf(s.charAt(i)));
                numberText = "";
            }

        }
        calList.add(numberText);
        Log.d(TAG, "getResult: callist" + calList);
        double answer = calculate(calList.get(0), calList.get(2), calList.get(1));
        calList.clear();
        return answer;
    }

    public double calculate(String s1, String s2, String operate){
        double i1 = Double.parseDouble(s1);
        double i2 = Double.parseDouble(s2);
        switch (operate){
            case "+":
                return i1 + i2;
            case "-":
                return i1 - i2;
            case "/":
                return i1 / i2;
            case "x":
                return i1 * i2;
        }

        Log.d(TAG, "calculate: is cleared");
        return 0;
    }

    public void getHistoryButtonState() {
        buttonHistory.setEnabled(sharedPreferences.getBoolean("ENABLE_STATE", true));
        Log.d(TAG, "getHistoryButtonState: " + sharedPreferences.getBoolean("ENABLE_STATE", true));
    }

    public void setHistoryButtonState(boolean value) {
        editor.putBoolean("ENABLE_STATE", value);
        editor.commit();
    }

    public void getSavedHistory() {
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


        result = getResult(inputText);
        Log.d(TAG, "setEqualButton: result" + result);
        answerTextView.setText(String.valueOf(result));

        History.getInstance().addEntry(new HistoryEntry(inputText, result));
        isExpressionCalculated = true;
        isExpressionHasOperator = false;

        setSavedHistory();

        setHistoryButtonState(true);

    }

    public void setSavedHistory() {
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


    public void setDeleteButton() {
        if (!isExpressionCalculated) {
            if (!inputText.isEmpty()) {
                // delete the dot
                char lastChar = inputText.charAt(inputText.length() - 1);
                if (lastChar == '.') {
                    isDotAlreadyInNumber = false;
                }
                // delete the operator
                if (lastChar == '+' || lastChar == '-' || lastChar == '/' || lastChar == 'x'){
                    isExpressionHasOperator = false;
                }
                inputText = inputText.substring(0, inputText.length() - 1);
                inputTextView.setText(inputText);
            }
        } else {
            inputTextView.setText("");
            answerTextView.setText("");
            isExpressionCalculated = false;
            isExpressionHasOperator = false;
            inputText = "";
        }
    }

    // return the answer to inputText when click the entry in history dialog
    HistoryDialog.IHistoryEntryClicked answerCallBack = new HistoryDialog.IHistoryEntryClicked() {
        @Override
        public void OnItemClicked(int position) {

            if (inputText.isEmpty()){
                inputText = String
                        .valueOf(History.getInstance().getHistoryEntryList().get(position).getHistoryAnswer());
                isExpressionHasOperator = false;
            }
            else {
                // answer in history is clicked
                // but the input text ended with an operator
                char c = inputText.charAt(inputText.length() - 1);
                if (c == 'x' || c == '/' || c == '+' || c == '-') {

                    // the answer is negative
                    // delete the operator
                    // now operator is minus
                    if (History.getInstance().getHistoryEntryList().get(position).getHistoryAnswer() < 0) {
                        inputText = inputText.substring(0, inputText.length() - 1);
                        inputText += String
                                .valueOf(History.getInstance().getHistoryEntryList().get(position).getHistoryAnswer());
                    } else inputText += String
                            .valueOf(History.getInstance().getHistoryEntryList().get(position).getHistoryAnswer());

                    isExpressionHasOperator = true;

                }
                // but the input text did not end with an operator
                // start new expression with the answer
                else {
                    inputText = String
                            .valueOf(History.getInstance().getHistoryEntryList().get(position).getHistoryAnswer());
                    isExpressionHasOperator = false;
                }
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
                buttonHistory.setEnabled(false);
                setHistoryButtonState(false);
            }
        }
    };
}