package com.kakaolabs.smscute.listener;

import com.kakaolabs.smscute.database.table.SMS;

public interface SMSDetailListener {
	public void onFavoriteButtonChange(SMS sms);
}