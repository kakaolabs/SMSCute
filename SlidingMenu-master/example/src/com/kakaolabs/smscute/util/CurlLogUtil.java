package com.kakaolabs.smscute.util;

import android.util.Log;

public class CurlLogUtil {
	private static final String CURL_GET = "curl -XGET ";
	private static final String CURL_POST = "curl -XPOST ";

	private static final String getFormatCurlLog(String msg) {
		return "\"" + msg + "\"";
	}

	public static final void getMessage(final String TAG, String msg) {
		msg = getFormatCurlLog(msg);
		Log.i(TAG, CURL_GET + msg);
	}

	public static final void postMessage(final String TAG, String msg) {
		msg = getFormatCurlLog(msg);
		Log.i(TAG, CURL_POST + msg);
	}
}