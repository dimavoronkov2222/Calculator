package com.dmytro.szmolgp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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
                break;
            case "=":
                calculateResult();
                return;
            default:
                currentExpression.append(text);
        }
        input.setText(currentExpression.toString());
    }
    private void calculateResult() {
        String expression = currentExpression.toString()
                .replace("×", "*")
                .replace("÷", "/")
                .replace("√", "Math.sqrt")
                .replace("^", "Math.pow");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        try {
            Object result = engine.eval(expression);
            input.setText(String.valueOf(result));
            currentExpression.setLength(0);
            currentExpression.append(result);
        } catch (ScriptException e) {
            input.setText("Error");
        }
    }
}