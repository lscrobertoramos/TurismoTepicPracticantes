package com.ut3.ehg.turismotepic.db;

/**
 * Created by LAB-DES-05 on 28/09/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class db_categorias extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="turismo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "categorias";
    public static final String COLUMN_NAME_IDPK = "id_categoria";
    public static final String COLUMN_NAME_ID_NAME = "Nombre";



    //--------------------------------Insert Table Query
    public static final String SQL_INSERT_ENTRIES = "INSERT INTO `categorias` (`id_categoria`, `Nombre`) VALUES (1, 'Hotel'), (2, 'Restaurant'), (3, 'Monumento'), (4, 'Museo'), (5, 'Banco'), (6, 'Farmacia'), " +
            "(7, 'Tienda'), (8, 'Plaza comercial'), (9, 'Parques'), (10, 'Otros')";
    //--------------------------------Delete Table Query
    public static final String SQL_DELETE_ENTRIES = " DROP TABLE IF EXIST " + TABLE_NAME;
    //--------------------------------Create Table Query
    public static final String CREATE_TABLE = " create table "
            + TABLE_NAME + " ( "
            + COLUMN_NAME_IDPK
            + " integer primary key, "
            + COLUMN_NAME_ID_NAME
            + " text )";


    public db_categorias(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(db_categorias.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
