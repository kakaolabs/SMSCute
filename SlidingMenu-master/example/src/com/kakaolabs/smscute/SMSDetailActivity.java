package com.kakaolabs.smscute;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kakaolabs.smscute.adapter.FragmentAdapter;
import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.util.Constants;

public class SMSDetailActivity extends FragmentActivity {
	private static final String TAG = "SMSDetailActivity";
	private SMS sms;
	private ArrayList<SMS> smsList;
	private int position;
	private FragmentAdapter mAdapter;
	private ViewPager mPager;
	private ImageView backButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			getDataFromBundle();
			setContentView(R.layout.sms_detail_layout);
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
		// view pager
		mAdapter = new FragmentAdapter(getSupportFragmentManager(), this,
				smsList);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setCurrentItem(position);
		// button
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
			sms = smsList.get(position);
			Log.i(TAG, sms.toString());
		} catch (Exception e) {
			Log.e(TAG, "getDataFromBundle", e);
		}
	}
}