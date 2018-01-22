package com.ut3.ehg.turismotepic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LAB-DES-05 on 26/09/2016.
 */

public class db_init extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "turismo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DB EXEC";

    public db_init(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,"INICINNADO.......");

        Log.i(TAG,db_pois.CREATE_TABLE);
        db.execSQL(db_pois.CREATE_TABLE);
        Log.i(TAG,db_pois.SQL_INSERT_ENTRIES);
        db.execSQL(db_pois.SQL_INSERT_ENTRIES);

        //------------------------------------------------------------------------------------------
        Log.i(TAG,db_motivo.CREATE_TABLE);
        db.execSQL(db_motivo.CREATE_TABLE);
        Log.i(TAG,db_motivo.SQL_INSERT_ENTRIES);
        db.execSQL(db_motivo.SQL_INSERT_ENTRIES);
        //------------------------------------------------------------------------------------------
        Log.i(TAG,db_categorias.CREATE_TABLE);
        db.execSQL(db_categorias.CREATE_TABLE);
        Log.i(TAG,db_categorias.SQL_INSERT_ENTRIES);
        db.execSQL(db_categorias.SQL_INSERT_ENTRIES);
        //------------------------------------------------------------------------------------------
        Log.i(TAG,db_origen.CREATE_TABLE);
        db.execSQL(db_origen.CREATE_TABLE);
        Log.i(TAG,db_origen.SQL_INSERT_ENTRIES);
        db.execSQL(db_origen.SQL_INSERT_ENTRIES);

        //------------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------
        Log.i(TAG,db_rank.CREATE_TABLE);
        db.execSQL(db_rank.CREATE_TABLE);
        //------------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------
        Log.i(TAG,db_acompanying.CREATE_TABLE);
        db.execSQL(db_acompanying.CREATE_TABLE);
        Log.i(TAG,db_acompanying.SQL_INSERT_ENTRIES);
        db.execSQL(db_acompanying.SQL_INSERT_ENTRIES);
        //------------------------------------------------------------------------------------------
Log.i(TAG,"Finalizar....");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(db_motivo.SQL_DELETE_ENTRIES);
        db.execSQL(db_acompanying.SQL_DELETE_ENTRIES);

        db.execSQL(db_origen.SQL_DELETE_ENTRIES);
        db.execSQL(db_pois.SQL_DELETE_ENTRIES);
        db.execSQL(db_rank.SQL_DELETE_ENTRIES);

        onCreate(db);
    }

}