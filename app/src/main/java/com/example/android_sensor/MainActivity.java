package com.example.android_sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor light;
    private Sensor temperature;
    private Sensor relativeHumidity;
    private Sensor airPressure;
    private TextView lightView;
    private TextView powerView;
    private TextView temperatureView;
    private TextView absoluteHumidityView;
    private TextView relativeHumidityView;
    private TextView airPressureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        lightView = findViewById(R.id.illuminance);
        powerView = findViewById(R.id.estimate_power);
        temperatureView= findViewById(R.id.air_temperature);
        absoluteHumidityView = findViewById(R.id.absolute_humidity);
        relativeHumidityView = findViewById(R.id.relative_humidity);
        airPressureView = findViewById(R.id.air_temperature);

        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        relativeHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        airPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        if (light == null) {
        lightView.setText("This device doesn't support light sensor!");
        powerView.setText("This device doesn't support power estimation");
        }

        if (temperature == null){
            temperatureView.setText("This device doesn't support temperature sensor!");
        }

        if (relativeHumidity == null) {
            absoluteHumidityView.setText("This device doesn't support absolute humidity!");
            relativeHumidityView.setText("This device doesn't support relative humidity!");
        }

        if (airPressure == null){
            airPressureView.setText("This device doesn't support air pressure!");
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {



        lightView.setText(String.valueOf(event.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
