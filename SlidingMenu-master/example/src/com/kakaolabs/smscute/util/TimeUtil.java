package com.kakaolabs.smscute.util;

import java.util.Date;

public class TimeUtil {

	/**
	 * get current time in long value
	 * 
	 * @author dungnh8
	 * @return
	 */
	public static final long getCurrentTime() {
		Date date = new Date();
		return date.getTime();
	}
}