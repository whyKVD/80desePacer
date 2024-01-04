package com.example.a80desepacer;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements StepCounter.StepCounterListener {

    private StepCounter stepCounter;
    TextView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepView = findViewById(R.id.stepCountTextView);

        // Instantiate StepCounter
        stepCounter = new StepCounter(this, this);

        // Start step counting
        stepCounter.startCounting();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop step counting when the activity is destroyed
        stepCounter.stopCounting();
    }

    @Override
    public void onStepCountChanged(int stepCount) {
        // Update UI or perform any actions when the step count changes
        Log.d("StepCounter", "Step count: " + stepCount);
        String text = "Step count: " + stepCount;
        stepView.setText(text);
    }
}