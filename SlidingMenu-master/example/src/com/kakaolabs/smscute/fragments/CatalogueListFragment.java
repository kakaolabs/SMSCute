package com.kakaolabs.smscute.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.kakaolabs.smscute.R;
import com.kakaolabs.smscute.adapter.MyExpandableListAdapter;
import com.kakaolabs.smscute.database.MySQLiteHelper;
import com.kakaolabs.smscute.database.table.Catalogue;
import com.kakaolabs.smscute.parser.CatalogueParser;
import com.kakaolabs.smscute.util.Constants;
import com.kakaolabs.smscute.util.CurlLogUtil;
import com.kakaolabs.smscute.util.SecureUtil;
import com.kakaolabs.smscute.util.TimeUtil;
import com.kakaolabs.smscute.util.VolleyHelper;
import com.smskute.android.volley.DefaultRetryPolicy;
import com.smskute.android.volley.Response;
import com.smskute.android.volley.VolleyError;
import com.smskute.android.volley.toolbox.JsonArrayRequest;

public class CatalogueListFragment extends Fragment {
	private MyExpandableListAdapter listAdapter;
	private ExpandableListView expListView;
	private List<Catalogue> listDataHeader;
	private HashMap<Catalogue, List<Catalogue>> listDataChild;
	private ArrayList<Catalogue> catalogues, cataloguesDB;
	private static final String TAG = "CatalogueListFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.catalogue_list_layout, null);
		// get the listview
		expListView = (ExpandableListView) view
				.findViewById(R.id.catalogue_list_expandable_listview);
		// preparing list data
		prepareListData();
		listAdapter = new MyExpandableListAdapter(this.getActivity(),
				listDataHeader, listDataChild);
		// setting list adapter
		expListView.setAdapter(listAdapter);
		return view;
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<Catalogue>();
		listDataChild = new HashMap<Catalogue, List<Catalogue>>();
		getCategoriesFromDatabase();
		getCategoriesInServer();
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * get catagories from database
	 * 
	 * @author dungnh8
	 */
	private void getCategoriesFromDatabase() {
		try {
			// get catalogues from DB
			cataloguesDB = MySQLiteHelper.getInstance(getActivity())
					.getAllCatalogues();
			drawCataloguesList();
		} catch (Exception e) {
			Log.e(TAG, "getCategoriesInDatabase", e);
		}
	}

	/**
	 * get api sig
	 * 
	 * @author dungnh8
	 * @param time
	 * @return
	 */
	private String getApiSig(long time) {
		ArrayList<String> params = new ArrayList<String>();
		params.add(Constants.API_KEY_PARAM);
		params.add(Constants.TIME_PARAM);
		ArrayList<String> values = new ArrayList<String>();
		values.add(Constants.API_KEY);
		values.add(String.valueOf(time));
		return SecureUtil.getApiSig(Constants.URL_CATEGORIES_ENDPOINT,
				Constants.API_SECRET, params, values);
	}

	/**
	 * get request link
	 * 
	 * @author dungnh8
	 * @return
	 */
	private String getRequestLink() {
		// create link
		long currentTime = TimeUtil.getCurrentTime();
		String apiSig = getApiSig(currentTime);
		ArrayList<String> params = new ArrayList<String>();
		params.add(Constants.API_KEY_PARAM);
		params.add(Constants.API_SIG_PARAM);
		params.add(Constants.TIME_PARAM);
		ArrayList<String> values = new ArrayList<String>();
		values.add(Constants.API_KEY);
		values.add(apiSig);
		values.add(String.valueOf(currentTime));
		String link = SecureUtil.getRequestLink(Constants.DOMAIN
				+ Constants.URL_CATEGORIES_ENDPOINT, params, values);
		return link;
	}

	/**
	 * get categories
	 * 
	 * @author dungnh8
	 */
	private void getCategoriesInServer() {
		try {
			final String link = getRequestLink();
			CurlLogUtil.getMessage(TAG, link);
			// get request
			JsonArrayRequest getRequest = new JsonArrayRequest(link,
					new Response.Listener<JSONArray>() {
						@Override
						public void onResponse(JSONArray response) {
							try {
								catalogues = CatalogueParser
										.getAllCatalogue(response);
								// if connect to server ok
								if (catalogues != null && catalogues.size() > 0) {
									// save catalogues to database
									new SaveCatalogueAsyncTask().execute();
								}
							} catch (Exception e) {
								Log.e(TAG, "getCategoriesInServer", e);
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e(TAG, "getCategoriesInServer", error);
						}
					});
			getRequest.setRetryPolicy(new DefaultRetryPolicy(
					Constants.VOLLEY_TIMEOUT, Constants.VOLLEY_MAX_NUM_RETRIES,
					Constants.VOLLEY_BACKOFF_MULTIPLER));
			VolleyHelper.getVolleyQueue(getActivity()).add(getRequest);
		} catch (Exception e) {
			Log.e(TAG, "getCategoriesInServer", e);
		}
	}

	/**
	 * Save catalogue to database
	 * 
	 * @author Daniel
	 * 
	 */
	private class SaveCatalogueAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			for (Catalogue catalogue : catalogues) {
				boolean isNew = true;
				// check that catalogue is exist in database
				for (Catalogue catalogueOfDB : cataloguesDB) {
					if (catalogue.getCatelogueID() == catalogueOfDB
							.getCatelogueID()) {
						isNew = false;
						break;
					}
				}
				if (isNew) { // if catalogue is not in database
					MySQLiteHelper.getInstance(
							CatalogueListFragment.this.getActivity())
							.createCatalogue(catalogue);
				}
			}
			return null;
		}
	}

	/**
	 * draw catalogues list
	 * 
	 * @author Daniel
	 * 
	 */
	private void drawCataloguesList() {
		try {
			// get parent catalogue
			for (int i = 0; i < cataloguesDB.size(); i++) {
				if (cataloguesDB.get(i).getParentCatalogueID() == 0) {
					listDataHeader.add(cataloguesDB.get(i));
				}
			}
			// get child catalogue
			for (int i = 0; i < listDataHeader.size(); i++) {
				Catalogue parent = listDataHeader.get(i);
				ArrayList<Catalogue> childs = new ArrayList<Catalogue>();
				for (int j = 0; j < cataloguesDB.size(); j++) {
					Catalogue child = cataloguesDB.get(j);
					if (child.getParentCatalogueID() == parent.getCatelogueID()) {
						childs.add(child);
					}
				}
				listDataChild.put(parent, childs);
			}
		} catch (Exception e) {
			Log.e(TAG, "drawCataloguesList", e);
		}
	}
}