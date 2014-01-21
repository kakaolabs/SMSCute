package com.dungnh8.smscute.database;

public class ConstantSQLite {
	public static final String DATABASE_NAME = "/data/data/com.dungnh8.smscute/databases/smscute.db";
	public static final int DATABASE_VERSION = 1;

	// catalogue table
	public static final String CATALOGUE_TABLE = "catalogue";
	public static final String CATALOGUE_ID = "_id";
	public static final String CATALOGUE_NAME = "name";
	public static final String CATALOGUE_SEARCHED_NAME = "searched_name";

	// catalogue query
	public static final String CREATE_CATALOGUE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ CATALOGUE_TABLE
			+ " ("
			+ CATALOGUE_ID
			+ " INTEGER PRIMARY KEY, "
			+ CATALOGUE_NAME + " TEXT, " + CATALOGUE_SEARCHED_NAME + " TEXT)";
	public static final String DROP_CATALOGUE_TABLE = "DROP TABLE IF EXISTS "
			+ CATALOGUE_TABLE;

	// sms table
	public static final String SMS_TABLE = "sms";
	public static final String SMS_ID = "_id";
	public static final String SMS_CONTENT = "content";
	public static final String SMS_SEARCHED_CONTENT = "searched_content";
	public static final String SMS_IS_FAVORITED = "is_favorited";
	public static final String SMS_IS_USED = "is_used";
	public static final String SMS_CATALOGUE_ID = "catalogue_id";

	// sms query
	public static final String CREATE_SMS_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ SMS_TABLE + " (" + SMS_ID + " INTEGER PRIMARY KEY, "
			+ SMS_CONTENT + " TEXT, " + SMS_SEARCHED_CONTENT + " TEXT, "
			+ SMS_IS_FAVORITED + " INTEGER, " + SMS_IS_USED + " INTEGER, "
			+ SMS_CATALOGUE_ID + " INTEGER)";
	public static final String DROP_SMS_TABLE = "DROP TABLE IF EXISTS "
			+ SMS_TABLE;

	// news table
	public static final String NEWS_TABLE = "news";
	public static final String NEWS_ID = "_id";
	public static final String NEWS_TITLE = "title";
	public static final String NEWS_CONTENT = "content";
	public static final String NEWS_IMAGE_LINK = "image_link";
	public static final String NEWS_IS_NEW = "is_new";

	// sms query
	public static final String CREATE_NEWS_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ NEWS_TABLE
			+ " ("
			+ NEWS_ID
			+ " INTEGER PRIMARY KEY, "
			+ NEWS_TITLE
			+ " TEXT, "
			+ NEWS_CONTENT
			+ " TEXT, "
			+ NEWS_IMAGE_LINK + " TEXT, " + NEWS_IS_NEW + " INTEGER)";
	public static final String DROP_NEWS_TABLE = "DROP TABLE IF EXISTS "
			+ NEWS_TABLE;
}