package com.example.a80desepacer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class StepCounter implements SensorEventListener {

    private final Context context;
    private int stepCount = 0;

    private int stepFromLastReboot = 0;
    private final StepCounterListener listener;

    public StepCounter(Context context, StepCounterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void startCounting() {
        // Register the sensor listener
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Handle the case where the device does not have a step counter sensor
            // You can fallback to other sensors or display a message to the user
        }
    }

    public void stopCounting() {
        // Unregister the sensor listener
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Update step count when a step is detected
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if(stepFromLastReboot == 0){
                stepFromLastReboot = (int) event.values[0];
            }
            stepCount = ((int) event.values[0]) - stepFromLastReboot;
            // Notify the listener with the new step count
            if (listener != null) {
                listener.onStepCountChanged(stepCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    // Define an interface for the listener
    public interface StepCounterListener {
        void onStepCountChanged(int stepCount);
    }
}

