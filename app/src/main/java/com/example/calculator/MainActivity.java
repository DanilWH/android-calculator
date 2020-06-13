package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button zero, one, two, three, four, five, six, seven, eight, nine;
    private Button add, subtract, multiple, divide;
    private Button equalSign, dot, reset, deleteDigit;
    private TextView value1_info, value2_info, operation_info, result_info;

    private final char ADDITION = '+';
    private final char SUBTRACTION = '-';
    private final char MULTIPLICATION = '*';
    private final char DIVISION = '/';
    private char operation;
    private double value1, value2, result;
    private boolean value1_inserted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        processButtonsListeners();
    }

    private void setupViews() {
        /*** finds forms by their ids. ***/

        // digit buttons.
        this.zero = (Button) findViewById(R.id.btn_digit0);
        this.one = (Button) findViewById(R.id.btn_digit1);
        this.two = (Button) findViewById(R.id.btn_digit2);
        this.three = (Button) findViewById(R.id.btn_digit3);
        this.four = (Button) findViewById(R.id.btn_digit4);
        this.five = (Button) findViewById(R.id.btn_digit5);
        this.six = (Button) findViewById(R.id.btn_digit6);
        this.seven = (Button) findViewById(R.id.btn_digit7);
        this.eight = (Button) findViewById(R.id.btn_digit8);
        this.nine = (Button) findViewById(R.id.btn_digit9);

        // operations buttons.
        this.add = (Button) findViewById(R.id.btn_add);
        this.subtract = (Button) findViewById(R.id.btn_subtract);
        this.multiple = (Button) findViewById(R.id.btn_multiple);
        this.divide = (Button) findViewById(R.id.btn_divide);

        this.equalSign = (Button) findViewById(R.id.btn_equalSign);
        this.dot = (Button) findViewById(R.id.btn_dot);
        this.reset = (Button) findViewById(R.id.btn_reset);
        this.deleteDigit = (Button) findViewById(R.id.btn_deleteDigit);

        // TextViews.
        this.value1_info = (TextView) findViewById(R.id.value1_view);
        this.value2_info = (TextView) findViewById(R.id.value2_view);
        this.operation_info = (TextView) findViewById(R.id.operation_view);
        this.result_info = (TextView) findViewById(R.id.result_view);
    }

    private void processButtonsListeners() {
        /*** processes buttons listeners. ***/

        // process digit buttons.
        this.processDigit(zero, "0");
        this.processDigit(one, "1");
        this.processDigit(two, "2");
        this.processDigit(three, "3");
        this.processDigit(four, "4");
        this.processDigit(five, "5");
        this.processDigit(six, "6");
        this.processDigit(seven, "7");
        this.processDigit(eight, "8");
        this.processDigit(nine, "9");
        this.processDigit(dot, ".");

        // process operations buttons.
        this.processOperation(add, ADDITION);
        this.processOperation(subtract, SUBTRACTION);
        this.processOperation(multiple, MULTIPLICATION);
        this.processOperation(divide, DIVISION);

        this.equalSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = calculate();
                // show the result in the result bar.
                result_info.setText("Result: " + (value1 = result));
            }
        });

        this.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reset the variables.
                value1 = 0.0d;
                value2 = 0.0d;
                result = 0.0d;
                value1_inserted = false;
                operation = '\u0000';

                // clear the fields.
                value1_info.setText(null);
                value2_info.setText(null);
                operation_info.setText(null);
                result_info.setText("Result: ");
            }
        });

        this.deleteDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete digits of the first value if it's not inserted yet.
                if (!value1_inserted) {
                    // get the value as a string
                    String text = value1_info.getText().toString();
                    // get the length of the string.
                    int text_len = text.length();
                    // set the string to the bar of the first value without the last character.
                    value1_info.setText(text.substring(0, text_len - (text_len == 0 ? 0 : 1)));
                }
                // otherwise, delete digits of the second value.
                else {
                    String text = value2_info.getText().toString();
                    int text_len = text.length();
                    value2_info.setText(text.substring(0, text_len - (text_len == 0 ? 0 : 1)));
                }
            }
        });
    }

    private void processDigit(Button button, final String digitChar) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!value1_inserted)
                    value1_info.setText(value1_info.getText().toString() + digitChar);
                else
                    value2_info.setText(value2_info.getText().toString() + digitChar);
            }
        });
    }

    private void processOperation(Button button, final char sign) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // an operation button is pressed. That means the user wants to insert the second value
                // so we mark that inserting the first value is completed.
                value1_inserted = true;
                // save the user operation choice.
                operation = sign;
                operation_info.setText(Character.toString(sign));
            }
        });
    }

    private double calculate() {
        double result = Double.NaN;

        if (!checkOutInputs()) return result;

        this.value1 = Double.parseDouble(this.value1_info.getText().toString());
        this.value2 = Double.parseDouble(this.value2_info.getText().toString());

        // calculate the two numbers depending on the operation the user has chose.
        switch (this.operation) {
            case ADDITION:
                result = this.value1 + this.value2;
                break;
            case SUBTRACTION:
                result = this.value1 - this.value2;
                break;
            case MULTIPLICATION:
                result = this.value1 * this.value2;
                break;
            case DIVISION:
                result = this.value1 / this.value2;
                break;
        }

        return result;
    }

    private boolean checkOutInputs() {
        /*** checks out if the inserted values are correct. ***/

        // check if the first field is inserted correctly.
        try {
            Double.parseDouble(this.value1_info.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "The first field is incorrect",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // check if the second field is inserted correctly.
        try {
            Double.parseDouble(this.value2_info.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "The second field is incorrect",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // success.
        return true;
    }
}