package com.ut3.ehg.turismotepic.rc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.ut3.ehg.turismotepic.db.db_pois;


/**
 * Created by LAB-DES-05 on 31/10/2016.
 */

public class rc_pois {
    private final db_pois dbPois;
    private SQLiteDatabase database;
    private final String TAG = this.getClass().getName();

    public rc_pois(Context context) { dbPois = new db_pois(context); }
    public void open() throws SQLiteException { database = dbPois.getWritableDatabase(); }
    public void close() { database.close(); }
    public void read(){ database = dbPois.getReadableDatabase(); }
    /*------------------------------- Public Methods for RC_USER----------------------------------*/

    //public void insertarPois (String Usuario, String Pass, String Sexo, String Motivo, String Origen, String Acompañantes, String Edad){
      //  SQLiteDatabase db;

     //   database.insert(TABLE_NAME, null,generarContentValues(Usuario,Pass, Sexo, Motivo, Origen, Acompañantes,Edad));
    //}

    public Cursor getDatos(String id){
        String[] args = new String[] {id};
        Cursor c = database.rawQuery(" SELECT * FROM pois WHERE id_poi=?",args);
        return c;
    }

    public Cursor getPois(){
        Cursor c =database.rawQuery("SELECT * FROM pois ", null);
        /*ArrayList<String> pois = new ArrayList<String>();
        while(!c.isAfterLast()) {
            pois.add(c.getString(c.getColumnIndex("name")));
            c.moveToNext();
        }*/
        //c.close();
        return c;
    }


}
