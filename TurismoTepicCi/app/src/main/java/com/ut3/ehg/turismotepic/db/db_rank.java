package com.ut3.ehg.turismotepic.db;

/**
 * Created by LAB-DES-05 on 27/09/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class db_rank extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="turismo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "rank";
    public static final String COLUMN_NAME_IDPK = "id_rank";
    public static final String COLUMN_NAME_ID_POI = "id_poi";
    public static final String COLUMN_NAME_ID_USER = "id_user";
    public static final String COLUMN_NAME_RATE = "rate";
    public static final String COLUMN_NAME_OPINION = "opinion";




    //--------------------------------Delete Table Query
    public static final String SQL_DELETE_ENTRIES = " DROP TABLE IF EXIST " + TABLE_NAME;
    //--------------------------------Create Table Query
    public static final String CREATE_TABLE = " create table "
            + TABLE_NAME + " ( "
            + COLUMN_NAME_IDPK
            + " integer primary key, "
            + COLUMN_NAME_ID_USER
            + " text, "
            + COLUMN_NAME_ID_POI
            + " text, "
            + COLUMN_NAME_RATE
            + " text, "
            + COLUMN_NAME_OPINION
            + " text )";


    public db_rank(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(db_rank.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
