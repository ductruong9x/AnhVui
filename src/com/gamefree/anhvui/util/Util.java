package com.gamefree.anhvui.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class Util {

	public static int getCacheSize(Context context) {
		int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 16;
		// Log.e("cacheSize", "cacheSizeVOLLEY:" + cacheSize);
		return cacheSize;
	}

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static void writetoFile(String content, String filename) {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/" + filename + ".txt";
		try {

			BufferedWriter out = new BufferedWriter(new FileWriter(path, true));
			out.write(content);
			out.close();
		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
		}
	}

	public static String convertDate(int time) {
		String date_convert = null;
		Date date = new Date(time * (long) 1000);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
		date_convert = format.format(date).toString();
		return date_convert;
	}
}
