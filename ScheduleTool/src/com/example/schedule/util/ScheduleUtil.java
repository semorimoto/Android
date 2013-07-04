package com.example.schedule.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;

import android.content.Context;


public class ScheduleUtil {

	/**
	 * ストリングデータのファイルへの書き込み
	 * @param context
	 * @param str
	 * @param fileName
	 */
	public static void writeFile(Context context, String str, String fileName) {
		writeBinaryFile(context, str.getBytes(), fileName);
	}

	/**
	 * バイナリファイルの書き込み
	 * @param context
	 * @param data
	 * @param fileName
	 */
	public static void writeBinaryFile(Context context, byte[] data, String fileName) {
		OutputStream out = null;
		try {
			out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			out.write(data, 0, data.length);
			out.close();

		} catch (Exception e1) {
			try {
				if (out != null) out.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * ファイルからストリングデータの読み込み
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String readFile(Context context,String fileName) {
		File file = new File(context.getFilesDir().getPath() + "/" + fileName);
		if (!file.exists()) {
			return "";
		}
		byte[] w = readBinaryFile(context, fileName);
		return new String(w);
	}

	/**
	 * バイナリファイルの読み込み
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static byte[] readBinaryFile(Context context, String fileName) {
		int size;
		byte[] w = new byte[128];
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = context.openFileInput(fileName);
			out = new ByteArrayOutputStream();
			while (true) {
				size = in.read(w);
				if (size <= 0) break;
				out.write(w, 0, size);
			}
			in.close();
			out.close();
			return out.toByteArray();
		} catch (Exception e1) {
			try {
				if (in != null) in.close();
				if (out != null) out.close();
			} catch (Exception e2) {
			}
			return null;
		}
	}

	public static HashMap<String, String> readProperties(Context context, String fileName) {
		File file = new File(context.getFilesDir().getPath() + "/" + fileName);
		if (!file.exists()) {
			return null;
		}

		InputStream in = null;
		HashMap<String, String> ret = new LinkedHashMap<String, String>();
		Properties properties = new Properties();
		try {
			in = context.openFileInput(fileName);
			properties.load(in);

			Set<Object> keys = properties.keySet();
			for (Iterator<Object> i = keys.iterator(); i.hasNext();) {
				String key = (String)i.next();
				String value = (String)properties.getProperty(key);
				ret.put(key,  value);
			}
			in.close();
			return ret;
		} catch (Exception e1) {
			try {
				if (in != null) in.close();
			} catch (Exception e2) {
			}
			return null;
		}
	}

	public static void writeProperties(Context context, HashMap<String, String> map, String fileName) {
		OutputStream out = null;
		Properties properties = new Properties();

		try {
			out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			Set<String> keys = map.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = (String)i.next();
				String value = (String)map.get(key);
				properties.setProperty(key,  value);
			}
			properties.store(out, null);
			out.close();
		} catch (Exception e1) {
			try {
				if (out != null) out.close();
			} catch (Exception e2){
			}
		}
	}
}
