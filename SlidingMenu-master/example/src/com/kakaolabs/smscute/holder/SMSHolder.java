package com.kakaolabs.smscute.holder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kakaolabs.smscute.R;
import com.kakaolabs.smscute.database.table.SMS;

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
		setListener();
		rowView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
				} catch (Exception e) {
					Log.i(TAG, "initHolder", e);
				}
			}
		});
		rowView.setTag(holder);
		setConvertView(rowView);
	}

	private void setListener() {
		setSendListener();
		setLikeListener();
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