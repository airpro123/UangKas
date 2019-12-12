package com.example.andri.uangkas;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.andri.uangkas.Helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity implements RippleView.OnClickListener {

    TextView tv_id, tv_status, tv_jml, tv_ket, tv_tgl;
    String id, status, jml, ket, tgl;
    RippleView btn_edit, btn_hapus, btn_keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //setActivityBackgroundColor(Color.parseColor("#ecf0f1"));

        tv_id       = findViewById(R.id.tv_id_detail);
        tv_status   = findViewById(R.id.tv_status_detail);
        tv_jml      = findViewById(R.id.tv_jumlah_detail);
        tv_ket      = findViewById(R.id.tv_keterangan_detail);
        tv_tgl      = findViewById(R.id.tv_tanggal_detail);

        btn_edit    = findViewById(R.id.rip_edit);
        btn_hapus   = findViewById(R.id.rip_hapus);
        btn_keluar  = findViewById(R.id.rip_keluar);

        detailData();

        btn_edit.setOnClickListener(this);
        btn_hapus.setOnClickListener(this);
        btn_keluar.setOnClickListener(this);

    }

    public void detailData(){

        Intent i    = getIntent();
        id          = i.getStringExtra("id");
        status      = i.getStringExtra("status");
        jml         = i.getStringExtra("jumlah");
        ket         = i.getStringExtra("keterangan");
        tgl         = i.getStringExtra("tanggal");

        tv_id.setText(id);
        tv_status.setText(status);
        tv_jml.setText(jml);
        tv_ket.setText(ket);
        tv_tgl.setText(tgl);

    }

    public void hapusData(){

        postJSON pj = new postJSON(this);
        pj.hapusData(id);

        kembali();

    }

    public void kembali(){

        Intent i = new Intent(DetailActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == btn_edit.getId()){
            Intent iedit = new Intent(DetailActivity.this, EditActivity.class);
            iedit.putExtra("id",id);
            iedit.putExtra("status",status);
            iedit.putExtra("jml",jml);
            iedit.putExtra("ket",ket);
            iedit.putExtra("tgl",tgl);
            startActivity(iedit);
        }
        if (v.getId() == btn_hapus.getId()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Peringatan")
                    .setMessage("Apakah anda yakin akan menghapus data \""+ket+"\"")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            hapusData();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
        if (v.getId() == btn_keluar.getId()){

            finish();

        }
    }
}
