package com.example.angyang.aymusic.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import a.a.a.f;
import a.a.a.d;
import android.widget.Toast;
import com.example.angyang.aymusic.R;

public class MainActivity extends Activity {

    private d mLlConnect;

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

//        InnerConnectLinstenerImpl innerConnectLinstener = new InnerConnectLinstenerImpl() {
//            @Override
//            public void requestData(String uri, String requestData) {
//                Log.d("HttpNettyServer--", "requestData-----" + requestData + "  uri-->" + uri + " currentThread= " + Thread.currentThread());
//                if ("/facereq/heartbeat".equals(uri)) {
//                    String resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/heartbeat\"}";
//                    httpNettyServer.writeResponse(resBody);
//                    Log.d("HttpMsgHandler", "00-----");
//                } else if ("/facereq/capture".equals(uri)) {
//                    String resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/capture\"}";
//                    httpNettyServer.writeResponse(resBody);
//
//                } else if ("/facereq/compare".equals(uri)) {
//                    Log.d("HttpMsgHandler", "22---- ");
//                    String resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/compare\"}";
//                    httpNettyServer.writeResponse(resBody);
//                }
//            }
//        };
//        httpNettyServer = HttpNettyServer.getInstance(innerConnectLinstener);
//        httpNettyServer.start(8008);


//        f innerConnectLinstener = new f() {
//            @Override
//            public void a(String uri, String requestData) {
//                Log.d("HttpNettyServer--", "requestData-----" + requestData + "  uri-->" + uri + " currentThread= " + Thread.currentThread());
//                if ("/facereq/heartbeat".equals(uri)) {
//                    String resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/heartbeat\"}";
//                    httpNettyServer.a(resBody);
//                    Log.d("HttpMsgHandler", "00-----");
//                } else if ("/facereq/capture".equals(uri)) {
//                    String resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/capture\"}";
//                    httpNettyServer.a(resBody);
//
//                } else if ("/facereq/compare".equals(uri)) {
//                    Log.d("HttpMsgHandler", "22---- ");
//                    String resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/compare\"}";
//                    httpNettyServer.a(resBody);
//                }
//            }
//
//        };
//        httpNettyServer = d.a(innerConnectLinstener);
//        httpNettyServer.a(8008);


        new Thread(() -> {
            f innerConnectLinstener = new f() {
                @Override
                public void a(String uri, String requestData) {
                    Log.d("HttpNettyServer--", "requestData-----" + requestData + "  uri-->" + uri + " currentThread= " + Thread.currentThread());

                    if ("/facereq/heartbeat".equals(uri)) {
//                        handlerHeartbeatReq();
                        String resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/heartbeat\"}";
                        mLlConnect.a(resBody);
                    } else if ("/facereq/capture".equals(uri)) {
//                        handlerFaceImgRegisterReq();
                    } else if ("/facereq/compare".equals(uri)) {
//                        handlerIdCompareRegisterReq(uri, requestData);
                    }
                }
            };
            mLlConnect = d.a(innerConnectLinstener);
            mLlConnect.a(8008);
        }
        ).start();

    }
}
