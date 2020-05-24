package com.example.myselfview.sections.camer;

import java.io.IOException;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class CameraService extends Service implements SurfaceHolder.Callback {

    private static final String TAG = "CameraService";
    private Camera mCamera;

    private PreviewCallback previewCallback;

    public class FirstBinder extends Binder implements IService {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String str = data.readString();
            Log.e(TAG, str);

            reply.writeString("����FirstService");
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public void callMethodInService() {
            if (mCamera != null) {
                requestPreviewFrame();
                Log.e(TAG, "ִ�е��ˣ�mCamera.takePicture");
            }
            Log.e(TAG, "ִ�е��ˣ�callMethodInService" + mCamera);
        }
    }


    @Override
    public void onCreate() {
        previewCallback = new PreviewCallback();
        Log.e(TAG, "======= service in onStartCommand");
        initCamer();
        super.onCreate();
    }

    private void initCamer() {
        if (Util.checkCameraHardware(this)) {
            mCamera = Util.getCameraInstance();
            if (mCamera != null) {
                SurfaceView sv = new SurfaceView(this);
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(1, 1,
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                        PixelFormat.TRANSLUCENT);
                SurfaceHolder sh = sv.getHolder();
                sv.setZOrderOnTop(true);
                sh.setFormat(PixelFormat.TRANSPARENT);
                sh.addCallback(this);
                wm.addView(sv, params);
            } else {
                Log.e(TAG, "==== get Camera from service failed");
            }
        } else {
            Log.e(TAG, "==== There is no camera hardware on device.");
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new FirstBinder();
    }

    private void takeOneshot() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestPreviewFrame();
                takeOneshot();
            }
        }, 3000);
    }


    public synchronized void requestPreviewFrame() {
        if (mCamera != null) {
            mCamera.setOneShotPreviewCallback(previewCallback);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Camera.Parameters params = mCamera.getParameters();
        mCamera.setParameters(params);
        Camera.Parameters p = mCamera.getParameters();

        List<Camera.Size> listSize;

        listSize = p.getSupportedPreviewSizes();
        Camera.Size mPreviewSize = listSize.get(2);
        Log.e(TAG, "preview width = " + mPreviewSize.width
                + " preview height = " + mPreviewSize.height);
        p.setPreviewSize(mPreviewSize.width, mPreviewSize.height);

        listSize = p.getSupportedPictureSizes();
        Camera.Size mPictureSize = listSize.get(2);
        Log.e(TAG, "capture width = " + mPictureSize.width
                + " capture height = " + mPictureSize.height);
        p.setPictureSize(mPictureSize.width, mPictureSize.height);
        mCamera.setParameters(p);

        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();

        Log.e(TAG, "========= recording start");

        takeOneshot();

        Log.e(TAG, "======= surfaceCreated " + mCamera);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
