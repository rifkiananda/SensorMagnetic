package com.example.sensormagnetic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MagneticActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    TextView textX, textY, textZ, textWaktu, textM;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;

    Button button_database;
    boolean flag = false;
    int interval = 2000;
    Handler handler;
    private final Runnable processSensors = new Runnable() {
        @Override
        public void run() {
            flag = true;
            handler.postDelayed(this, interval);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetic);

        handler = new Handler();

        textWaktu = findViewById(R.id.textWaktu);
        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        textZ = findViewById(R.id.textZ);
        textM = findViewById(R.id.textM);
        button_database = findViewById(R.id.buttondatabase);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            textX.setText("0");
            textY.setText("0");
            textZ.setText("0");
            textM.setText("0");
        } else {
            textX.setText("Sensor NA");
            textY.setText("Sensor NA");
            textZ.setText("Sensor NA");
            textM.setText("Sensor NA");
        }

        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                calendar=Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                Date=simpleDateFormat.format((calendar.getTime()));
                textWaktu.setText(Date);

                String stringWaktu = textWaktu.getText().toString();
                String stringX = textX.getText().toString();
                String stringY = textY.getText().toString();
                String stringZ = textZ.getText().toString();
                String stringM = textM.getText().toString();

                DatabaseHelper databaseHelper = new DatabaseHelper(MagneticActivity.this);
                SensorModel sensorModel = new SensorModel(stringWaktu,stringX,stringY,stringZ,stringM);
                databaseHelper.addSensor(sensorModel);
                handler.postDelayed(this, 120000);
            }
        };
        handler.postDelayed(refresh, 120000);
    }

    private SensorEventListener magnetic = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (flag){
                textX.setText(String.format("%.2f", sensorEvent.values[0]));
                textY.setText(String.format("%.2f", sensorEvent.values[1]));
                textZ.setText(String.format("%.2f", sensorEvent.values[2]));

                float azismuth = Math.round(sensorEvent.values[0]);
                float pitch = Math.round(sensorEvent.values[1]);
                float roll = Math.round(sensorEvent.values[2]);

                float tesla = (float) Math.sqrt((azismuth * azismuth) + (pitch * pitch) + (roll * roll));

                String text = String.format("%.2f" ,tesla);
                textM.setText(text + "μT");

                flag = false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public void start(View view) {
        int delay = SensorManager.SENSOR_DELAY_NORMAL;
        sensorManager.registerListener(magnetic,sensor,delay);

        handler.post(processSensors);
    }

    public void stop(View view) {
        sensorManager.unregisterListener(magnetic);
    }

    public void database(View view) {
        Intent intent = new Intent(MagneticActivity.this,ViewActivitySensorMagnetic.class);
        startActivity(intent);
    }
}