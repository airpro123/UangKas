package com.example.andri.uangkas.Helper;

import java.util.ArrayList;
import java.util.HashMap;

public class Model  {

    public String status;
    public String jumlah;
    public String keterangan;

    public ArrayList<HashMap<String,String>> semuaData;

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public ArrayList<HashMap<String, String>> getSemuaData() {
        return semuaData;
    }

    public void setSemuaData(ArrayList<HashMap<String, String>> semuaData) {
        this.semuaData = semuaData;
    }

    public Model(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKet() {
        return keterangan;
    }

    public void setKet(String ket) {
        this.keterangan = ket;
    }

}
