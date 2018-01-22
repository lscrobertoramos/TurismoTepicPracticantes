package com.ut3.ehg.turismotepic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ut3.ehg.turismotepic.db.db_acompanying;
import com.ut3.ehg.turismotepic.db.db_motivo;
import com.ut3.ehg.turismotepic.db.db_origen;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Registro extends conexion{
    private String datoSexo;
    private EditText edad, usuario, pass;
    private TextView motivo, acompañantes, origen, sexo;
    private RadioButton rbHombre, rbMujer;
    private RadioGroup rdgGrupo;
    //private rc_usuarios rcUsuarios;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        db_motivo dataMotivo = new db_motivo(this);
        db_origen dataOrigen = new db_origen(this);
        final db_acompanying dataAcompañante = new db_acompanying(this);
        //final db_usuarios dataUsuarios = new db_usuarios(this);
        ctx = Registro.this;

        rqt = Volley.newRequestQueue(ctx);


        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        //Typeface beba = Typeface.createFromAsset(getAssets(), "fonts/BebasKai-Regular.otf");
        Typeface maven = Typeface.createFromAsset(getAssets(), "fonts/MavenPro-Regular.ttf");


        sexo = (TextView) findViewById(R.id.tvTitleSexo);

        usuario = (EditText) findViewById(R.id.etUsuarioLog);
        pass = (EditText) findViewById(R.id.etPassLog);
        edad = (EditText) findViewById(R.id.etEdad);

        usuario.setTypeface(maven);
        pass.setTypeface(maven);
        edad.setTypeface(maven);


        motivo = (TextView) findViewById(R.id.tvTexto_spMotivo);
        acompañantes = (TextView) findViewById(R.id.tvTexto_spAcompa);
        origen = (TextView) findViewById(R.id.tvTexto_spOrigen);





        /*motivo.setTypeface(maven);
        acompañantes.setTypeface(maven);
        origen.setTypeface(maven);*/


        rbHombre = (RadioButton) findViewById(R.id.rbHombre);
        rbMujer = (RadioButton) findViewById(R.id.rbMujer);
        rdgGrupo = (RadioGroup) findViewById(R.id.RGB);
        ArrayList<String> listaOrigenes = dataOrigen.obtenerOrigenes();
        ArrayList<String> listaMotivos = dataMotivo.obtenerMotivo();
        ArrayList<String> listaAcompa = dataAcompañante.obtenerAcompañantes();
        final Spinner spinnerMotivo = (Spinner) findViewById(R.id.spMotivo);
        final Spinner spinnerCompañeros = (Spinner) findViewById(R.id.spCompaneros);
        final Spinner spinnerOrigen = (Spinner) findViewById(R.id.spOrigen);


        ImageButton btnGuardar = (ImageButton) findViewById(R.id.btnGuardarReg);
        //Button btnCancelar = (Button) findViewById(R.id.btnCancelarReg);
        ImageButton btnEdad = (ImageButton) findViewById(R.id.ibtnEdad);

        ArrayAdapter<String> adaptadorMotivo = new ArrayAdapter<String>(this, R.layout.sp_motivo, R.id.tvTexto_spMotivo, listaMotivos);
        spinnerMotivo.setAdapter(adaptadorMotivo);

        //Spinner spinner = (Spinner) findViewById(R.id.pioedittxt5);

       /* ArrayAdapter<String> adaptadorMotivo = new ArrayAdapter<String>(this, R.layout.sp_motivo,R.id.tvTexto_spMotivo,listaMotivos);
        adaptadorMotivo.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerMotivo.setAdapter(adaptadorMotivo);*/


        ArrayAdapter<String> adaptadorAcomp = new ArrayAdapter<String>(this, R.layout.sp_acompa, R.id.tvTexto_spAcompa, listaAcompa);
        spinnerCompañeros.setAdapter(adaptadorAcomp);

        ArrayAdapter<String> adaptadorOrigenes = new ArrayAdapter<String>(this, R.layout.sp_origen, R.id.tvTexto_spOrigen, listaOrigenes);
        spinnerOrigen.setAdapter(adaptadorOrigenes);

        rdgGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.rbHombre) {
                    datoSexo = "Hombre";//Toast.makeText(getApplicationContext(), "choice: Hombre", Toast.LENGTH_SHORT).show();
                    rbMujer.setChecked(false);
                } else if (checkedId == R.id.rbMujer) {
                    datoSexo = "Mujer";//Toast.makeText(getApplicationContext(), "choice: Mujer", Toast.LENGTH_SHORT).show();
                    rbHombre.setChecked(false);
                }
            }
        });


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println("Estoy en el boton");

                if (edad.getText().length() != 0 || usuario.getText().length() != 0 || pass.getText().length() != 0) {
                    final String Sexo = datoSexo;
                    final String Usuario = usuario.getText().toString();
                    final String Pass = pass.getText().toString();
                    final String Edad = edad.getText().toString();
                    final String Motivo = spinnerMotivo.getSelectedItem().toString();
                    final String Acompañantes = spinnerCompañeros.getSelectedItem().toString();
                    int EdadP = Integer.parseInt(Edad);
                    String Perfil = "";
                    System.out.println("la edad es de " + EdadP);
                    System.out.println(" El sexo es " +Sexo);

                    if (Motivo.equals("Negocio") && Acompañantes.equals("Pareja")) {
                        Perfil = "3";
                    } else if (Motivo.equals("Negocio") && Acompañantes.equals("Familia")) {
                        Perfil = "4";
                    } else if (Motivo.equals("Negocio") && Acompañantes.equals("Compañero de trabajo")) {
                        Perfil = "5";
                    } else if (Motivo.equals("Placer") && Acompañantes.equals("Solo") && Sexo.equals("Hombre")) {
                        Perfil = "6";
                    } else if (Motivo.equals("Placer") && Acompañantes.equals("Solo") && Sexo.equals("Mujer")) {
                        Perfil = "7";
                    } else if (Motivo.equals("Placer") && Acompañantes.equals("Pareja") || Motivo.equals("Placer") && Acompañantes.equals("Familia")) {
                        Perfil = "8";
                    } else if (Motivo.equals("Placer") && Acompañantes.equals("Compañero de trabajo")) {
                        Perfil = "10";
                    } else if (Motivo.equals("Compras") && Acompañantes.equals("Solo") || Motivo.equals("Compras") && Acompañantes.equals("Pareja") || Motivo.equals("Compras") && Acompañantes.equals("Compañero de trabajo")) {
                        Perfil = "11";
                    } else if (Motivo.equals("Compras") && Acompañantes.equals("Familia")) {
                        Perfil = "13";
                    } else if (Motivo.equals("Negocio") && Acompañantes.equals("Solo") && Sexo.equals("Hombre")) {
                        Perfil = "1";
                    }
                    else if (Motivo.equals("Negocio") && Acompañantes.equals("Solo") && Sexo.equals("Mujer")) {
                        Perfil = "2";
                    }

                    if (EdadP >= 65) {
                        Perfil = "14";
                    }

                    final String Origen = spinnerOrigen.getSelectedItem().toString();
                    seleccionar(Sexo, Usuario, Pass, Edad, Motivo, Acompañantes, Perfil, Origen);

                    //System.out.println("El motivo es " +Motivo);
                    //System.out.println("El acompanante es " + Acompañantes);

                    //System.out.println("El perfil final es " +Perfil);

                    /*
                    rcUsuarios = new rc_usuarios(getApplicationContext());
                    rcUsuarios.open();
                    rcUsuarios.insertarUsuarios(Usuario, Pass, Sexo, Motivo, Acompañantes, Origen, Edad, Perfil);
                    rcUsuarios.close();

                    */
                   // System.out.println("El origen es " + Origen);
                    //Toast.makeText(getApplicationContext(), "Registro Realizado", Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent(v.getContext(), Login.class);
                    startActivity(intent);
                    finish();*/
                } else {
                    Toast.makeText(getApplicationContext(), "Uno o más campos estan vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });*/

        btnEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPickerDialog();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }




    private void seleccionar(final String Sexo, final String Usuario, final String Pass, final String Edad, final String Motivo, final String Acompañante, final String Perfil, final String Origen) {

        strq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rta_servidor", response);
                        Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                        if (response.equals("El usuario ya existe")) {
                            usuario.setText("");
                        } else {
                            Intent intent = new Intent(Registro.this, Login.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "No se pudo crear el usuario \nPor favor verifique su conexion a internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("usuario", Usuario);
                parametros.put("sexo", Sexo);
                parametros.put("pass", Pass);
                parametros.put("edad", Edad);
                parametros.put("motivo", Motivo);
                parametros.put("acompañante", Acompañante);
                parametros.put("perfil", Perfil);
                parametros.put("origen", Origen);
                parametros.put("operacion", "registro");

              //  System.out.println("Los valores son" + Usuario + " " + Sexo + " " + Pass + " " + Edad + " " + Motivo + " " + Acompañante + " " + Perfil + " " + Origen);


                return parametros;
            }
        };

        rqt.add(strq);

    }

    ////////////////////Funciola antes de agregar esta parte
    public void numberPickerDialog() {
        NumberPicker npEdad = new NumberPicker(this);
        npEdad.setMaxValue(80);
        npEdad.setMinValue(12);
        NumberPicker.OnValueChangeListener miValor = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                edad.setText("" + newVal);
            }
        };
        npEdad.setOnValueChangedListener(miValor);
        AlertDialog.Builder Dialogo = new AlertDialog.Builder(this).setView(npEdad);
        Dialogo.setTitle("Edad").setIcon(R.drawable.ic_menu_manage);

        Dialogo.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        Dialogo.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        Dialogo.show();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Registro Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
