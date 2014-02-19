package com.kakaolabs.smscute;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakaolabs.smscute.adapter.SMSFragmentAdapter;
import com.kakaolabs.smscute.database.MySQLiteHelper;
import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.helper.DrawHelper;
import com.kakaolabs.smscute.listener.SMSDetailListener;
import com.kakaolabs.smscute.util.Constants;

public class SMSDetailActivity extends FragmentActivity implements
		SMSDetailListener {
	private static final String TAG = "SMSDetailActivity";
	private SMS sms;
	private ArrayList<SMS> smsList;
	private int position;
	private String catalogueTitle;
	private SMSFragmentAdapter mAdapter;
	private ViewPager mPager;
	private ImageView backButton, sendButton, favoriteButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			getDataFromBundle();
			setContentView(R.layout.sms_detail_layout);
			setComponentView();
			setListener();
			setImageFavorite(sms);
		} catch (Exception e) {
			Log.e(TAG, "onCreate", e);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		DrawHelper.addSMSDetailListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		DrawHelper.removeSMSDetailListener(this);
	}

	/**
	 * set component view
	 * 
	 * @author dungnh8
	 */
	private void setComponentView() {
		// view pager
		mAdapter = new SMSFragmentAdapter(getSupportFragmentManager(), this,
				smsList);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setCurrentItem(position);
		// button
		backButton = (ImageView) findViewById(R.id.header_back_button);
		sendButton = (ImageView) findViewById(R.id.header_send_button);
		favoriteButton = (ImageView) findViewById(R.id.header_favorite_button);
		// header
		TextView title = (TextView) findViewById(R.id.header_title);
		title.setText(catalogueTitle);
		title.setVisibility(View.GONE);
	}

	/**
	 * set listener
	 * 
	 * @author dungnh8
	 */
	private void setListener() {
		setBackListener();
		setSendListener();
		setFavoriteListener();
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
	 * set send listener
	 * 
	 * @author dungnh8
	 */
	private void setSendListener() {
		sendButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int currentPosition = mPager.getCurrentItem();
				SMS currentSMS = smsList.get(currentPosition);
				Log.i(TAG, currentSMS.toString());
				// set sms to be used
				SMS[] smsParams = { currentSMS };
				new UpdateUsedSMSAsyncTask().execute(smsParams);
				// send sms
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.putExtra("sms_body", currentSMS.getContent());
				intent.setType("vnd.android-dir/mms-sms");
				startActivity(intent);
			}
		});
	}

	/**
	 * set sms to be favorite
	 * 
	 * @author dungnh8
	 */
	private void setFavoriteListener() {
		favoriteButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int currentPosition = mPager.getCurrentItem();
				SMS currentSMS = smsList.get(currentPosition);
				boolean isFavorited = !currentSMS.isFavorited();
				currentSMS.setFavorited(isFavorited);
				setImageFavorite(currentSMS);
				SMS[] smsParams = { currentSMS };
				new UpdateFavoritedSMSAsyncTask().execute(smsParams);
			}
		});
	}

	/**
	 * set image to be favorite or not
	 * 
	 * @author dungnh8
	 */
	private void setImageFavorite(SMS currentSMS) {
		if (currentSMS.isFavorited()) {
			favoriteButton.setImageResource(R.drawable.ic_favorite_check);
		} else {
			favoriteButton.setImageResource(R.drawable.ic_favorite);
		}
	}

	/**
	 * get data from bundle
	 * 
	 * @author dungnh8
	 */
	private void getDataFromBundle() {
		try {
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			smsList = (ArrayList<SMS>) bundle.get(Constants.SMS_LIST);
			position = bundle.getInt(Constants.SMS_POSITION);
			catalogueTitle = bundle.getString(Constants.CATALOGUE_TITLE);
			sms = smsList.get(position);
			Log.i(TAG, sms.toString());
		} catch (Exception e) {
			Log.e(TAG, "getDataFromBundle", e);
		}
	}

	/**
	 * update this sms to be used
	 * 
	 * @author dungnh8
	 * 
	 */
	private class UpdateUsedSMSAsyncTask extends AsyncTask<SMS, Void, Void> {
		@Override
		protected Void doInBackground(SMS... params) {
			SMS currentSMS = params[0];
			currentSMS.setUsed(true);
			MySQLiteHelper.getInstance(SMSDetailActivity.this).updateSMS(
					currentSMS);
			return null;
		}
	}

	/**
	 * update this sms to be used
	 * 
	 * @author dungnh8
	 * 
	 */
	private class UpdateFavoritedSMSAsyncTask extends
			AsyncTask<SMS, Void, Void> {
		@Override
		protected Void doInBackground(SMS... params) {
			SMS currentSMS = params[0];
			Log.i(TAG, currentSMS.toString());
			MySQLiteHelper.getInstance(SMSDetailActivity.this).updateSMS(
					currentSMS);
			return null;
		}
	}

	@Override
	public void onFavoriteButtonChange(SMS sms) {
		setImageFavorite(sms);
	}
}