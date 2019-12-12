package com.example.andri.uangkas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.andri.uangkas.Helper.Config;
import com.example.andri.uangkas.Helper.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    ArrayList<Integer> myString;
    ArrayList<String> myStatus, myJumlah, myKet, myTgl;
    ArrayList<HashMap<String,String>> list;

    private ListView listView;
    private String transaksi_id, status, jumlah, keterangan, tanggal;

    private String tgl, tgl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AndroidNetworking.initialize(this);

        Bundle extras = getIntent().getExtras();

        //Validasi FILTER
        if (extras==null){

            semuaData();

        }else{

            Intent i = getIntent();
            tgl = i.getStringExtra("tgl");
            tgl2 = i.getStringExtra("tgl2");

            if (tgl.equals("0") && tgl2.equals("0")){

                semuaData();

            }else{

                filterData(tgl, tgl2);

            }

        }

        listView = findViewById(R.id.listview);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, TambahData.class);
                startActivity(i);

            }
        });

        listView.setOnItemClickListener(this);
    }

    public void semuaData(){

        AndroidNetworking.get("http://192.168.100.7/uangkas/list.php/")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("semuaData", "onResponse: ");
                            list = new ArrayList<HashMap<String,String>>();

                            String masuk = response.getString("masuk");
                            String keluar = response.getString("keluar");
                            String saldo = response.getString("saldo");

                            JSONArray mJSONResult = response.getJSONArray("result");

                            for (int i = 0; i < mJSONResult.length(); i++) {

                                JSONObject mJsonObjectProperty = mJSONResult.getJSONObject(i);

                                String transaksi_id = mJsonObjectProperty.getString("transaksi_id");
                                String status       = mJsonObjectProperty.getString("status");
                                String jumlah       = mJsonObjectProperty.getString("jumlah");
                                String keterangan   = mJsonObjectProperty.getString("keterangan");
                                String tanggal      = mJsonObjectProperty.getString("tanggal");

                                HashMap<String,String> dataUang = new HashMap<>();
                                dataUang.put(Config.TAG_ID,transaksi_id);
                                dataUang.put(Config.TAG_STATUS,status);
                                dataUang.put(Config.TAG_JUMLAH,jumlah);
                                dataUang.put(Config.TAG_KETERANGAN,keterangan);
                                dataUang.put(Config.TAG_TANGGAL,tanggal);
                                list.add(dataUang);
                            }

                            ListAdapter adapter = new SimpleAdapter(
                                    MainActivity.this, list, R.layout.list_item,
                                    new String[]{Config.TAG_TANGGAL, Config.TAG_STATUS, Config.TAG_JUMLAH, Config.TAG_ID, Config.TAG_KETERANGAN},
                                    new int[]{R.id.et_tanggal, R.id.et_status, R.id.et_jumlah, R.id.et_id, R.id.et_ket_item});

                            listView.setAdapter(adapter);

                        }catch (JSONException e){
                            Log.d("semuaData", "onResponse: "+ e);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("semuaData", "onError: "+ anError);
                    }
                });
    }

    public void filterData(String tanggal, String tanggal2){
        AndroidNetworking.post("http://192.168.100.7/uangkas/filter.php")
                .addBodyParameter("from",tanggal)
                .addBodyParameter("to", tanggal2)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("semuaData", "onResponse: ");
                            list = new ArrayList<HashMap<String,String>>();

                            String masuk = response.getString("masuk");
                            String keluar = response.getString("keluar");
                            String saldo = response.getString("saldo");

                            JSONArray mJSONResult = response.getJSONArray("result");

                            for (int i = 0; i < mJSONResult.length(); i++) {

                                JSONObject mJsonObjectProperty = mJSONResult.getJSONObject(i);

                                String transaksi_id = mJsonObjectProperty.getString("transaksi_id");
                                String status       = mJsonObjectProperty.getString("status");
                                String jumlah       = mJsonObjectProperty.getString("jumlah");
                                String keterangan   = mJsonObjectProperty.getString("keterangan");
                                String tanggal      = mJsonObjectProperty.getString("tanggal");

                                HashMap<String,String> dataUang = new HashMap<>();
                                dataUang.put(Config.TAG_ID,transaksi_id);
                                dataUang.put(Config.TAG_STATUS,status);
                                dataUang.put(Config.TAG_JUMLAH,jumlah);
                                dataUang.put(Config.TAG_KETERANGAN,keterangan);
                                dataUang.put(Config.TAG_TANGGAL,tanggal);
                                list.add(dataUang);
                            }

                            ListAdapter adapter = new SimpleAdapter(
                                    MainActivity.this, list, R.layout.list_item,
                                    new String[]{Config.TAG_TANGGAL, Config.TAG_STATUS, Config.TAG_JUMLAH, Config.TAG_ID, Config.TAG_KETERANGAN},
                                    new int[]{R.id.et_tanggal, R.id.et_status, R.id.et_jumlah, R.id.et_id, R.id.et_ket_item});

                            listView.setAdapter(adapter);

                        }catch (JSONException e){
                            Log.d("semuaData", "onResponse: "+ e);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {

            Intent iPop = new Intent(MainActivity.this, FilterActivity.class);
            startActivity(iPop);

            return true;
        }

        if (id == R.id.action_close){

            tutupAplikasi();

        }

        return super.onOptionsItemSelected(item);
    }

    public void tutupAplikasi(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv_id = view.findViewById(R.id.et_id);
        TextView tv_status = view.findViewById(R.id.et_status);
        TextView tv_jml = view.findViewById(R.id.et_jumlah);
        TextView tv_ket = view.findViewById(R.id.et_ket_item);
        TextView tv_tgl = view.findViewById(R.id.et_tanggal);

        transaksi_id = tv_id.getText().toString();
        status = tv_status.getText().toString();
        jumlah = tv_jml.getText().toString();
        keterangan = tv_ket.getText().toString();
        tanggal = tv_tgl.getText().toString();

        panggilDetail(transaksi_id, status, jumlah, keterangan, tanggal);
    }

    public void panggilMain(){
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void panggilDetail(String id, String status, String jumlah, String keterangan, String tanggal){
        Intent i = new Intent(MainActivity.this, DetailActivity.class);
        i.putExtra("id", id);
        i.putExtra("status", status);
        i.putExtra("jumlah", jumlah);
        i.putExtra("keterangan", keterangan);
        i.putExtra("tanggal", tanggal);
        startActivity(i);
    }
}
