package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    double result;
    boolean isResultTrue;
    String text = "", numberText = "";
    List<String> calculationList = new ArrayList<>();

    View.OnClickListener numberButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isResultTrue) {
                isResultTrue = false;
                text = "";
                calculationList.clear();
            }

            text = text + ((Button)v).getText();
            ((Button) v).setText(text);
            numberText = numberText + ((Button)v).getText();
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

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);



        buttonDot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                text = text + ".";
                textView1.setText(text);
                numberText = numberText + ".";
            }
        });
        buttonDiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                text = text + "/";
                calculationList.add(numberText);
                calculationList.add("/");
                textView1.setText(text);
                numberText = "";
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                text = text + "+";
                calculationList.add(numberText);
                calculationList.add("+");
                textView1.setText(text);
                numberText = "";
            }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                text = text + "-";
                calculationList.add(numberText);
                calculationList.add("-");
                textView1.setText(text);
                numberText = "";
            }
        });
        buttonMultiply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                text = text + "x";
                calculationList.add(numberText);
                calculationList.add("x");
                textView1.setText(text);
                numberText = "";

            }
        });
        buttonEqual.setOnClickListener(v -> {
            if (calculationList.size() == 2 & numberText == "") {
                textView2.setText("Error");
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

                    textView2.setText(String.valueOf(result) );

                    isResultTrue = true;
                    numberText = "";
                }
                if (calculationList.size() == 1) {
                    textView2.setText("Error!");
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
                    if (!text.isEmpty()) {
                        text = text.substring(0, text.length() - 1);
                        textView1.setText(text);
                    } else {
                        textView1.setText("");
                    }
                } else {
                    textView1.setText("");
                    textView2.setText("");
                    isResultTrue = false;
                    text = "";
                    calculationList.clear();
                    numberText = "";
                }

                calculationList.clear();
                numberText = "";

                if (!text.isEmpty()) {
                    for (int i = 0; i < text.length(); i++){
                        if (Character.isDigit(text.charAt(i))){
                            numberText += text.charAt(i);
                        }
                        else {
                            calculationList.add(numberText);
                            calculationList.add(String.valueOf(text.charAt(i)));
                            numberText = "";
                        }
                    }
                }
            }
        });

    }

}