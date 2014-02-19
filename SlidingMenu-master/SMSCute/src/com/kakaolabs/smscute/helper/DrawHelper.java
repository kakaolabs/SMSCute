package com.kakaolabs.smscute.helper;

import java.util.ArrayList;

import com.kakaolabs.smscute.database.table.SMS;
import com.kakaolabs.smscute.listener.SMSDetailListener;

public class DrawHelper {
	public static ArrayList<SMSDetailListener> smsDetailListeners = new ArrayList<SMSDetailListener>();

	public static void addSMSDetailListener(SMSDetailListener listener) {
		smsDetailListeners.add(listener);
	}

	public static void removeSMSDetailListener(SMSDetailListener listener) {
		if (smsDetailListeners.contains(listener)) {
			smsDetailListeners.remove(listener);
		}
	}

	public static void drawFavoriteImageButton(SMS sms) {
		for (SMSDetailListener listener : smsDetailListeners) {
			listener.onFavoriteButtonChange(sms);
		}
	}
}