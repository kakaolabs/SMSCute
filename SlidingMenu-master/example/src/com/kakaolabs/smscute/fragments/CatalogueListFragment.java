package com.kakaolabs.smscute.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kakaolabs.smscute.R;
import com.kakaolabs.smscute.adapter.MyExpandableListAdapter;
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
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
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
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		getCategories();
		// // Adding child data
		// listDataHeader.add("Top 250");
		// listDataHeader.add("Now Showing");
		// listDataHeader.add("Coming Soon..");
		//
		// // Adding child data
		// List<String> top250 = new ArrayList<String>();
		// top250.add("The Shawshank Redemption");
		// top250.add("The Godfather");
		// top250.add("The Godfather: Part II");
		// top250.add("Pulp Fiction");
		// top250.add("The Good, the Bad and the Ugly");
		// top250.add("The Dark Knight");
		// top250.add("12 Angry Men");
		//
		// List<String> nowShowing = new ArrayList<String>();
		// nowShowing.add("The Conjuring");
		// nowShowing.add("Despicable Me 2");
		// nowShowing.add("Turbo");
		// nowShowing.add("Grown Ups 2");
		// nowShowing.add("Red 2");
		// nowShowing.add("The Wolverine");
		//
		// List<String> comingSoon = new ArrayList<String>();
		// comingSoon.add("2 Guns");
		// comingSoon.add("The Smurfs 2");
		// comingSoon.add("The Spectacular Now");
		// comingSoon.add("The Canyons");
		// comingSoon.add("Europa Report");
		//
		// listDataChild.put(listDataHeader.get(0), top250); // Header, Child
		// data
		// listDataChild.put(listDataHeader.get(1), nowShowing);
		// listDataChild.put(listDataHeader.get(2), comingSoon);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
	private void getCategories() {
		try {
			final String link = getRequestLink();
			CurlLogUtil.getMessage(TAG, link);
			// get request
			JsonArrayRequest getRequest = new JsonArrayRequest(link,
					new Response.Listener<JSONArray>() {
						@Override
						public void onResponse(JSONArray response) {
							try {
								ArrayList<Catalogue> catalogues = CatalogueParser
										.getAllCatalogue(response);
								for (int i = 0; i < catalogues.size(); i++) {
									System.out.println(catalogues.get(i));
								}
							} catch (Exception e) {
								Log.e(TAG, "getCategories", e);
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e(TAG, "getCategories", error);
						}
					});
			getRequest.setRetryPolicy(new DefaultRetryPolicy(
					Constants.VOLLEY_TIMEOUT, Constants.VOLLEY_MAX_NUM_RETRIES,
					Constants.VOLLEY_BACKOFF_MULTIPLER));
			VolleyHelper.getVolleyQueue(getActivity()).add(getRequest);
		} catch (Exception e) {
			Log.e(TAG, "crawlerOverview", e);
		}
	}
}