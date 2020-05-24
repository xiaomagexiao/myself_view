package com.whaty.base.asyncimage;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MCRequestManager {
	private static RequestQueue mRequestQueue;

	private MCRequestManager() {
		super();
	}

	public static RequestQueue getRequestQueue() {
		return MCRequestManager.mRequestQueue;

	}

	public static void init(Context context) {
		MCRequestManager.mRequestQueue = Volley.newRequestQueue(context);
	}
}
