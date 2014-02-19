package com.kakaolabs.smscute;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.kakaolabs.smscute.fragments.CatalogueListFragment;
import com.kakaolabs.smscute.fragments.SlidingMenuFragment;

public class MainActivity extends SlidingFragmentActivity {

	protected Fragment mFrag;
	private static final String TAG = "BaseActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setTitle(R.string.app_name);

			// set the Above View - Content view
			setContentView(R.layout.content_frame);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, new CatalogueListFragment())
					.commit();

			// set the Behind View - Left menu view
			setBehindContentView(R.layout.menu_frame);
			if (savedInstanceState == null) {
				FragmentTransaction t = this.getSupportFragmentManager()
						.beginTransaction();
				mFrag = new SlidingMenuFragment();
				t.replace(R.id.menu_frame, mFrag);
				t.commit();
			} else {
				mFrag = (ListFragment) this.getSupportFragmentManager()
						.findFragmentById(R.id.menu_frame);
			}

			// customize the SlidingMenu
			SlidingMenu sm = getSlidingMenu();
			sm.setShadowWidthRes(R.dimen.shadow_width);
			sm.setShadowDrawable(R.drawable.shadow);
			sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
			sm.setFadeDegree(0.35f);
			sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		} catch (Exception e) {
			Log.e(TAG, "onCreate", e);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
}