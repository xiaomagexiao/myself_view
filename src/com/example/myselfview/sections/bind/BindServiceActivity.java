package com.example.myselfview.sections.bind;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.myself_view.R;
import com.example.myselfview.sections.jdtoast.MCToast;

public class BindServiceActivity extends Activity implements OnClickListener {
	private static final String TAG = "MainActivity";
	private Button bt_bind;
	private Button bt_unbind;
	private Button bt_call_method;
	private Button bt_change_type;
	private TextView tv_info;

	private CameraService.FirstBinder binder;
	private boolean bindFlag = false;

	int bindType = 1; // 1是start形式开启 其他为bind形式开启service

	ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// 從遠端Service取得IBinder給main的binder
			binder = (CameraService.FirstBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_service_activity);

		bt_call_method = (Button) findViewById(R.id.bt_call_method);
		bt_bind = (Button) findViewById(R.id.bt_bind);
		bt_unbind = (Button) findViewById(R.id.bt_unbind);
		bt_change_type = (Button) findViewById(R.id.bt_change_type);
		tv_info = (TextView) findViewById(R.id.tv_info);

		bt_call_method.setOnClickListener(this);
		bt_bind.setOnClickListener(this);
		bt_unbind.setOnClickListener(this);
		bt_change_type.setOnClickListener(this);

		updateBindInfo();

	}

	/**
	 * Context.startService()： Service会经历onCreate ->
	 * onStart（如果Service还没有运行，则android先调用onCreate
	 * ()然后调用onStart()；如果Service已经运行，则只调用onStart
	 * ()，所以一个Service的onStart方法可能会重复调用多次 ）；
	 * stopService的时候直接onDestroy，如果是调用者自己直接退出而没有调用stopService的话
	 * ，Service会一直在后台运行。该Service的调用者再启动起来后可以通过stopService关闭Service。
	 * 调用顺序为：onCreate --> onStart(可多次调用) --> onDestroy。
	 * 
	 * Context.bindService()：Service会经历onCreate() ->
	 * onBind()，onBind将返回给客户端一个IBind接口实例
	 * ，IBind允许客户端回调服务的方法，比如得到Service运行的状态或其他操作。
	 * 这个时候把调用者（Context，例如Activity）会和Service绑定在一起，Context退出了，Srevice就会调用onUnbind
	 * -> onDestroyed相应退出，所谓绑定在一起就共存亡了 。
	 * 
	 * 补充说明：传递给bindService()的Intent对象会传递给onBind()，传递给unbindService()
	 * 的Intent对象会传递给onUnbind()方法。 调用顺序为：onCreate --> onBind(只一次，不可多次绑定) -->
	 * onUnbind --> onDestory。
	 */
	private void startRecording() {
		Intent intent = new Intent(this, CameraService.class);
		if (bindType == 1) {
			startService(intent);
		} else {
			bindService(intent, conn, Context.BIND_AUTO_CREATE);
		}
		Log.e(TAG, "startRecording");
		bindFlag = true;
	}

	private void stopRecording() {
		if (bindType == 1) {
			Intent intent = new Intent(this, CameraService.class);
			stopService(intent);
		} else {
			unbindService(conn);
		}
		bindFlag = false;
		Log.e(TAG, "stopRecording");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_call_method:
			if (bindType == 1) {
				MCToast.show("只有bind方式启动的可以调用方法");
			} else {
				Log.e(TAG, "callMethodInService");
				if (bindFlag) {
					binder.callMethodInService();
				} else {
					MCToast.show("只有绑定后才可以执行方法！！！");
				}
			}
			break;
		case R.id.bt_bind:
			startRecording();
			break;
		case R.id.bt_unbind:
			stopRecording();
			break;
		case R.id.bt_change_type:
			chageType();
			break;
		}
	}

	private void chageType() {
		stopRecording();
		bindType = bindType == 1 ? 0 : 1;
		updateBindInfo();
	}

	private void updateBindInfo() {
		String info = "当前开始service模式为：";
		if (bindType == 1) {
			info += "start 模式";
		} else {
			info += "bind 模式";
		}
		tv_info.setText(info);
	}
}
