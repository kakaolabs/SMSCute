package com.kakaolabs.smscute.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.kakaolabs.smscute.database.table.Catalogue;
import com.kakaolabs.smscute.util.StringUtil;

public class CatalogueParser {
	private static final String TAG = "CatalogueParser";
	private static final String NAME = "name";
	private static final String TYPE = "type";
	private static final String ID = "id";
	private static final String DATA = "data";

	public static ArrayList<Catalogue> getAllCatalogue(JSONArray jsonArray) {
		try {
			ArrayList<Catalogue> catalogues = new ArrayList<Catalogue>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				Catalogue catalogue = getCatalogueFromObject(jsonObject);
				catalogues.add(catalogue);
			}
			return catalogues;
		} catch (Exception e) {
			Log.e(TAG, "getAllCatalogue", e);
			return null;
		}
	}

	/**
	 * get catalogue from object
	 * 
	 * @author dungnh8
	 * @param jsonObject
	 * @return
	 */
	private static Catalogue getCatalogueFromObject(JSONObject jsonObject) {
		try {
			Catalogue catalogue = new Catalogue();
			int id = Integer.parseInt(jsonObject.getString(ID));
			int type = Integer.parseInt(jsonObject.getString(TYPE));
			String name = jsonObject.getString(NAME);
			if (type == 0) {
				jsonObject.getJSONArray(DATA);
			} else {
				catalogue.setId(id);
				catalogue.setName(name);
				catalogue.setSearched_name(StringUtil.removeAccent(name));
			}
			return catalogue;
		} catch (Exception e) {
			Log.e(TAG, "getCatalogueFromObject", e);
			return null;
		}
	}
}