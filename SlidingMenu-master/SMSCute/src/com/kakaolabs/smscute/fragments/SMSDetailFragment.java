package com.kakaolabs.smscute.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kakaolabs.smscute.R;
import com.kakaolabs.smscute.database.MySQLiteHelper;
import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.util.Constants;

public class SMSDetailFragment extends Fragment {
	private TextView content;
	private SMS sms;
	private Button sendButton, likeButton;
	private static final String TAG = "SMSDetailFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.sms_content_layout, null);
		setComponentView(v);
		setArguments();
		drawData();
		setListener();
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
			sendButton = (Button) view.findViewById(R.id.sms_content_send_sms);
			likeButton = (Button) view.findViewById(R.id.sms_content_like_sms);
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
			sms = MySQLiteHelper.getInstance(getActivity()).getSMSByID(
					sms.getId());
			// set favorite button
			setFavoriteButton();
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
			content.setText(sms.getContent());
		} catch (Exception e) {
			Log.e(TAG, "drawData", e);
		}
	}

	private void setListener() {
		setFavoriteListener();
		setSendListener();
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
				SMS[] smsParams = { sms };
				new UpdateUsedSMSAsyncTask().execute(smsParams);
				// send sms
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.putExtra("sms_body", sms.getContent());
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
		likeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean isFavorited = !sms.isFavorited();
				sms.setFavorited(isFavorited);
				// set button to be favorite
				setFavoriteButton();
				// update to database
				SMS[] smsParams = { sms };
				new UpdateFavoritedSMSAsyncTask().execute(smsParams);
			}
		});
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
			MySQLiteHelper.getInstance(SMSDetailFragment.this.getActivity())
					.updateSMS(currentSMS);
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
			MySQLiteHelper.getInstance(SMSDetailFragment.this.getActivity())
					.updateSMS(currentSMS);
			return null;
		}
	}

	/**
	 * set favorite button
	 * 
	 * @author dungnh8
	 */
	private void setFavoriteButton() {
		if (sms.isFavorited()) {
			likeButton.setBackgroundResource(R.drawable.rounded_red_rectangle);
		} else {
			likeButton
					.setBackgroundResource(R.drawable.rounded_green_rectangle);
		}
	}
}