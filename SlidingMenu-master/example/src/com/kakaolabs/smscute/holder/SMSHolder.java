package com.kakaolabs.smscute.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kakaolabs.smscute.R;
import com.kakaolabs.smscute.SMSDetailActivity;
import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.util.Constants;

public class SMSHolder extends AbsContentHolder {
	private TextView content;
	private Button send;
	private Button like;
	private Context mContext;
	private static SMSHolder holder;
	private SMS sms;
	private static final String TAG = "SMSHolder";

	public SMSHolder(Context mContext) {
		this.mContext = mContext;
		holder = this;
	}

	@Override
	public void setElemnts(Object obj) {
		sms = (SMS) obj;
		content.setText(sms.getContent());
	}

	@Override
	public void initHolder(ViewGroup parent, View rowView, int position,
			LayoutInflater layoutInflater) {
		rowView = layoutInflater
				.inflate(R.layout.sms_row_layout, parent, false);
		content = (TextView) rowView.findViewById(R.id.sms_row_content);
		send = (Button) rowView.findViewById(R.id.sms_row_send);
		like = (Button) rowView.findViewById(R.id.sms_row_like);
		setListener(rowView);
		rowView.setTag(holder);
		setConvertView(rowView);
	}

	private void setListener(View rowView) {
		setRowListener(rowView);
		setSendListener();
		setLikeListener();
	}

	private void setRowListener(View rowView) {
		rowView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					Intent intent = new Intent(mContext,
							SMSDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable(Constants.SMS, sms);
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				} catch (Exception e) {
					Log.i(TAG, "initHolder", e);
				}
			}
		});
	}

	private void setSendListener() {
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
	}

	private void setLikeListener() {
		like.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
	}
}