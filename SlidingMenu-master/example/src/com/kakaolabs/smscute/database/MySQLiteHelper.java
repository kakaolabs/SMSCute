package com.kakaolabs.smscute.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kakaolabs.smscute.database.table.Catalogue;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private static MySQLiteHelper mInstance;
	private static SQLiteDatabase myWritableDb;
	private static SQLiteDatabase myReadableDb;
	private static Context mContext;
	private static final String TAG = "MySQLiteHelper";

	/**
	 * constructor
	 * 
	 * @author dungnh8
	 * @param context
	 */
	private MySQLiteHelper(Context context) {
		super(context, ConstantSQLite.DATABASE_NAME, null,
				ConstantSQLite.DATABASE_VERSION);
	}

	public static MySQLiteHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new MySQLiteHelper(context);
		}
		if (context != null)
			mContext = context;
		return mInstance;
	}

	public static MySQLiteHelper getInstance() {
		if (mInstance == null) {
			return new MySQLiteHelper(mContext);
		}
		return mInstance;
	}

	/**
	 * close connection
	 * 
	 * @author DungNH8
	 * @param database
	 */
	public void closeDatabaseConnection(SQLiteDatabase database) {
		try {
			if (database != null) {
				database.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "closeDatabaseConnection", e);
		}
	}

	public void closeCursor(Cursor cursor) {
		try {
			if (cursor != null) {
				cursor.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "closeCursor", e);
		}
	}

	/**
	 * create database
	 * 
	 * @author dungnh8
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		try {
			database.execSQL(ConstantSQLite.CATALOGUE_CREATE_TABLE);
		} catch (Exception e) {
			Log.e(TAG, "onCreate", e);
		}
	}

	/**
	 * upgrade database
	 * 
	 * @author dungnh8
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			// Drop older table if existed
			db.execSQL(ConstantSQLite.DROP_TABLE);
			// Create tables again
			onCreate(db);
		} catch (Exception e) {
			Log.e(TAG, "onUpgrade", e);
		}
	}

	public SQLiteDatabase getMyWritableDatabase() {
		if ((myWritableDb == null) || (!myWritableDb.isOpen())) {
			if (mInstance == null) {
				mInstance = getInstance();
			}
			myWritableDb = mInstance.getWritableDatabase();
			Log.i("ViettelSQLHelper", " new Writable database");
		}
		return myWritableDb;
	}

	public SQLiteDatabase getMyReadableDatabase() {
		if ((myReadableDb == null) || (!myReadableDb.isOpen())) {
			if (mInstance == null) {
				mInstance = getInstance();
			}
			myReadableDb = mInstance.getReadableDatabase();
		}
		return myReadableDb;
	}

	@Override
	public void close() {
		super.close();
		if (myWritableDb != null) {
			myWritableDb.close();
			myWritableDb = null;
		}
		if (myReadableDb != null) {
			myReadableDb.close();
			myReadableDb = null;
		}
	}

	/**
	 * create catalogue
	 * 
	 * @author Daniel
	 * @param catalogue
	 * @return
	 */
	public Catalogue createCatalogue(Catalogue catalogue) {
		SQLiteDatabase db = null;
		try {
			db = this.getMyWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(ConstantSQLite.CATALOGUE_CATALOGUE_ID,
					catalogue.getCatelogueID());
			values.put(ConstantSQLite.CATALOGUE_NAME, catalogue.getName());
			values.put(ConstantSQLite.CATALOGUE_SEARCHED_NAME,
					catalogue.getSearched_name());
			values.put(ConstantSQLite.CATALOGUE_PARENT_CATALOGUE_ID,
					catalogue.getParentCatalogueID());
			long id = db.insert(ConstantSQLite.CATALOGUE_TABLE, null, values);
			catalogue.setId(id);
			return catalogue;
		} catch (Exception e) {
			Log.e(TAG, "createCatalogue", e);
			return null;
		}
	}

	/**
	 * get all catalogues
	 * 
	 * @author Daniel
	 * @param link
	 * @return
	 */
	public ArrayList<Catalogue> getAllCatalogues() {
		try {
			ArrayList<Catalogue> catalogues = new ArrayList<Catalogue>();
			String sql = ConstantSQLite.CATALOGUE_SELECT_ALL;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				Catalogue catalogue = getCatalogueFromCursor(cursor);
				if (catalogue != null) {
					catalogues.add(catalogue);
				}
			}
			// return result
			if (catalogues != null && catalogues.size() >= 0) {
				return catalogues;
			} else {
				return null;
			}
		} catch (Exception e) {
			Log.e(TAG, "getTruyenByLink", e);
			return null;
		}
	}

	/**
	 * get all fields of catalogue from cursor
	 * 
	 * @author Daniel
	 * @param cursor
	 * @return
	 */
	private Catalogue getCatalogueFromCursor(Cursor cursor) {
		try {
			Catalogue catalogue = new Catalogue();
			catalogue.setId(cursor.getLong(0));
			catalogue.setCatelogueID(cursor.getInt(1));
			catalogue.setName(cursor.getString(2));
			catalogue.setSearched_name(cursor.getString(3));
			catalogue.setParentCatalogueID(cursor.getInt(4));
			return catalogue;
		} catch (Exception e) {
			Log.e(TAG, "getCatalogueFromCursor", e);
			return null;
		}
	}
}