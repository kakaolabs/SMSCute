package com.kakaolabs.smscute.holder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakaolabs.smscute.R;
import com.kakaolabs.smscute.SMSDetailActivity;
import com.kakaolabs.smscute.database.MySQLiteHelper;
import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.util.Constants;

public class SMSHolder extends AbsContentHolder {
	private TextView content;
	private ImageView send;
	private ImageView like;
	private Context mContext;
	private static SMSHolder holder;
	private SMS sms;
	private int position;
	private ArrayList<SMS> smsList;
	private String catalogueTitle;
	private static final String TAG = "SMSHolder";

	public SMSHolder(Context mContext, int position, List<SMS> smsList,
			String catalogueTitle) {
		this.mContext = mContext;
		this.position = position;
		this.smsList = (ArrayList<SMS>) smsList;
		this.catalogueTitle = catalogueTitle;
		holder = this;
	}

	@Override
	public void setElemnts(Object obj) {
		sms = (SMS) obj;
		content.setText(sms.getContent());
		setImageFavorite();
	}

	@Override
	public void initHolder(ViewGroup parent, View rowView, int position,
			LayoutInflater layoutInflater) {
		rowView = layoutInflater
				.inflate(R.layout.sms_row_layout, parent, false);
		content = (TextView) rowView.findViewById(R.id.sms_row_content);
		send = (ImageView) rowView.findViewById(R.id.sms_row_send);
		like = (ImageView) rowView.findViewById(R.id.sms_row_like);
		setListener();
		rowView.setTag(holder);
		setConvertView(rowView);
	}

	private void setListener() {
		setRowListener();
		setSendListener();
		setLikeListener();
	}

	/**
	 * row click listener
	 * 
	 * @author dungnh8
	 */
	private void setRowListener() {
		content.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					Intent intent = new Intent(mContext,
							SMSDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable(Constants.SMS_LIST, smsList);
					bundle.putInt(Constants.SMS_POSITION, position);
					bundle.putString(Constants.CATALOGUE_TITLE, catalogueTitle);
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				} catch (Exception e) {
					Log.i(TAG, "initHolder", e);
				}
			}
		});
	}

	/**
	 * send sms
	 * 
	 * @author dungnh8
	 */
	private void setSendListener() {
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					// set sms to be used
					sms.setUsed(true);
					MySQLiteHelper.getInstance(mContext).updateSMS(sms);
					// send sms
					Intent i = new Intent(android.content.Intent.ACTION_VIEW);
					i.putExtra("sms_body", sms.getContent());
					i.setType("vnd.android-dir/mms-sms");
					mContext.startActivity(i);
				} catch (Exception e) {
					Log.e(TAG, "setSendListener", e);
				}
			}
		});
	}

	/**
	 * set sms to be favorite
	 * 
	 * @author dungnh8
	 */
	private void setLikeListener() {
		like.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean isFavorited = !sms.isFavorited();
				sms.setFavorited(isFavorited);
				setImageFavorite();
				MySQLiteHelper.getInstance(mContext).updateSMS(sms);
			}
		});
	}

	/**
	 * set image to be favorite or not
	 * 
	 * @author dungnh8
	 */
	private void setImageFavorite() {
		if (sms.isFavorited()) {
			like.setImageResource(R.drawable.ic_favorite_check);
		} else {
			like.setImageResource(R.drawable.ic_favorite);
		}
	}
}