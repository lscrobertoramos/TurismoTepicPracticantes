package com.ut3.ehg.turismotepic;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Login extends conexion{


    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private SharedPreferences loginPreferences,user;
    private SharedPreferences.Editor loginPrefsEditor,userEditor;
    private Boolean saveLogin;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            }
        }

        ctx = Login.this;

        rqt = Volley.newRequestQueue(ctx);

        TextView tx = (TextView)findViewById(R.id.tvTitleLogin);
        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        //Typeface beba = Typeface.createFromAsset(getAssets(), "fonts/BebasKai-Regular.otf");
        Typeface maven = Typeface.createFromAsset(getAssets(), "fonts/MavenPro-Regular.ttf");

        final EditText etUsuarioL = (EditText) findViewById(R.id.etUsuarioLog);
        final EditText etPassL = (EditText) findViewById(R.id.etPassLog);
        final TextView tvLogin = (TextView) findViewById(R.id.tvTitleLogin);
        final CheckBox cbR = (CheckBox) findViewById(R.id.cbRemenberme);
        loginPreferences = getSharedPreferences("loginPrefs",MODE_PRIVATE);
        user=getSharedPreferences("user",MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        userEditor=user.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin",false);
        if(saveLogin==true){
            etUsuarioL.setText(loginPreferences.getString("username",""));
            etPassL.setText(loginPreferences.getString("password",""));
            cbR.setChecked(true);
        }

        tx.setTypeface(roboto);
        etUsuarioL.setTypeface(maven);
        etPassL.setTypeface(maven);
        cbR.setTypeface(maven);
        tvLogin.setTypeface(roboto);


        TextView btnRegistro =(TextView) findViewById(R.id.tvRegistro);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (etUsuarioL.getText().length()==0){
                    etUsuarioL.setError("Correo Vacio");
                }else{
                    login(etUsuarioL.getText().toString(), etPassL.getText().toString());
                }
                if (etPassL.getText().length()==0){
                    etPassL.setError("Password vacio");
                }else{
                    progressDialog = ProgressDialog.show(ctx, "Espera",
                            "Iniciando sesión", true);
                    login(etUsuarioL.getText().toString(), etPassL.getText().toString());

                }





/*
                String id =null;
                rcUsuarios = new rc_usuarios(getApplicationContext());
                rcUsuarios.open();
                id = rcUsuarios.checarUsuario(etUsuarioL.getText().toString(),etPassL.getText().toString());
                rcUsuarios.close();
                if(id == null){
                    Toast.makeText(Login.this, "El usuario o password es incorrecto", Toast.LENGTH_SHORT).show();
                }else{

                    System.out.println("El id del user es " +id);

                    userEditor.putInt("idUser",parseInt(id));
                    userEditor.putString("user",etUsuarioL.getText().toString() );
                    userEditor.putString("pass", etPassL.getText().toString());
                    userEditor.commit();

                    if (cbR.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", etUsuarioL.getText().toString());
                        loginPrefsEditor.putString("password", etPassL.getText().toString());
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                    Login.this.finish();
                }*/
            }

        });

        btnRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Registro.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    private void login(final String usuario, final String pass) {
        final CheckBox cbR = (CheckBox) findViewById(R.id.cbRemenberme);
        strq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rta_servidor", response);
                        //Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                        if (response.equals("Bienvenido")) {



                            userEditor.putString("user",usuario);
                            userEditor.putString("pass", pass);
                            userEditor.commit();



                            if (cbR.isChecked()) {
                                loginPrefsEditor.putBoolean("saveLogin", true);
                                loginPrefsEditor.putString("username", usuario);
                                loginPrefsEditor.putString("password", pass);
                                loginPrefsEditor.commit();
                            } else {
                                loginPrefsEditor.clear();
                                loginPrefsEditor.commit();
                            }

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);

                            Login.this.finish();
                            progressDialog.dismiss();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(ctx, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error_servidor", error.toString());
                Toast.makeText(ctx, "No se pudo buscar el usuario \nPor favor verifique su conexion a internet", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("usuario", usuario);
                parametros.put("pass", pass);
                parametros.put("operacion", "login");

                return parametros;
            }
        };

        rqt.add(strq);

    }
}
