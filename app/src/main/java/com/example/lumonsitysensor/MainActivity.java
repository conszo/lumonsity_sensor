package com.example.lumonsitysensor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.hardware.Sensor.TYPE_LIGHT;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor lightSensor;
    SensorEventListener lightEventListener;
    View root;
    float maxValue;

    @SuppressLint("MissingInflatedId")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root= findViewById(R.id.root);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

           androidx.appcompat.widget.Toolbar toolbar= findViewById(R.id.toolbar_view);

        if(lightSensor == null){
            Toast.makeText(this, "No liight sensor available", Toast.LENGTH_SHORT).show();
             finish();
    }

        maxValue = lightSensor.getMaximumRange();

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value =sensorEvent.values[0];

                toolbar.setTitle("Lux: " + value + "lx");
                int newValue =(int)(255f * value/maxValue);
                root.setBackgroundColor(Color.rgb(newValue,newValue,newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public MainActivity() {
        super();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
}