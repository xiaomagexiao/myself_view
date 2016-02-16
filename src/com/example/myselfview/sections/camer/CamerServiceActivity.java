package com.example.myselfview.sections.camer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myself_view.R;


public class CamerServiceActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button bt_recordingButton;
    private Button bt_stop;
    private Button bt_shot;

    private CameraService.FirstBinder binder;

    ServiceConnection conn = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // ���h��Serviceȡ��IBinder�omain��binder
            binder = (CameraService.FirstBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camer_service_activity);

        bt_recordingButton = (Button) findViewById(R.id.recording_button);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_shot = (Button) findViewById(R.id.bt_shot);

        bt_recordingButton.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
        bt_shot.setOnClickListener(this);
    }

    int type = 1;
    private void startRecording() {
        Intent intent = new Intent(this, CameraService.class);
        if(type==1){
            startService(intent);
        }else {
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
        Log.e(TAG, "startRecording");

    }

    private void stopRecording() {

        if(type==1){
            Intent intent = new Intent(this, CameraService.class);
            stopService(intent);
        }else {
             unbindService(conn);
        }
        Log.e(TAG, "startRecording");

    }

    private void takeShot() {
        Log.e(TAG, "takeShot");
        binder.callMethodInService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_shot:
                takeShot();
                break;
            case R.id.recording_button:
                startRecording();
                break;
            case R.id.bt_stop:
                stopRecording();
                break;
        }
    }
}
