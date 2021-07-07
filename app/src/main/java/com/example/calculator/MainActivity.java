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
    public static ArrayList<HistoryEntry> historyEntryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
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


        button0.setOnClickListener(v -> {
            if (isResultTrue == true) {
                isResultTrue = false;
                text = "";
                calculationList.clear();
            }
            text = text + "0";
            textView1.setText(text);
            numberText = numberText + "0";
        });

        button1.setOnClickListener(v -> {
            if (isResultTrue == true) {
                isResultTrue = false;

                text = "";
                calculationList.clear();
            }
            text = text + "1";
            textView1.setText(text);
            numberText = numberText + "1";

        });
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isResultTrue == true) {
                    isResultTrue = false;

                    text = "";
                    calculationList.clear();
                }
                text = text + "2";
                textView1.setText(text);
                numberText = numberText + "2";

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isResultTrue) {
                    isResultTrue = false;

                    text = "";
                    calculationList.clear();
                }
                text = text + "3";
                textView1.setText(text);
                numberText = numberText + "3";

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isResultTrue) {
                    isResultTrue = false;

                    text = "";
                    calculationList.clear();
                }
                text = text + "4";
                textView1.setText(text);
                numberText = numberText + "4";

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isResultTrue == true) {
                    isResultTrue = false;

                    text = "";
                    calculationList.clear();
                }
                text = text + "5";
                textView1.setText(text);
                numberText = numberText + "5";

            }
        });
        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isResultTrue == true) {
                    isResultTrue = false;

                    text = "";
                    calculationList.clear();
                }
                text = text + "6";
                textView1.setText(text);
                numberText = numberText + "6";

            }
        });
        button7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isResultTrue == true) {
                    isResultTrue = false;

                    text = "";
                    calculationList.clear();
                }
                text = text + "7";
                textView1.setText(text);
                numberText = numberText + "7";

            }
        });
        button8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isResultTrue == true) {
                    isResultTrue = false;

                    text = "";
                    calculationList.clear();
                }
                text = text + "8";
                textView1.setText(text);
                numberText = numberText + "8";

            }
        });
        button9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isResultTrue == true) {
                    isResultTrue = false;

                    text = "";
                    calculationList.clear();
                }
                text = text + "9";
                textView1.setText(text);
                numberText = numberText + "9";

            }
        });
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

                    historyEntryList.add(new HistoryEntry(textConvert, result));

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