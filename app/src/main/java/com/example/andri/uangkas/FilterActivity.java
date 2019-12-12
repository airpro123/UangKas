package com.example.andri.uangkas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_filter, btn_hapusFilter;
    EditText et_tanggal, et_tanggal2;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    String klik_tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        et_tanggal = findViewById(R.id.et_tanggal);
        et_tanggal2 = findViewById(R.id.et_tanggal2);

        et_tanggal.setInputType(InputType.TYPE_NULL);
        et_tanggal2.setInputType(InputType.TYPE_NULL);

        btn_filter = findViewById(R.id.btn_filter);
        btn_hapusFilter = findViewById(R.id.btn_hapus_filter);

        btn_filter.setOnClickListener(this);
        btn_hapusFilter.setOnClickListener(this);
        et_tanggal.setOnClickListener(this);
        et_tanggal2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_tanggal:
                klik_tanggal = "1";
                showDateDialog();
                break;

            case R.id.et_tanggal2:
                klik_tanggal = "2";
                showDateDialog();
                break;

            case R.id.btn_filter:
                filterData();
                break;

            case R.id.btn_hapus_filter:
                hapusFilter();
                break;

        }
    }

    public void hapusFilter(){

        Intent i = new Intent(FilterActivity.this, MainActivity.class);
        i.putExtra("tgl","0");
        i.putExtra("tgl2","0");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    public void filterData(){

        String tgl = et_tanggal.getText().toString();
        String tgl2 = et_tanggal2.getText().toString();

        if (tgl.equals("")||tgl2.equals("")){

            Toast.makeText(this, "Tanggal harus diisi", Toast.LENGTH_SHORT).show();

        }else{

            Intent i = new Intent(FilterActivity.this, MainActivity.class);
            i.putExtra("tgl",tgl);
            i.putExtra("tgl2",tgl2);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
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

                if (klik_tanggal.equals("1")){
                    et_tanggal.setText(dateFormatter.format(newDate.getTime()));
                }else{
                    et_tanggal2.setText(dateFormatter.format(newDate.getTime()));
                }

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

}
