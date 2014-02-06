package com.kakaolabs.smscute.database;

public class ConstantSQLite {
	// database info
	public static final String DATABASE_PATH = "/data/data/com.kakaolabs.smscute/databases/";
	public static final String DATABASE_NAME = "KAKAOLABS.db";
	public static final int DATABASE_VERSION = 1;
	// catalogue table
	public static final String CATALOGUE_TABLE = "catalogue";
	public static final String CATALOGUE_ID = "id";
	public static final String CATALOGUE_CATALOGUE_ID = "catalogue_id";
	public static final String CATALOGUE_NAME = "name";
	public static final String CATALOGUE_SEARCHED_NAME = "searched_name";
	public static final String CATALOGUE_PARENT_CATALOGUE_ID = "parent_catalogue_id";
	// catalogue
	public static final String CATALOGUE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ CATALOGUE_TABLE
			+ " ("
			+ CATALOGUE_ID
			+ " INTEGER PRIMARY KEY, "
			+ CATALOGUE_CATALOGUE_ID
			+ " INTEGER, "
			+ CATALOGUE_NAME
			+ " TEXT, "
			+ CATALOGUE_SEARCHED_NAME
			+ " TEXT, "
			+ CATALOGUE_PARENT_CATALOGUE_ID + " INTEGER)";
	public static final String CATALOGUE_DROP_TABLE = "DROP TABLE IF EXISTS "
			+ CATALOGUE_TABLE;
	public static final String CATALOGUE_SELECT_ALL = "SELECT * FROM "
			+ CATALOGUE_TABLE;

	// sms table
	public static final String SMS_TABLE = "sms";
	public static final String SMS_ID = "id";
	public static final String SMS_SMS_ID = "sms_id";
	public static final String SMS_CONTENT = "content";
	public static final String SMS_SEARCHED_CONTENT = "searched_content";
	public static final String SMS_VOTES = "votes";
	public static final String SMS_INDEX = "sms_index";
	public static final String SMS_IS_FAVORITED = "is_favorited";
	public static final String SMS_IS_USED = "is_used";
	public static final String SMS_CATALOGUE_ID = "catalogue_id";
	// sms
	public static final String SMS_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ SMS_TABLE + " (" + SMS_ID + " INTEGER PRIMARY KEY, " + SMS_SMS_ID
			+ " INTEGER, " + SMS_CONTENT + " TEXT, " + SMS_SEARCHED_CONTENT
			+ " TEXT, " + SMS_VOTES + " INTEGER, " + SMS_INDEX + " INTEGER, "
			+ SMS_IS_FAVORITED + " INTEGER, " + SMS_IS_USED + " INTEGER, "
			+ SMS_CATALOGUE_ID + " INTEGER)";
	public static final String SMS_DROP_TABLE = "DROP TABLE IF EXISTS "
			+ SMS_TABLE;
	public static final String SMS_SELECT_ALL = "SELECT * FROM " + SMS_TABLE;
}