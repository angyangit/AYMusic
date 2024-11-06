package com.example.angyang.aymusic.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.angyang.aymusic.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv= (ImageView) findViewById(R.id.iv);
//        c.a().a(8008, new e() {
//            @Override
//            public void a(String uri, String requestData) {
//                Log.d(" AYIT==> ", "httpnetty  requestData-----" + uri + "  " + requestData + " currentThread= " + Thread.currentThread());
//            }
//
//            @Override
//            public void a(boolean b) {
//
//            }
//        });

    }
}
