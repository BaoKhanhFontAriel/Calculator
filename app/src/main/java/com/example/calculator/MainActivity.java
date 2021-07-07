package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    double result;
    String text = " ", numberText = "";
    List<String> calculationList = new ArrayList<>();
    List<String> newList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layoutctivity_main);



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

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);


        button0.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "0";
                textView1.setText(text);
                numberText = numberText + "0";
            }
        });

        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "1";
                textView1.setText(text);
                numberText = numberText + "1";

            }
        });
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "2";
                textView1.setText(text);
                numberText = numberText + "2";

            }
        });
        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "3";
                textView1.setText(text );
                numberText = numberText + "3";

            }
        });
        button4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "4";
                textView1.setText(text);
                numberText = numberText + "4";

            }
        });
        button5.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "5";
                textView1.setText(text);
                numberText = numberText + "5";

            }
        });
        button6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "6";
                textView1.setText(text);
                numberText = numberText + "6";

            }
        });
        button7.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "7";
                textView1.setText(text);
                numberText = numberText + "7";

            }
        });
        button8.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "8";
                textView1.setText(text);
                numberText = numberText + "8";

            }
        });
        button9.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "9";
                textView1.setText(text);
                numberText = numberText + "9";

            }
        });
        buttonDot.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + ".";
                textView1.setText(text);
                numberText = numberText + ".";

            }
        });
        buttonDiv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "/";
                calculationList.add(numberText);
                calculationList.add("div");
                textView1.setText(text);
                numberText = "";
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "+";
                calculationList.add(numberText);
                calculationList.add("add");
                textView1.setText(text);
                numberText = "";
            }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "-";
                calculationList.add(numberText);
                calculationList.add("minus");
                textView1.setText(text);
                numberText = "";
            }
        });
        buttonMultiply.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                text = text + "x";
                calculationList.add(numberText);
                calculationList.add("multiply");
                textView1.setText(text);
                numberText = "";

            }
        });
        buttonEqual.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calculationList.add(numberText);

                if (calculationList.contains("div")){
                    result = Double.parseDouble(calculationList.get(0)) / Double.parseDouble(calculationList.get(2));
                }
                if (calculationList.contains("multiply")){
                    result = Double.parseDouble(calculationList.get(0)) * Double.parseDouble(calculationList.get(2));
                }
                if (calculationList.contains("add")){
                    result = Double.parseDouble(calculationList.get(0)) + Double.parseDouble(calculationList.get(2));
                }
                if (calculationList.contains("minus")){
                    result = Double.parseDouble(calculationList.get(0)) - Double.parseDouble(calculationList.get(2));
                }


                textView2.setText(String.valueOf(result));
                numberText = "";

            }
        });
}
}