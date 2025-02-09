package com.dmytro.szmolgp;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.Expression;
public class MainActivity extends AppCompatActivity {
    private EditText input;
    private StringBuilder currentExpression = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        setButtonListeners();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setButtonListeners() {
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv, R.id.btnDot,
                R.id.btnSin, R.id.btnCos, R.id.btnTan, R.id.btnLn, R.id.btnLog, R.id.btnPow, R.id.btnSqrt,
                R.id.btnParenL, R.id.btnParenR, R.id.btnClear, R.id.btnEqual
        };
        for (int id : buttonIds) {
            Button button = findViewById(id);
            if (button != null) {
                button.setOnClickListener(this::onButtonClick);
            }
        }
    }
    private void onButtonClick(View view) {
        if (!(view instanceof Button)) return;
        Button button = (Button) view;
        String text = button.getText().toString();
        switch (text) {
            case "C":
                currentExpression.setLength(0);
                input.setText("");
                return;
            case "=":
                calculateResult();
                return;
            case "^":
                currentExpression.append("pow(");
                break;
            case "√":
                currentExpression.append("sqrt(");
                break;
            case "sin":
                currentExpression.append("sin(");
                break;
            case "cos":
                currentExpression.append("cos(");
                break;
            case "tan":
                currentExpression.append("tan(");
                break;
            case "ln":
                currentExpression.append("ln(");
                break;
            case "log":
                currentExpression.append("log10(");
                break;
            case ")":
                currentExpression.append(")");
                break;
            case ".":
                currentExpression.append(".");
                break;
            default:
                currentExpression.append(text);
        }
        input.setText(currentExpression.toString());
    }
    private void calculateResult() {
        String expression = currentExpression.toString()
                .replace("^", "pow");
        Expression mathExpression = new Expression(expression);
        double result = mathExpression.calculate();
        if (Double.isNaN(result)) {
            input.setText("Error");
        } else {
            input.setText(String.valueOf(result));
            currentExpression.setLength(0);
            currentExpression.append(result);
        }
    }
}