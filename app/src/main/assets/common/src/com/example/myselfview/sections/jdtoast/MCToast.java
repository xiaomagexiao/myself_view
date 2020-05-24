package com.example.myselfview.sections.jdtoast;

import android.content.Context;
import android.widget.Toast;

import com.example.myselfview.index.MoocApplication;

public class MCToast {
	private static Toast mToast;

	/**
	 * 系统自带的普通toast
	 * 
	 * @param msg
	 */
	public static void show(String msg) {
		show(MoocApplication.getInstance(), msg);
	}

	public static void show(Context mContext, String msg) {
		show(mContext, msg, 0);
	}

	public static void show(Context mContext, String msg, int duration) {
		if (MCToast.mToast == null) {
			MCToast.mToast = new Toast(mContext);
			MCToast.mToast = Toast.makeText(mContext, ((CharSequence) msg), duration);
		}

		MCToast.mToast.setDuration(duration);
		MCToast.mToast.setText(((CharSequence) msg));
		MCToast.mToast.show();
	}
}
