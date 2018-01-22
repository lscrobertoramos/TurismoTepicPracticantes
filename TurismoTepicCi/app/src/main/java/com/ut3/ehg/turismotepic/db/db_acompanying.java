package com.ut3.ehg.turismotepic.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LAB-DES-05 on 28/09/2016.
 */

import java.util.ArrayList;

public class db_acompanying  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="turismo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "acompanying";
    public static final String COLUMN_NAME_IDPK = "id_accom";
    public static final String COLUMN_NAME_TIPO = "nombre";



    //--------------------------------Insert Table Query
    public static final String SQL_INSERT_ENTRIES = "INSERT INTO `acompanying` (`id_accom`, `nombre`) VALUES (1, 'Solo'), (2, 'Pareja'), (3, 'Familia'), (4, 'Compañero de trabajo') ";
    //--------------------------------Delete Table Query
    public static final String SQL_DELETE_ENTRIES = " DROP TABLE IF EXIST " + TABLE_NAME;
    //--------------------------------Create Table Query
    public static final String CREATE_TABLE = " create table "
            + TABLE_NAME + " ( "
            + COLUMN_NAME_IDPK
            + " integer primary key, "
            + COLUMN_NAME_TIPO
            + " text )";


    public db_acompanying(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(db_motivo.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public ArrayList<String> obtenerAcompañantes(){
        ArrayList<String> lista=new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String oname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TIPO));
                    lista.add(oname);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            db.close();
        }
        return lista;
    }
}