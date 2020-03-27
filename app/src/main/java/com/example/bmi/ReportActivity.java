package com.example.bmi;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Bundle bundle = getIntent().getExtras();
        double height = (bundle.getInt("feet") * 12 + bundle.getInt("inches"))*0.0254;
        double weight = Double.parseDouble(bundle.getString("weight"))*0.45359;
        double bmi = weight / (height * height);
        DecimalFormat nf = new DecimalFormat("0.00");
        TextView result = (TextView) findViewById(R.id.resultTV);
        result.setText(getString(R.string.bmi_result) + " " + nf.format(bmi));
// Give health advice
        ImageView image = (ImageView) findViewById(R.id.resultImage);
        TextView advice = (TextView) findViewById(R.id.adviceTV);
        if (bmi > 25) {
            image.setImageResource(R.drawable.bot_fat);
            image.animate().scaleX(1.5f).scaleY(1.5f).setDuration(4000);
            advice.setText(R.string.advice_heavy);
        } else if (bmi < 20) {
            image.setImageResource(R.drawable.bot_thin);
            image.animate().scaleX(0.5f).scaleY(0.5f).setDuration(4000);
            advice.setText(R.string.advice_light);
        } else {
            image.setImageResource(R.drawable.bot_fit);
            advice.setText(R.string.advice_average);
        }
    }
}
