package com.kakaolabs.smscute.util;

import android.content.Context;
import android.util.Log;

import com.smskute.android.volley.RequestQueue;
import com.smskute.android.volley.toolbox.Volley;

public class VolleyHelper {
	private static final String TAG = "VolleyHelper";
	private static RequestQueue queue; // singleton volley queue

	/**
	 * @author Daniel
	 * @param context
	 * @return
	 */
	public static RequestQueue getVolleyQueue(Context context) {
		try {
			if (queue == null) {
				queue = Volley.newRequestQueue(context);
			}
			return queue;
		} catch (Exception e) {
			Log.e(TAG, "getVolleyQueue", e);
			return null;
		}
	}
}
