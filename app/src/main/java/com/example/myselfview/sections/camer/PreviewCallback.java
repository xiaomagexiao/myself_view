/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myselfview.sections.camer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

final class PreviewCallback implements Camera.PreviewCallback {

    private final String TAG = "CameraService";

    /**
     * 抓图一次的回调
     *
     * @param data
     * @param camera
     */
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.e("CameraService", "======= onPreviewFrame ");
        Bitmap bt = bitmapFromData(data, camera);
        
        try {
            String path = FileUtils.saveImage(bt);
            Log.e("CameraService", "======= onPreviewFrame " + path);
        } catch (IOException e) {
            Log.e("CameraService", "======= IOException " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 从data转换为bitmap
     *
     * @param data
     * @param camera
     * @return
     */
    private Bitmap bitmapFromData(byte[] data, Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        int imageFormat = parameters.getPreviewFormat();
        int w = parameters.getPreviewSize().width;
        int h = parameters.getPreviewSize().height;
        Rect rect = new Rect(0, 0, w, h);
        YuvImage yuvImg = new YuvImage(data, imageFormat, w, h, null);
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        yuvImg.compressToJpeg(rect, 100, outputstream);
        return BitmapFactory.decodeByteArray(outputstream.toByteArray(), 0, outputstream.size());
    }
}
