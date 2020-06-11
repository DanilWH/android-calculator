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
    private Button equalSign, dot;
    private TextView value1_info, value2_info, operation_info, result_info;
    private char operation;
    private double value1, value2, result;
    private boolean value1_inserted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* find forms by their ids. */
        // buttons
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

        this.add = (Button) findViewById(R.id.btn_add);
        this.subtract = (Button) findViewById(R.id.btn_subtract);
        this.multiple = (Button) findViewById(R.id.btn_multiple);
        this.divide = (Button) findViewById(R.id.btn_divide);

        this.equalSign = (Button) findViewById(R.id.btn_equalSign);
        this.dot = (Button) findViewById(R.id.btn_dot);

        // TextViews.
        this.value1_info = (TextView) findViewById(R.id.value1_view);
        this.value2_info = (TextView) findViewById(R.id.value2_view);
        this.operation_info = (TextView) findViewById(R.id.operation_view);
        this.result_info = (TextView) findViewById(R.id.result_view);

        // process buttons.
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

        this.processOperation(add, '+');
        this.processOperation(subtract, '-');
        this.processOperation(multiple, '*');
        this.processOperation(divide, '/');

        this.equalSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
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
                // check if the second field is inserted correctly.
                try {
                    // save the first number that the user has inserted.
                    value1 = Double.parseDouble(value1_info.getText().toString());
                }
                catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "The first field is incorrect",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // an operation button is pressed that means the user wants to insert the second value
                // so we mark that inserting the first value is completed.
                value1_inserted = true;
                // save the user operation choice.
                operation = sign;
                operation_info.setText(Character.toString(sign));
            }
        });
    }

    private void calculate() {
        // check if the second field is inserted correctly.
        try {
            // save the first number that the user has inserted.
            value1 = Double.parseDouble(value1_info.getText().toString());
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "The first field is incorrect",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // check if the second field is inserted correctly.
        try {
            // save the second number that the user has inserted.
            this.value2 = Double.parseDouble(this.value2_info.getText().toString());
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"The second field is incorrect",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // calculate the two numbers depending on the operation the user has chose.
        switch (this.operation) {
            case '+':
                this.result =  this.value1 + this.value2;
            case '-':
                this.result = this.value1 - this.value2;
            case '*':
                this.result = this.value1 * this.value2;
            case '/':
                this.result = this.value1 / this.value2;
        }

        // show the result in the result bar.
        this.result_info.setText("Result: " + (this.value1 = this.result));
    }
}