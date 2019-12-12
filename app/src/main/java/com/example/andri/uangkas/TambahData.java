package com.example.andri.uangkas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.androidnetworking.AndroidNetworking;

public class TambahData extends AppCompatActivity implements RippleView.OnRippleCompleteListener, RadioGroup.OnCheckedChangeListener {

    RadioGroup rg_status;
    RadioButton rb_masuk, rb_keluar;
    EditText et_jumlah, et_ket;
    RippleView rip_simpan;
    Button btn_simpan;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        AndroidNetworking.initialize(getApplicationContext());

        status = "";

        rg_status   = findViewById(R.id.rg_status);
        rb_masuk    = findViewById(R.id.rb_masuk);
        rb_keluar   = findViewById(R.id.rb_keluar);

        et_jumlah   = findViewById(R.id.et_jumlah);
        et_ket      = findViewById(R.id.et_ket);

        rip_simpan  = findViewById(R.id.rip_simpan);
        btn_simpan  = findViewById(R.id.btn_simpan);

        rg_status.setOnCheckedChangeListener(this);
        rip_simpan.setOnRippleCompleteListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_masuk:
                status = "MASUK";
                break;
            case R.id.rb_keluar:
                status = "KELUAR";
                break;
        }
    }

    @Override
    public void onComplete(RippleView rippleView) {
        if (status.equals("") || et_jumlah.getText().toString().equals("") || et_ket.getText().toString().equals("")){
            Toast.makeText(this, "Data harus diisi dengan benar !!!", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("Ripple", "onComplete: ");
            String jumlah = et_jumlah.getText().toString();
            String keterangan = et_ket.getText().toString();
            postJSON pj = new postJSON(this);
            pj.tambahData(status, jumlah, keterangan);

            Intent i = new Intent(TambahData.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

}
