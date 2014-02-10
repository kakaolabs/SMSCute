package com.kakaolabs.smscute.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.fragments.SMSDetailFragment;
import com.kakaolabs.smscute.util.Constants;
import com.kakaolabs.smscute.viewpagerindicator.IconPagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter implements
		IconPagerAdapter {
	private ArrayList<SMS> smsList;

	public FragmentAdapter(FragmentManager fm, Context mContext,
			ArrayList<SMS> smsList) {
		super(fm);
		this.smsList = smsList;
	}

	@Override
	public int getIconResId(int index) {
		return 0;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment;
		fragment = new SMSDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(Constants.SMS, smsList.get(position));
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int getCount() {
		return smsList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "";
	}
}