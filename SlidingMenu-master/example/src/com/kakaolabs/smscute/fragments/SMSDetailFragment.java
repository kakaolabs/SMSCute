package com.kakaolabs.smscute.fragments;

import com.kakaolabs.smscute.R;
import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.util.Constants;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SMSDetailFragment extends Fragment {
	private TextView content;
	private SMS sms;
	private static final String TAG = "SMSDetailFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.sms_content_layout, null);
		setComponentView(v);
		setArguments();
		drawData();
		return v;
	}

	/**
	 * setComponentView
	 * 
	 * @author dungnh8
	 */
	private void setComponentView(View view) {
		try {
			content = (TextView) view.findViewById(R.id.sms_content_textview);
		} catch (Exception e) {
			Log.e(TAG, "setComponentView", e);
		}
	}

	/**
	 * set arguments
	 * 
	 * @author dungnh8
	 */
	private void setArguments() {
		try {
			sms = (SMS) getArguments().getSerializable(Constants.SMS);
			Log.i(TAG, sms.toString());
		} catch (Exception e) {
			Log.e(TAG, "setArguments", e);
		}
	}

	/**
	 * draw data
	 * 
	 * @author dungnh8
	 */
	private void drawData() {
		try {
			String contentText = "<p style='line-height:200%;'>"
					+ sms.getContent() + "<p/>";
			Log.i(TAG, contentText);
			content.setText(Html.fromHtml(contentText));
		} catch (Exception e) {
			Log.e(TAG, "drawData", e);
		}
	}
}