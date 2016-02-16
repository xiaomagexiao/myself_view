package com.example.myselfview.sections.camer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtils {
	public FileUtils() {
		super();
	}

	public static String saveImage(final Bitmap bitmap) throws IOException, FileNotFoundException {
		String filePath = "";
		// SD卡可用
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// 创建文件目录
			File f = new File(getRootFilePath());
			f.mkdirs();
			// 获取成功
			if (bitmap != null) {
				// 获取当前系统时间
				Calendar cl = Calendar.getInstance();
				cl.setTime(Calendar.getInstance().getTime());
				long milliseconds = cl.getTimeInMillis();
				// 当前系统时间作为文件名
				String fileName = String.valueOf(milliseconds) + ".jpg";
				// 保存图片
				// 创建文件
				File file = new File(getRootFilePath(), fileName);

				file.createNewFile();
				if (!file.isFile()) {
					throw new FileNotFoundException();
				}
				filePath = file.getAbsolutePath();
				FileOutputStream fOut = null;
				fOut = new FileOutputStream(file);
				// 将图片转为输出流
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
				// 关闭输出流
				fOut.flush();
				fOut.close();
			}
		}
		return filePath;
	}
	
	  private static String getRootFilePath() {
	        File f = new File(Environment.getExternalStorageDirectory().getPath() + "/whaty/");
	        f.mkdirs();
	        StringBuffer stbPath = new StringBuffer();
	        stbPath.append(Environment.getExternalStorageDirectory().getPath());
	        stbPath.append(File.separator);
	        stbPath.append("whaty");
	        stbPath.append(File.separator);
	        stbPath.append("smallPic");
	        stbPath.append(File.separator);
	        return stbPath.toString();
	    }

}
