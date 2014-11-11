package ru.itmo.delf.colloquium2;
/*
 * Copyright (C) 2008 Google Inc.
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

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    public static final String CANDIDATE_NAME= "candidate_name";
    public static final String CANDIDATE_ID = "_id";
    public static final String CANDIDATE_VALUE = "value";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String CANDIDATE_TABLE = "candidates";
    private static final String DATABASE_NAME = "vote_itmo_delf";
    private static final int DATABASE_VERSION = 2;

    private static final String INIT_CANDIDATE_TABLE =
            "create table `" + CANDIDATE_TABLE + "` (`" + CANDIDATE_ID + "` integer primary key autoincrement, `"
                    + CANDIDATE_NAME + "` text not null, `"
                    + CANDIDATE_VALUE + "` integer DEFAULT 0)";

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(INIT_CANDIDATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + CANDIDATE_TABLE);
            onCreate(db);
        }
    }

    public DBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DBAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDb.close();
        mDbHelper.close();
    }


    void clearCandidateTable() {
        mDb.delete(CANDIDATE_TABLE, null, null);
    }

    public long addCandidate(String candidateName) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CANDIDATE_NAME, candidateName);
        return mDb.insert(CANDIDATE_TABLE, null, initialValues);
    }


    public boolean deleteCandidate(long candidateId) {
        return mDb.delete(CANDIDATE_TABLE, CANDIDATE_ID + "=" + candidateId, null) > 0;
    }

    public Cursor fetchAllCandidates() {
        return mDb.query(CANDIDATE_TABLE, new String[] {CANDIDATE_ID, CANDIDATE_NAME,
                CANDIDATE_VALUE}, null, null, null, null, null);
    }

    public boolean addVote(long candidateId) {
        /*ContentValues args = new ContentValues();
        args.put(CANDIDATE_VALUE, CANDIDATE_VALUE+"+"+1);
        return mDb.update(CANDIDATE_TABLE, args, CANDIDATE_ID + "=" + candidateId, null) > 0;*/
        mDb.execSQL("UPDATE " + CANDIDATE_TABLE+ " SET "
                + CANDIDATE_VALUE+ " = " + CANDIDATE_VALUE+ " +1 WHERE "
                + CANDIDATE_ID + " =" +candidateId);
        return  true
                ;
    }

    public boolean editCandidate(long candidateId, String new_name) {
        ContentValues args = new ContentValues();
        args.put(CANDIDATE_NAME, new_name);
        return mDb.update(CANDIDATE_TABLE, args, CANDIDATE_ID + "=" + candidateId, null) > 0;
    }
}
