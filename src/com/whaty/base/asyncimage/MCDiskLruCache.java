package com.whaty.base.asyncimage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Editor;
import com.jakewharton.disklrucache.DiskLruCache.Snapshot;

public class MCDiskLruCache implements ImageCache {
    private static final int APP_VERSION = 1;
    private static int IO_BUFFER_SIZE = 0;
    private static final int VALUE_COUNT = 1;
    private CompressFormat mCompressFormat;
    private int mCompressQuality;
    private DiskLruCache mDiskCache;

    static {
        MCDiskLruCache.IO_BUFFER_SIZE = 8192;
    }

    public MCDiskLruCache(Context context, String uniqueName, int diskCacheSize, CompressFormat compressFormat, int quality) {
        super();
        this.mCompressFormat = CompressFormat.JPEG;
        this.mCompressQuality = 70;
        try {
            this.mDiskCache = DiskLruCache.open(this.getDiskCacheDir(context, uniqueName), 1, 1, ((long)diskCacheSize));
            this.mCompressFormat = compressFormat;
            this.mCompressQuality = quality;
        }
        catch(IOException v1) {
            v1.printStackTrace();
        }
    }

    public void clearCache() {
        try {
            this.mDiskCache.delete();
        }
        catch(IOException v0) {
            v0.printStackTrace();
        }
    }

    public boolean containsKey(String key) {
        Snapshot v2 = null;
        boolean v0 = false;
        try {
            v2 = this.mDiskCache.get(key);
            if(v2 == null) {
               return false;
            }else{
            	return true;
            }
        }catch(Exception v1) {
        	v1.printStackTrace();
        }finally{
        	if(v2!=null){
        		v2.close();
        	}
        }
        return v0;
    }

    public Bitmap getBitmap(String key) {
        Snapshot v4;
        Bitmap v0 = null;
        InputStream v3 = null;
        try {
            v4 = this.mDiskCache.get(key);
            v3 = v4.getInputStream(0);
            if(v4 != null) {
            	v0 = BitmapFactory.decodeStream(new BufferedInputStream(v3, MCDiskLruCache.IO_BUFFER_SIZE));
            }
        }catch(Exception v2) {
           v2.printStackTrace();
        }finally{
        	if(v3 !=null){
        		try {
					v3.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return v0;
    }

    public File getCacheFolder() {
        return this.mDiskCache.getDirectory();
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        return new File(String.valueOf(context.getCacheDir().getPath()) + File.separator + uniqueName);
    }

    public void putBitmap(String key, Bitmap data) {
        Editor v1 = null;
        try {
            v1 = this.mDiskCache.edit(key);
            if(v1 == null) {
                return;
            }

            if(this.writeBitmapToFile(data, v1)) {
                this.mDiskCache.flush();
                v1.commit();
                return;
            }

            v1.abort();
        }
        catch(IOException v0) {
            if(v1 == null) {
                return;
            }

            try {
                v1.abort();
            }
            catch(IOException v2) {
            }
        }
    }

    private boolean writeBitmapToFile(Bitmap bitmap, Editor editor) throws IOException, FileNotFoundException {
        BufferedOutputStream v1 = null;
        BufferedOutputStream v0 = null;
        try {
            v1 = new BufferedOutputStream(editor.newOutputStream(0), MCDiskLruCache.IO_BUFFER_SIZE);
        }catch(Throwable v2) {
        	 if(v0 != null) {
                 v0.close();
             }
        	 return false;
        }

        try {
            bitmap.compress(this.mCompressFormat, this.mCompressQuality, ((OutputStream)v1));
            if(v1 == null) {
            	 return false;
            }
            v1.close();
        }
        catch(Throwable v2) {
            return false;
        }
        return true;
    }
}

