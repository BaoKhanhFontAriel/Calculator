package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    double result;
    boolean isResultTrue;
    String inputText = "", numberText = "";
    List<String> calculationList = new ArrayList<>();
    TextView inputTextView;
    TextView answerTextView;



    View.OnClickListener numberButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isResultTrue) {
                isResultTrue = false;
                inputText = "";
                calculationList.clear();
            }
            Log.d(TAG, "current text: " + inputText);
            inputText = inputText + ((Button)v).getText();
            inputTextView.setText(inputText);
            numberText = numberText + ((Button)v).getText();
        }
    };

    char[] actionList = {'+', '-', 'x', '/'};


//    void parseCalculation(String inputText) {
//        //thuc hien toan bo cac phep nhan
//        String result1;
//
//        parseCalculation(result1);
//
//        double asdj = calculate(String input)
//    }


    View.OnClickListener onActionClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {




            char c = inputText.charAt(inputText.length()-1);
             if (c > '9' || c < '0') {
                 Log.d(TAG, "cannot add input: not a number");
                 return;
             }

            //clone old behavior
            inputText += ((TextView)v).getText();
            inputTextView.setText(inputText);
            numberText += ((TextView)v).getText();
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

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonDiv = findViewById(R.id.buttonDiv);
        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonEqual = findViewById(R.id.buttonEqual);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonHistory = findViewById(R.id.historyButton);
        Button buttonDelete = findViewById(R.id.deleteButton);

        inputTextView = findViewById(R.id.textView1);
        answerTextView = findViewById(R.id.textView2);

        if (!History.getInstance().getHistoryEntryList().isEmpty()){
            inputText += getIntent().getStringExtra("historyAnswer");
            inputTextView.setText(inputText);
            numberText += inputText;

        }

        buttonDot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputText = inputText + ".";
                inputTextView.setText(inputText);
                numberText = numberText + ".";
            }
        });
        buttonDiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputText = inputText + "/";
                calculationList.add(numberText);
                calculationList.add("/");
                inputTextView.setText(inputText);
                numberText = "";
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputText = inputText + "+";
                calculationList.add(numberText);
                calculationList.add("+");
                inputTextView.setText(inputText);
                numberText = "";
            }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputText = inputText + "-";
                calculationList.add(numberText);
                calculationList.add("-");
                inputTextView.setText(inputText);
                numberText = "";
            }
        });
        buttonMultiply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputText = inputText + "x";
                calculationList.add(numberText);
                calculationList.add("x");
                inputTextView.setText(inputText);
                numberText = "";

            }
        });
        buttonEqual.setOnClickListener(v -> {
            if (calculationList.size() == 2 & numberText == "") {
                answerTextView.setText("Error");
            } else {
                calculationList.add(numberText);

                if (calculationList.size() == 3) {

                    if (calculationList.contains("/")) {
                        result = Double.parseDouble(calculationList.get(0)) / Double.parseDouble(calculationList.get(2));
                    }
                    if (calculationList.contains("x")) {
                        result = Double.parseDouble(calculationList.get(0)) * Double.parseDouble(calculationList.get(2));
                    }
                    if (calculationList.contains("+")) {
                        result = Double.parseDouble(calculationList.get(0)) + Double.parseDouble(calculationList.get(2));
                    }
                    if (calculationList.contains("-")) {
                        result = Double.parseDouble(calculationList.get(0)) - Double.parseDouble(calculationList.get(2));
                    }

                    String textConvert = "";
                    for (String s : calculationList) {
                        textConvert += s;
                    }

                    History.getInstance().addEntry(new HistoryEntry(textConvert, result));

                    answerTextView.setText(String.valueOf(result) );

                    isResultTrue = true;
                    numberText = "";
                }
                if (calculationList.size() == 1) {
                    answerTextView.setText("Error!");
                }
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResultTrue) {
                    if (!inputText.isEmpty()) {
                        inputText = inputText.substring(0, inputText.length() - 1);
                        inputTextView.setText(inputText);
                    } else {
                        inputTextView.setText("");
                    }
                } else {
                    inputTextView.setText("");
                    answerTextView.setText("");
                    isResultTrue = false;
                    inputText = "";
                    calculationList.clear();
                    numberText = "";
                }

                calculationList.clear();
                numberText = "";

                if (!inputText.isEmpty()) {
                    for (int i = 0; i < inputText.length(); i++){
                        if (Character.isDigit(inputText.charAt(i))){
                            numberText += inputText.charAt(i);
                        }
                        else {
                            calculationList.add(numberText);
                            calculationList.add(String.valueOf(inputText.charAt(i)));
                            numberText = "";
                        }
                    }
                }
            }
        });

    }

}