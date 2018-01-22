package com.ut3.ehg.turismotepic.db;

/**
 * Created by LAB-DES-05 on 28/09/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class db_origen extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="turismo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "origen";
    public static final String COLUMN_NAME_IDPK = "id_origen";
    public static final String COLUMN_NAME_ID_NAME = "nombre_estado";
    public static final String COLUMN_NAME_ID_NAME_MUN = "municipio";



    //--------------------------------Insert Table Query
    public static final String SQL_INSERT_ENTRIES = "INSERT INTO `origen` (`id_origen`, `nombre_estado`, `municipio`) VALUES (1, 'Aguascalientes', ''), (2, 'Baja California', ''), (3, 'Baja California Sur', ''), (4, 'Campeche', ''), (5, 'CDMX', ''), (6, 'Chiapas', ''), (7, 'Chihuahua', ''), (8, 'Coahuila', ''), (9, 'Colima', ''), (10, 'Durango', ''), (11, 'Edo. Mex', ''), (12, 'Guanajuato', ''), (13, 'Guerrero', ''), (14, 'Hidalgo', ''), (15, 'Jalisco', ''), (16, 'Michoacán', ''), (17, 'Morelos', ''), (18, 'Nayarit', ''), (19, 'Nuevo León', ''), (20, 'Oaxaca', ''), (21, 'Puebla', ''), (22, 'Querétaro', ''), (23, 'Quintana Roo', ''), (24, 'San Luis Potosí', ''), (25, 'Sinaloa', ''), (26, 'Sonora', ''), (27, 'Tabasco', ''), (28, 'Tamaulipas', ''), (29, 'Tlaxcala', ''), (30, 'Veracruz', ''), (31, 'Yucatan', ''), (32, 'Zacatecas', ''), (33, 'Internacional', '') ";
    //--------------------------------Delete Table Query
    public static final String SQL_DELETE_ENTRIES = " DROP TABLE IF EXIST " + TABLE_NAME;
    //--------------------------------Create Table Query
    public static final String CREATE_TABLE = " create table "
            + TABLE_NAME + " ( "
            + COLUMN_NAME_IDPK
            + " integer primary key, "
            + COLUMN_NAME_ID_NAME
            + " text, "
            + COLUMN_NAME_ID_NAME_MUN
            + " text )";


    public db_origen(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        //db.execSQL("INSERT INTO `categorias` (`id_categoria`, `Nombre`) VALUES (1, 'Hotel'), (2, 'Restaurant'), (3, 'Monumento'), (4, 'Museo'), (5, 'Parque'), (6, 'Banco'), (7, 'Farmacia'), (8, 'Restaurant'), (9, 'Tienda'), (10, 'Plaza Comercial'), (11, 'Otro') ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(db_origen.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public ArrayList<String> obtenerOrigenes(){
        ArrayList<String> lista=new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String oname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID_NAME));
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
