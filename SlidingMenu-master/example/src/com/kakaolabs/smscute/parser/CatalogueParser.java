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

	/**
	 * get all catalogue from json array
	 * 
	 * @author Daniel
	 * @param jsonArray
	 * @return
	 */
	public static ArrayList<Catalogue> getAllCatalogue(JSONArray jsonArray) {
		try {
			ArrayList<Catalogue> catalogues = new ArrayList<Catalogue>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				ArrayList<Catalogue> list = getCatalogueFromObject(jsonObject);
				if (list != null && list.size() > 0) {
					catalogues.addAll(list);
				}
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
	private static ArrayList<Catalogue> getCatalogueFromObject(
			JSONObject jsonObject) {
		try {
			ArrayList<Catalogue> catalogues = new ArrayList<Catalogue>();
			// get parent
			Catalogue parent = getOneCatalogue(jsonObject);
			if (parent != null) {
				catalogues.add(parent);
			}
			// get child
			int type = Integer.parseInt(jsonObject.getString(TYPE));
			if (type == 0) {
				JSONArray childs = jsonObject.getJSONArray(DATA);
				for (int i = 0; i < childs.length(); i++) {
					Catalogue child = getOneCatalogue((JSONObject) childs
							.get(i));
					child.setParentCatalogueID(parent.getCatelogueID());
					catalogues.add(child);
				}
			}
			return catalogues;
		} catch (Exception e) {
			Log.e(TAG, "getCatalogueFromObject", e);
			return null;
		}
	}

	/**
	 * get one catalogue from json object
	 * 
	 * @author Daniel
	 * @param jsonObject
	 * @return
	 */
	private static Catalogue getOneCatalogue(JSONObject jsonObject) {
		try {
			Catalogue catalogue = new Catalogue();
			int id = Integer.parseInt(jsonObject.getString(ID));
			String name = jsonObject.getString(NAME);
			catalogue.setCatelogueID(id);
			catalogue.setName(name);
			catalogue.setSearched_name(StringUtil.utf8ToAscii(name));
			return catalogue;
		} catch (Exception e) {
			Log.e(TAG, "getOneCatalogue", e);
			return null;
		}
	}
}