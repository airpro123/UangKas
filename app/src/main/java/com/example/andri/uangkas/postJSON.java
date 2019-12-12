package com.example.andri.uangkas;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.andri.uangkas.Helper.Config;
import com.example.andri.uangkas.Helper.Model;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class postJSON extends TambahData {

    Context context;

    public postJSON(Context context){
        this.context = context;
    }

    public void tambahData(String status, String jumlah, String ket){
        Log.d("postJSON", "tambahData: ");
        AndroidNetworking.post("http://192.168.100.7/uangkas/add.php")
                .addBodyParameter("status",status)
                .addBodyParameter("jumlah", jumlah)
                .addBodyParameter("keterangan", ket)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void updateData(String id, String status, String jumlah, String ket, String tanggal){
        AndroidNetworking.post("http://192.168.100.7/uangkas/update.php")
                .addBodyParameter("transaksi_id", id)
                .addBodyParameter("status", status)
                .addBodyParameter("jumlah", jumlah)
                .addBodyParameter("keterangan", ket)
                .addBodyParameter("tanggal", tanggal)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "Gagal mengupdate", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void hapusData(String id){

        AndroidNetworking.post("http://192.168.100.7/uangkas/delete.php")
                .addBodyParameter(Config.TAG_ID,id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Terhapus", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "Gagal Menghapus", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
