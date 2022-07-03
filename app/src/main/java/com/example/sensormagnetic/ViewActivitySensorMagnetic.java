package com.example.sensormagnetic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class ViewActivitySensorMagnetic extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sensor_magnetic);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<SensorModel> sensorModel = databaseHelper.getSensorList();

        if (sensorModel.size() > 0){
            SensorAdapter sensorAdapter = new SensorAdapter(sensorModel,ViewActivitySensorMagnetic.this);
            recyclerView.setAdapter(sensorAdapter);
        }else{

        }
    }
}