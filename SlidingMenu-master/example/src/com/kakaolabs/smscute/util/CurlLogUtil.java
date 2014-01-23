package com.kakaolabs.smscute.util;

import android.util.Log;

public class CurlLogUtil {
	private static final String CURL_GET = "curl -XGET ";
	private static final String CURL_POST = "curl -XPOST ";

	public static final void getInfo(final String TAG, String msg) {
		Log.i(TAG, CURL_GET + msg);
	}

	public static final void getDebug(final String TAG, String msg) {
		Log.d(TAG, CURL_GET + msg);
	}

	public static final void getError(final String TAG, String msg) {
		Log.e(TAG, CURL_GET + msg);
	}

	public static final void postInfo(final String TAG, String msg) {
		Log.i(TAG, CURL_POST + msg);
	}

	public static final void postDebug(final String TAG, String msg) {
		Log.d(TAG, CURL_POST + msg);
	}

	public static final void postError(final String TAG, String msg) {
		Log.e(TAG, CURL_POST + msg);
	}
}
