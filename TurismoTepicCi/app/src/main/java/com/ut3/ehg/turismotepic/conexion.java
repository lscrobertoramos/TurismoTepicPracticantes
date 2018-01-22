package com.ut3.ehg.turismotepic;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by LAB-DES-05 on 01/02/2017.
 */

public class conexion extends  Activity{
    RequestQueue rqt;
    Context ctx;
    String url = "https://arcadia.cicese.mx/WebServiceT2/proceso.php ";//"http://158.97.121.65/WebServiceT2/proceso.php";

    StringRequest strq;


}
