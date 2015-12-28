package com.whaty.base.asyncimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class MCCacheManager {
	public enum CacheType {
		DISK("DISK", 0), MEMORY("MEMORY", 1);

		private CacheType(String arg1, int arg2) {
		}

	}

	private ImageCache mImageCache;
	private ImageLoader mImageLoader;
	private static MCCacheManager mInstance;
	private static int[] cacheType;
	private RequestQueue mQueue;

	static int[] initCacheType() {
		int[] v0 = MCCacheManager.cacheType;
		if (v0 == null) {
			v0 = new int[CacheType.values().length];
			try {
				v0[CacheType.DISK.ordinal()] = 1;
			} catch (NoSuchFieldError v1) {
			}

			try {
				v0[CacheType.MEMORY.ordinal()] = 2;
			} catch (NoSuchFieldError v1) {
			}

			MCCacheManager.cacheType = v0;
		}

		return v0;
	}

	public MCCacheManager() {
		super();
		initCacheType();
	}

	private String createKey(String url) {
		return String.valueOf(url.hashCode());
	}

	public Bitmap getBitmap(String url) {
		try {
			return this.mImageCache.getBitmap(this.createKey(url));
		} catch (NullPointerException v0) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}

	public void getImage(String url, ImageListener listener) {
		this.mImageLoader.get(url, listener);
	}

	public ImageLoader getImageLoader() {
		return this.mImageLoader;
	}

	public static MCCacheManager getInstance() {
		if (MCCacheManager.mInstance == null) {
			MCCacheManager.mInstance = new MCCacheManager();
		}

		return MCCacheManager.mInstance;
	}

	public void init(Context context, String uniqueName, int cacheSize,
			CompressFormat compressFormat, int quality, CacheType type) {
		switch (MCCacheManager.cacheType[type.ordinal()]) {
		case 1: {//disk
			this.mImageCache = new MCDiskLruCache(context, uniqueName,
					cacheSize, compressFormat, quality);
		}
		case 2: {//memory
			this.mImageCache = new MCBitmapLruCache(cacheSize);
		}
		}

		RequestQueue queue = MCRequestManager.getRequestQueue();
		if (queue == null) {
			MCRequestManager.init(context);
		}

		this.mImageLoader = new ImageLoader(MCRequestManager.getRequestQueue(),
				this.mImageCache);

	}

	public void putBitmap(String url, Bitmap bitmap) {
		try {
			this.mImageCache.putBitmap(this.createKey(url), bitmap);
			return;
		} catch (NullPointerException v0) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}
}
