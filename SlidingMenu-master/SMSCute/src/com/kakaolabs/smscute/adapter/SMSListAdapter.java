package com.kakaolabs.smscute.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.holder.SMSHolder;

public class SMSListAdapter extends BaseAdapter {
	private List<SMS> data;
	private Context mContext;
	private LayoutInflater inflater;
	private String catalogueTitle;
	private static final String TAG = "SMSAdapter";

	public SMSListAdapter(Context context, List<SMS> data, String catalogueTitle) {
		this.mContext = context;
		this.data = data;
		this.catalogueTitle = catalogueTitle;
		this.inflater = LayoutInflater.from(this.mContext);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public SMS getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final SMS sms = getItem(position);
		Log.i(TAG, position + " : " + sms.toString());
		SMSHolder holder;
		if (convertView == null) {
			holder = new SMSHolder(mContext, data, catalogueTitle);
			holder.initHolder(parent, convertView, position, inflater);
		} else {
			holder = (SMSHolder) convertView.getTag();
		}
		holder.setElemnts(sms);
		convertView = holder.getConvertView();
		return convertView;
	}
}