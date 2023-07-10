package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultTextView;
    private StringBuilder inputStringBuilder;
    private DecimalFormat decimalFormat;

    private double operand1 = 0;
    private double operand2 = 0;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.result);
        inputStringBuilder = new StringBuilder();
        decimalFormat = new DecimalFormat("#.##########");

        Button[] buttons = new Button[]{
                findViewById(R.id.zero), findViewById(R.id.one), findViewById(R.id.two),
                findViewById(R.id.three), findViewById(R.id.four), findViewById(R.id.five),
                findViewById(R.id.six), findViewById(R.id.seven), findViewById(R.id.eight),
                findViewById(R.id.nine), findViewById(R.id.decimal), findViewById(R.id.plus),
                findViewById(R.id.minus), findViewById(R.id.multiply), findViewById(R.id.divide),
                findViewById(R.id.mod), findViewById(R.id.sign), findViewById(R.id.equal),
                findViewById(R.id.clear)
        };

        for (Button button : buttons) {
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.zero ||
                id == R.id.one ||
                id == R.id.two ||
                id == R.id.three ||
                id == R.id.four ||
                id == R.id.five ||
                id == R.id.six ||
                id == R.id.seven ||
                id == R.id.eight ||
                id == R.id.nine ||
                id == R.id.decimal) {
            Button numberButton = (Button) v;
            inputStringBuilder.append(numberButton.getText().toString());
        } else if (id == R.id.plus ||
                id == R.id.minus ||
                id == R.id.multiply ||
                id == R.id.divide ||
                id == R.id.mod) {
            Button operatorButton = (Button) v;
            String operatorText = operatorButton.getText().toString();
            if (inputStringBuilder.length() > 0) {
                operand1 = Double.parseDouble(inputStringBuilder.toString());
                operator = operatorText;
                inputStringBuilder.setLength(0);
            }
        } else if (id == R.id.sign) {
            if (inputStringBuilder.length() > 0 && !inputStringBuilder.toString().equals("0")) {
                double value = Double.parseDouble(inputStringBuilder.toString());
                value *= -1;
                inputStringBuilder.setLength(0);
                inputStringBuilder.append(decimalFormat.format(value));
            }
        } else if (id == R.id.equal) {
            if (inputStringBuilder.length() > 0 && !operator.isEmpty()) {
                operand2 = Double.parseDouble(inputStringBuilder.toString());
                inputStringBuilder.setLength(0);
                double result = performOperation(operand1, operand2, operator);
                inputStringBuilder.append(decimalFormat.format(result));
                operator = "";
            }
        } else if (id == R.id.clear) {
            operand1 = 0;
            operand2 = 0;
            operator = "";
            inputStringBuilder.setLength(0);
        }

        resultTextView.setText(inputStringBuilder.toString());
    }

    private double performOperation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "x": // Use "×" symbol for multiplication
                return operand1 * operand2;
            case "÷": // Use "÷" symbol for division
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    // Division by zero error
                    return Double.POSITIVE_INFINITY;
                }
            case "%":
                return operand1 / 100; // Divide by 100 for percentage calculation
            default:
                return 0;
        }
    }

}
