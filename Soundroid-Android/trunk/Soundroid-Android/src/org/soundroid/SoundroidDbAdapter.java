/*
 * Copyright (C) 2009 SIAHM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.soundroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * 
 * @author Antonio Hinojo
 * 
 * Simple token database access helper class. Defines the basic CRUD operations
 * for the tokens.
 * 
 */
public class SoundroidDbAdapter {

	public static final String KEY_TOKEN_TYPE = "tokentype";
	public static final String KEY_TOKEN = "token";
	public static final String KEY_ROWID = "_id";

	private static final String TAG = "SoundroidDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_NAME = "soundroid";
	private static final String DATABASE_TABLE = "tokens";
	private static final int DATABASE_VERSION = 1;
	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE = "create table tokens (_id integer primary key autoincrement, token text not null, tokentype text not null);";

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	
	 /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public SoundroidDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    
    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public SoundroidDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx, DATABASE_TABLE, null, DATABASE_VERSION);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        mDbHelper.close();
    }
    
    /**
     * Return a Cursor positioned at the token that matches the given tokenType
     * 
     * @param tokenType to retrieve
     * @return Cursor positioned to matching token, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchToken(long tokenType) throws SQLException {

        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_TOKEN, KEY_TOKEN_TYPE}, KEY_TOKEN_TYPE + "=" + tokenType, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    
    /**
     * Delete the token with the given tokenType
     * 
     * @param tokenType id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteToken(long tokenType) {

        return mDb.delete(DATABASE_TABLE, KEY_TOKEN_TYPE + "=" + tokenType, null) > 0;
    }
    
    /**
     * Update the token using the details provided. The note to be updated is
     * specified using the tokenType, and it is altered to use the new token value 
     * passed in
     * 
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateNote(long tokenType, String token) {
        ContentValues args = new ContentValues();
        args.put(KEY_TOKEN, token);
        args.put(KEY_TOKEN_TYPE, tokenType);

        return mDb.update(DATABASE_TABLE, args, KEY_TOKEN_TYPE + "=" + tokenType, null) > 0;
    }
}
