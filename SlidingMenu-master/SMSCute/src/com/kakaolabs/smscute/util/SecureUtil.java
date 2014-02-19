package com.kakaolabs.smscute.util;

import java.security.MessageDigest;
import java.util.ArrayList;

import android.util.Log;

public class SecureUtil {
	private static final String TAG = "SecureUtil";

	/**
	 * get api_sig
	 * 
	 * @author dungnh8
	 * @param urlEndoint
	 * @param apiSecret
	 * @param params
	 * @param values
	 * @return api_sig
	 */
	public static String getApiSig(String urlEndoint, String apiSecret,
			ArrayList<String> params, ArrayList<String> values) {
		String request_string = urlEndoint;
		for (int i = 0; i < params.size(); i++) {
			request_string += params.get(i) + "=" + values.get(i);
		}
		request_string += apiSecret;
		return sha1(request_string);
	}

	/**
	 * encrypt request link to return api_sig
	 * 
	 * @author dungnh8
	 * @param input
	 * @return
	 */
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

	/**
	 * get request link
	 * 
	 * @author dungnh8
	 * @param urlEndpoint
	 * @param params
	 * @param values
	 * @return
	 */
	public static final String getRequestLink(String urlEndpoint,
			ArrayList<String> params, ArrayList<String> values) {
		String link = urlEndpoint + "?";
		for (int i = 0; i < params.size(); i++) {
			String tmp = params.get(i) + "=" + values.get(i);
			if (i < params.size() - 1) {
				link += tmp + "&";
			} else {
				link += tmp;
			}
		}
		return link;
	}
}