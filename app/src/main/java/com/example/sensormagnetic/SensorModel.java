package com.example.sensormagnetic;

public class SensorModel {
    Integer id;
    String waktu, x, y, z, m;

    public SensorModel(String waktu, String x, String y, String z, String m) {
        this.waktu = waktu;
        this.x = x;
        this.y = y;
        this.z = z;
        this.m = m;
    }

    public SensorModel(Integer id, String waktu, String x, String y, String z, String m) {
        this.id = id;
        this.waktu = waktu;
        this.x = x;
        this.y = y;
        this.z = z;
        this.m = m;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
}
