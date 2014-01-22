package com.dungnh8.smscute.util;

import java.security.MessageDigest;

import android.util.Log;

public class SecureUtil {
	private static final String TAG = "SecureUtil";
	private static final String API_KEY = "api_key=";
	private static final String TIME = "time=";

	public static String getApiSig(String url_endpoint) {
		java.util.Date date = new java.util.Date();
		String request_string = url_endpoint + API_KEY + Constants.API_KEY
				+ TIME + date.getTime() + Constants.API_SECRET;
		return sha1(request_string);
	}

	private static String sha1(String input) {
		try {
			Log.i(TAG, input);
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (Exception e) {
			Log.e(TAG, "sha1", e);
			return null;
		}
	}
}