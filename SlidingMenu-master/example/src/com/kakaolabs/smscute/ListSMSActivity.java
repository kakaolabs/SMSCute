package com.kakaolabs.smscute;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.kakaolabs.smscute.database.table.Catalogue;
import com.kakaolabs.smscute.util.Constants;
import com.kakaolabs.smscute.util.CurlLogUtil;
import com.kakaolabs.smscute.util.SecureUtil;
import com.kakaolabs.smscute.util.TimeUtil;
import com.kakaolabs.smscute.util.VolleyHelper;
import com.smskute.android.volley.DefaultRetryPolicy;
import com.smskute.android.volley.Response;
import com.smskute.android.volley.VolleyError;
import com.smskute.android.volley.toolbox.JsonArrayRequest;

public class ListSMSActivity extends FragmentActivity {
	private static final String TAG = "ListSMSActivity";
	private Catalogue catalogue;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			getDataFromBundle();
			getSMSInServer(0);
			setContentView(R.layout.list_sms_layout);
		} catch (Exception e) {
			Log.e(TAG, "onCreate", e);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * get data from bundle
	 * 
	 * @author dungnh8
	 */
	private void getDataFromBundle() {
		try {
			Intent intent = getIntent();
			catalogue = (Catalogue) intent.getExtras().get(Constants.CATALOGUE);
		} catch (Exception e) {
			Log.e(TAG, "getDataFromBundle", e);
		}
	}

	/**
	 * get sms in category from server
	 * 
	 * @author dungnh8
	 */
	private void getSMSInServer(int offset) {
		try {
			final String link = getRequestLink(offset);
			CurlLogUtil.getMessage(TAG, link);
			// get request
			JsonArrayRequest getRequest = new JsonArrayRequest(link,
					new Response.Listener<JSONArray>() {
						@Override
						public void onResponse(JSONArray response) {
							try {
								System.out.println(response);
							} catch (Exception e) {
								Log.e(TAG, "getSMSInServer", e);
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e(TAG, "getSMSInServer", error);
						}
					});
			getRequest.setRetryPolicy(new DefaultRetryPolicy(
					Constants.VOLLEY_TIMEOUT, Constants.VOLLEY_MAX_NUM_RETRIES,
					Constants.VOLLEY_BACKOFF_MULTIPLER));
			VolleyHelper.getVolleyQueue(this).add(getRequest);
		} catch (Exception e) {
			Log.e(TAG, "getSMSInServer", e);
		}
	}

	/**
	 * get request link
	 * 
	 * @author dungnh8
	 * @return
	 */
	private String getRequestLink(int offset) {
		// create link
		long currentTime = TimeUtil.getCurrentTime();
		String apiSig = getApiSig(currentTime, offset);
		ArrayList<String> params = new ArrayList<String>();
		params.add(Constants.API_KEY_PARAM);
		params.add(Constants.API_SIG_PARAM);
		params.add(Constants.TIME_PARAM);
		params.add(Constants.SIZE_PARAM);
		params.add(Constants.OFFSET_PARAM);
		ArrayList<String> values = new ArrayList<String>();
		values.add(Constants.API_KEY);
		values.add(apiSig);
		values.add(String.valueOf(currentTime));
		values.add(Constants.SIZE);
		values.add(String.valueOf(offset));
		String link = SecureUtil.getRequestLink(
				Constants.DOMAIN + Constants.URL_SMS_ENDPOINT
						+ catalogue.getCatelogueID() + "/", params, values);
		return link;
	}

	/**
	 * get api sig
	 * 
	 * @author dungnh8
	 * @param time
	 * @return
	 */
	private String getApiSig(long time, int offset) {
		ArrayList<String> params = new ArrayList<String>();
		params.add(Constants.API_KEY_PARAM);
		params.add(Constants.TIME_PARAM);
		params.add(Constants.SIZE_PARAM);
		params.add(Constants.OFFSET_PARAM);
		ArrayList<String> values = new ArrayList<String>();
		values.add(Constants.API_KEY);
		values.add(String.valueOf(time));
		values.add(Constants.SIZE);
		values.add(String.valueOf(offset));
		return SecureUtil.getApiSig(
				Constants.URL_SMS_ENDPOINT + catalogue.getCatelogueID() + "/",
				Constants.API_SECRET, params, values);
	}
}