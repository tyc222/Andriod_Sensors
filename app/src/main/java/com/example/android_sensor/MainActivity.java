package com.example.android_sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Create SensorManager and Sensors
    private SensorManager sensorManager;
    private Sensor light;
    private Sensor temperature;
    private Sensor relativeHumidity;
    private Sensor airPressure;
    //Create TextViews
    private TextView lightView;
    private TextView powerView;
    private TextView temperatureView;
    private TextView relativeHumidityView;
    private TextView airPressureView;

    /**
    Menu options
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set App Title
        setTitle("Android Environmental Sensors");



        // Find views
        lightView = findViewById(R.id.illuminance);
        powerView = findViewById(R.id.estimate_power);
        temperatureView= findViewById(R.id.air_temperature);
        relativeHumidityView = findViewById(R.id.relative_humidity);
        airPressureView = findViewById(R.id.air_pressure);

        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        relativeHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        airPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);


        // Check if device support sensor, if not, let the user know
        if (light == null) {
        lightView.setText("This device doesn't support light sensor!");
        powerView.setText("This device doesn't support power estimation");
        }

        if (temperature == null){
            temperatureView.setText("This device doesn't support temperature sensor!");
        }

        if (relativeHumidity == null) {
            relativeHumidityView.setText("This device doesn't support relative humidity!");
        }


        if (airPressure == null){
            airPressureView.setText("This device doesn't support air pressure!");
        }
    }


    /**
     * When Sensor senses sth, display that data
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        int type = event.sensor.getType();

        switch (type) {
            case Sensor.TYPE_LIGHT:
                lightView.setText(String.valueOf(event.values[0]) + " lx");
                double irradiance = event.values[0] *0.0079;
                DecimalFormat f = new DecimalFormat("##.00");
                powerView.setText(String.valueOf(f.format(irradiance)) + " W/m^2");
                break;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                temperatureView.setText(String.valueOf(event.values[0]) + " °C");
                break;

            case Sensor.TYPE_RELATIVE_HUMIDITY:
                relativeHumidityView.setText(String.valueOf(event.values[0]) + " %");
                break;

            case Sensor.TYPE_PRESSURE:
                airPressureView.setText(String.valueOf(event.values[0]) + " hPa");
                break;
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Register Sensor Listeners onResume
     */
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, relativeHumidity, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, airPressure, SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * Unregister Listener to save battery and resource
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
