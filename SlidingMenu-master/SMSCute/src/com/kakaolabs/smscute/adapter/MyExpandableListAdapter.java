package com.kakaolabs.smscute.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.kakaolabs.smscute.ListSMSActivity;
import com.kakaolabs.smscute.R;
import com.kakaolabs.smscute.database.table.Catalogue;
import com.kakaolabs.smscute.util.Constants;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<Catalogue> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<Catalogue, List<Catalogue>> _listDataChild;
	private static final String TAG = "MyExpandableListAdapter";

	public MyExpandableListAdapter(Context context,
			List<Catalogue> listDataHeader,
			HashMap<Catalogue, List<Catalogue>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final Catalogue catalogue = (Catalogue) getChild(groupPosition,
				childPosition);
		final String childText = catalogue.getName();

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.child_catalogue_layout, null);
		}

		TextView childCatalogueName = (TextView) convertView
				.findViewById(R.id.child_catalogue_name);
		childCatalogueName.setText(childText);
		// onClick listener
		onRowClickListener(convertView, catalogue);

		return convertView;
	}

	/**
	 * @author dungnh8
	 * @param view
	 */
	private void onRowClickListener(View view, final Catalogue catalogue) {
		// check catalogue has child
		// start sms list activity
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(_context, ListSMSActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable(Constants.CATALOGUE, catalogue);
					intent.putExtras(bundle);
					_context.startActivity(intent);
				} catch (Exception e) {
					Log.e(TAG, "onRowClickListener", e);
				}
			}
		});
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		Catalogue catalogue = (Catalogue) getGroup(groupPosition);
		String headerTitle = catalogue.getName();
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}
		TextView group_name = (TextView) convertView
				.findViewById(R.id.list_group_name);
		group_name.setTypeface(null, Typeface.BOLD);
		group_name.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}