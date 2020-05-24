package com.example.myselfview.sections.bind;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.example.myselfview.sections.jdtoast.MCToast;

public class CameraService extends Service {

	private static final String TAG = "CameraService";

	public class FirstBinder extends Binder implements IService {
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
			String str = data.readString();
			Log.e(TAG, str);

			reply.writeString("这里是FirstBinder onTransact");
			return super.onTransact(code, data, reply, flags);
		}

		@Override
		public void callMethodInService() {
			Log.e(TAG, "执行到了servcie里的方法callMethodInService");
			MCToast.show("执行到了servcie里的方法callMethodInService");
		}
	}

	@Override
	public void onCreate() {
		Log.e(TAG, "执行到了CameraService  onCreate");
		MCToast.show("执行到了 CameraService onCreate");
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.e(TAG, "执行到了CameraService  onBind");
		MCToast.show("执行到了 CameraService onBind");
		return new FirstBinder();
	}

}
