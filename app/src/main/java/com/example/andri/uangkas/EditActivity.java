package com.example.andri.uangkas;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnFocusChangeListener, RippleView.OnRippleCompleteListener {

    public static final String TAG = "EditActivity";
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    EditText et_jumlah, et_keterangan, et_tanggal;
    RadioGroup rg_status;
    RadioButton rb_masuk, rb_keluar;
    RippleView rip_update;
    String id, status, jumlah, keterangan, tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        rg_status   = findViewById(R.id.rg_status_edit);
        rb_keluar   = findViewById(R.id.rb_keluar_edit);
        rb_masuk    = findViewById(R.id.rb_masuk_edit);

        et_tanggal = findViewById(R.id.et_tanggal);
        et_jumlah = findViewById(R.id.et_jumlah_edit);
        et_keterangan = findViewById(R.id.et_ket_edit);

        rip_update = findViewById(R.id.rip_update_edit);

        et_tanggal.setInputType(InputType.TYPE_NULL);

        setData();

        et_tanggal.setOnFocusChangeListener(this);
        rg_status.setOnCheckedChangeListener(this);

        rip_update.setOnRippleCompleteListener(this);

    }

    private void setData(){
        Intent i = getIntent();
        id          = i.getStringExtra("id");
        status      = i.getStringExtra("status");
        jumlah      = i.getStringExtra("jml");
        keterangan  = i.getStringExtra("ket");
        tanggal     = i.getStringExtra("tgl");

        if (status.equals("MASUK")){
            rb_masuk.setChecked(true);
        }else{
            rb_keluar.setChecked(true);
        }

        et_jumlah.setText(jumlah);
        et_keterangan.setText(keterangan);
        et_tanggal.setText(tanggal);
    }

    private void updateData(){

        postJSON pj = new postJSON(this);
        pj.updateData(
                id,
                status,
                et_jumlah.getText().toString(),
                et_keterangan.getText().toString(),
                et_tanggal.getText().toString()
        );

        Intent i = new Intent(EditActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                et_tanggal.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_masuk_edit:
                status = "MASUK";
                break;
            case R.id.rb_keluar_edit:
                status = "KELUAR";
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            showDateDialog();
        }
    }

    @Override
    public void onComplete(RippleView rippleView) {
        updateData();
    }
}
