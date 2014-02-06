package com.kakaolabs.smscute.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.util.StringUtil;

public class SMSParser {
	private static final String TAG = "SMSParser";
	private static final String CONTENT = "content";
	private static final String VOTES = "votes";
	private static final String ID = "id";
	private static final String INDEX = "index";

	/**
	 * get all catalogue from json array
	 * 
	 * @author Daniel
	 * @param jsonArray
	 * @return
	 */
	public static ArrayList<SMS> getAllSMS(JSONArray jsonArray, int catalogueID) {
		try {
			ArrayList<SMS> smsList = new ArrayList<SMS>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				SMS sms = getOneSMS(jsonObject);
				if (sms != null) {
					sms.setCatalogueID(catalogueID);
					smsList.add(sms);
				}
			}
			return smsList;
		} catch (Exception e) {
			Log.e(TAG, "getAllSMS", e);
			return null;
		}
	}

	/**
	 * get one sms from json object
	 * 
	 * @author Daniel
	 * @param jsonObject
	 * @return
	 */
	private static SMS getOneSMS(JSONObject jsonObject) {
		try {
			SMS sms = new SMS();
			int id = Integer.parseInt(jsonObject.getString(ID));
			String content = jsonObject.getString(CONTENT);
			sms.setSmsId(id);
			sms.setContent(content);
			sms.setIndex(Integer.parseInt(jsonObject.getString(INDEX)));
			sms.setVotes(Integer.parseInt(jsonObject.getString(VOTES)));
			sms.setSearchedContent(StringUtil.utf8ToAscii(content));
			return sms;
		} catch (Exception e) {
			Log.e(TAG, "getOneSMS", e);
			return null;
		}
	}
}