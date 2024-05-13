package edu.tlu.slide4;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etFirstNumber, etSecondNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etFirstNumber = findViewById(R.id.etFirstNumber);
        etSecondNumber = findViewById(R.id.etSecondNumber);

        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSubtract).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int num1 = Integer.parseInt(etFirstNumber.getText().toString());
        int num2 = Integer.parseInt(etSecondNumber.getText().toString());
        int result = 0;
        if (v.getId() == R.id.btnAdd){
            result = num1 + num2;
        } else if (v.getId() == R.id.btnSubtract) {
            result = num1 - num2;
        }

//        Toast.makeText(this, "Kết quả: " + result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("result", result);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
}