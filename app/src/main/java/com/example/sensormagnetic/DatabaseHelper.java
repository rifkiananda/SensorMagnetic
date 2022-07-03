package com.example.sensormagnetic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sensor.db";
    private static final String TABLE_NAME = "sensor_table";
    public static final String ID = "id";
    public static final String WAKTU = "waktu";
    public static final String KOOR_X = "x";
    public static final String KOOR_Y = "y";
    public static final String KOOR_Z = "z";
    public static final String KOOR_M = "m";

    private SQLiteDatabase sqLiteDatabase;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME +"("+ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + WAKTU + " VARCHAR, " + KOOR_X + " TEXT, " + KOOR_Y + " TEXT, " + KOOR_Z +
            " TEXT," + KOOR_M + " TEXT);";

    public DatabaseHelper(Context context) {

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addSensor(SensorModel sensorModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.WAKTU, sensorModel.getWaktu());
        contentValues.put(DatabaseHelper.KOOR_X, sensorModel.getX());
        contentValues.put(DatabaseHelper.KOOR_Y, sensorModel.getY());
        contentValues.put(DatabaseHelper.KOOR_Z, sensorModel.getZ());
        contentValues.put(DatabaseHelper.KOOR_M, sensorModel.getM());
        sqLiteDatabase  = this.getWritableDatabase();
        sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
    }

    public List<SensorModel> getSensorList() {
        String sql = "select * from " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<SensorModel> storeSensor = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String waktu = cursor.getString(1);
                String x = cursor.getString(2);
                String y = cursor.getString(3);
                String z = cursor.getString(4);
                String m = cursor.getString(5);
                storeSensor.add(new SensorModel(id,waktu,x,y,z,m));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeSensor;
    }
}
