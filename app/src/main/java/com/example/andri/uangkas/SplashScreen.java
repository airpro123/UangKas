package com.example.andri.uangkas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread th = new Thread(){
            @Override
            public void run() {
                try {
                    //tidurkan setelah berapa detik
                    sleep(5000);

                }catch (InterruptedException e){
                    //cetak jika erorr
                    e.printStackTrace();
                }finally {
                    //finally akan dijalankan terlepas try error atau tidak
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                //cara ini juga berhasil menghasilkan hasil yang sama dengan yang diatats
//                Intent i = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(i);
//                finish();

            }
        };
        th.start();

    }
}
