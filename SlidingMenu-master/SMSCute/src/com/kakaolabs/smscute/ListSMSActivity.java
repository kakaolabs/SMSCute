package com.kakaolabs.smscute;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.kakaolabs.smscute.adapter.SMSListAdapter;
import com.kakaolabs.smscute.database.MySQLiteHelper;
import com.kakaolabs.smscute.database.table.Catalogue;
import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.parser.SMSParser;
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
	private ArrayList<SMS> smsList;
	private SwipeListView swipeListView;
	private SMSListAdapter smsAdapter;
	private ImageView backButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			getDataFromBundle();
			getSMSInDatabase();
			getSMSInServer(0);
			setContentView(R.layout.list_sms_layout);
			setComponentView();
			setListener();
		} catch (Exception e) {
			Log.e(TAG, "onCreate", e);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * set component view
	 * 
	 * @author dungnh8
	 */
	private void setComponentView() {
		swipeListView = (SwipeListView) findViewById(R.id.list_sms_swipe_listview);
		TextView title = (TextView) findViewById(R.id.header_title);
		title.setText(catalogue.getName());
		backButton = (ImageView) findViewById(R.id.header_back_button);
	}

	/**
	 * set listener
	 * 
	 * @author dungnh8
	 */
	private void setListener() {
		setBackListener();
	}

	/**
	 * set back listener
	 * 
	 * @author dungnh8
	 */
	private void setBackListener() {
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * get sms in database
	 * 
	 * @author dungnh8
	 */
	private void getSMSInDatabase() {
		smsList = MySQLiteHelper.getInstance(this).getAllSMS();
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
			Log.i(TAG, catalogue.toString());
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
								smsList = SMSParser.getAllSMS(response,
										catalogue.getCatelogueID());
								new SaveSMSAsyncTask().execute();
								drawSMSList();
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
		params.add(Constants.TIME_PARAM);
		params.add(Constants.API_SIG_PARAM);
		// params.add(Constants.SIZE_PARAM);
		// params.add(Constants.OFFSET_PARAM);
		ArrayList<String> values = new ArrayList<String>();
		values.add(Constants.API_KEY);
		values.add(String.valueOf(currentTime));
		values.add(apiSig);
		// values.add(Constants.SIZE);
		// values.add(String.valueOf(offset));
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
		// params.add(Constants.SIZE_PARAM);
		// params.add(Constants.OFFSET_PARAM);
		ArrayList<String> values = new ArrayList<String>();
		values.add(Constants.API_KEY);
		values.add(String.valueOf(time));
		// values.add(Constants.SIZE);
		// values.add(String.valueOf(offset));
		return SecureUtil.getApiSig(
				Constants.URL_SMS_ENDPOINT + catalogue.getCatelogueID() + "/",
				Constants.API_SECRET, params, values);
	}

	/**
	 * Save catalogue to database
	 * 
	 * @author Daniel
	 * 
	 */
	private class SaveSMSAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			for (SMS sms : smsList) {
				MySQLiteHelper.getInstance(ListSMSActivity.this).createSMS(sms);
			}
			return null;
		}
	}

	/**
	 * draw sms list
	 * 
	 * @author dungnh8
	 */
	private void drawSMSList() {
		try {
			smsAdapter = new SMSListAdapter(this, smsList, catalogue.getName());
			swipeListView.setAdapter(smsAdapter);
		} catch (Exception e) {
			Log.e(TAG, "drawSMSList", e);
		}
	}
}