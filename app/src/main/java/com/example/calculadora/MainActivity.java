package com.example.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtResult;
    private TextView txtHistory;
    private String lastDigit;
    private boolean isResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = (TextView) findViewById(R.id.txtResult);
        txtHistory = (TextView) findViewById(R.id.txtHistory);
    }

    public void buttonsClick(View view ) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        String resultText;

        switch (btnText) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if (this.isResult) {
                    this.txtResult.setText(btnText);
                    this.lastDigit = btnText;
                    this.isResult = false;
                } else {
                    this.txtResult.append(btnText);
                    this.lastDigit = btnText;
                }
                break;

            case "X":
                resultText = this.txtResult.getText().toString();
                if(resultText.length() > 0) {
                    resultText = resultText.substring(0, resultText.length() - 1);

                    this.txtResult.setText(resultText);
                    this.lastDigit = resultText.substring(resultText.length() - 2);
                }
                break;

            case "C":
                this.isResult = false;
                this.txtResult.setText("");
                this.txtHistory.setText("");
                this.lastDigit = "";
                break;

            case ",":
                resultText = txtResult.getText().toString();
                if (!this.isResult && !resultText.contains(".")) {
                    this.txtResult.append(".");
                }
                break;

            case "*":
            case "-":
            case "/":
            case "+":
                if (!this.lastDigit.matches("^[0-9]")) {
                    this.lastDigit = btnText;
                    String historyText = this.txtHistory.getText().toString();
                    historyText = historyText.substring(0, historyText.length() - 1);
                    this.txtHistory.setText(historyText + btnText);
                    break;
                }

                this.txtHistory.append(this.txtResult.getText().toString() + btnText);
                this.lastDigit = btnText;
                this.isResult = true;
                break;

            case "=":
                this.txtHistory.append(this.txtResult.getText().toString());
                String history = txtHistory.getText().toString();

                Double result = this.calculateString(history);
                this.txtResult.setText(result.toString());

                txtHistory.setText("");
                this.isResult = true;
                break;
        }
    }

    private Double calculateString(String input) {
        String parsedDouble = "";
        String operator = "";
        Double acc = 0.0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                parsedDouble += c;
            }
            if (!(Character.isDigit(c) || (c == '.')) || i == input.length() - 1) {
                Double parsed = Double.parseDouble(parsedDouble);

                switch (operator) {
                    case "":
                        acc = parsed;
                        break;
                    case "+":
                        acc = acc + parsed;
                        break;
                    case "-":
                        acc = acc - parsed;
                        break;
                    case "/":
                        acc = acc / parsed;
                        break;
                    case "*" :
                        acc = acc * parsed;
                        break;
                }

                parsedDouble = "";
                operator = "" + c;
            }
        }

        return acc;
    }
}
