package com.kakaolabs.smscute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.util.Constants;

public class SMSDetailActivity extends FragmentActivity {
	private static final String TAG = "SMSDetailActivity";
	private SMS sms;
	private TextView content;
	private ImageView backButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			getDataFromBundle();
			setContentView(R.layout.sms_detail_layout);
			setComponentView();
			setListener();
			drawSMS();
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
		backButton = (ImageView) findViewById(R.id.header_back_button);
		content = (TextView) findViewById(R.id.sms_detail_content);
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
			sms = (SMS) intent.getExtras().get(Constants.SMS);
			Log.i(TAG, sms.toString());
		} catch (Exception e) {
			Log.e(TAG, "getDataFromBundle", e);
		}
	}

	/**
	 * draw sms
	 * 
	 * @author dungnh8
	 */
	private void drawSMS() {
		try {
			content.setText(sms.getContent());
		} catch (Exception e) {
			Log.e(TAG, "drawSMS", e);
		}
	}
}