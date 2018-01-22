package com.ut3.ehg.turismotepic;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Integer.parseInt;

public class HomeActivity extends Fragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    //private rc_usuarios rcUsuarios;
    private int idcat, idcat1, idcat2, idcat3;
    ViewGroup root;
    Context cntx;
    ImageButton bmenu;
    SharedPreferences.Editor editarCat;

    private RequestQueue rqt;
    private Context ctx;

    private String url = "https://arcadia.cicese.mx/WebServiceT2/proceso.php ";//"http://158.97.121.65/WebServiceT2/proceso.php";
    private StringRequest strq;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = (ViewGroup) inflater.inflate(R.layout.activity_home, null);
        cntx = container.getContext();
        bmenu = (ImageButton)root.findViewById(R.id.bmenu);
        bmenu.setOnClickListener(this);

        ctx = getContext();

        rqt = Volley.newRequestQueue(ctx);

        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

            }
        }

        SharedPreferences cat=this.getActivity().getSharedPreferences("user",MODE_PRIVATE);
        String usuario = cat.getString("user","user");
        String pass = cat.getString("pass","pass");
        perfil(usuario,pass);
        return root;
    }


    @Override
    public void onClick(View v) {
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        if(!drawer.isDrawerOpen(GravityCompat.START))
            drawer.openDrawer(GravityCompat.START);
        else
            drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    private void perfil(final String usuario, final String pass) {


        strq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rta_servidor", response);
                        //Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                        ImageView imageone = (ImageView)root.findViewById(R.id.img1);
                        ImageView imagetwo = (ImageView)root.findViewById(R.id.img2);
                        ImageView imagethree = (ImageView)root.findViewById(R.id.img3);
                        ImageView imagefour = (ImageView)root.findViewById(R.id.img4);
                        TextView textone=(TextView)root.findViewById(R.id.text1);
                        TextView texttwo=(TextView)root.findViewById(R.id.text2);
                        TextView textthree=(TextView)root.findViewById(R.id.text3);
                        TextView textfour=(TextView)root.findViewById(R.id.text4);

                        String idPerfil = response;


                        if(idPerfil.equals("1")){
                            imageone.setImageResource(R.drawable.restaurantes);
                            textone.setText("Restaurantes");
                            idcat = 2;
                            imagetwo.setImageResource(R.drawable.museos);
                            texttwo.setText("Museos");
                            idcat1 = 4;
                            imagethree.setImageResource(R.drawable.bancos);
                            textthree.setText("Bancos");
                            idcat2 = 5;
                            imagefour.setImageResource(R.drawable.monumentos);
                            textfour.setText("Monumentos");
                            idcat3 = 3;
                        }else if(idPerfil.equals("2")){
                            imageone.setImageResource(R.drawable.restaurantes);
                            textone.setText("Restaurantes");
                            idcat = 2;
                            imagetwo.setImageResource(R.drawable.mall);
                            texttwo.setText("Plazas comerciales");
                            idcat1 = 8;
                            imagethree.setImageResource(R.drawable.tiendas);
                            textthree.setText("Tiendas");
                            idcat2 = 7;
                            imagefour.setImageResource(R.drawable.bancos);
                            textfour.setText("Bancos");
                            idcat3 = 5;
                        }else if(idPerfil.equals("3")){
                            imageone.setImageResource(R.drawable.parques);
                            textone.setText("Parques");
                            idcat = 9;
                            imagetwo.setImageResource(R.drawable.museos);
                            texttwo.setText("Museos");
                            idcat1 = 4;
                            imagethree.setImageResource(R.drawable.restaurantes);
                            textthree.setText("Restaurantes");
                            idcat2 = 2;
                            imagefour.setImageResource(R.drawable.mall);
                            textfour.setText("Plazas Comerciales");
                            idcat3 = 8;
                        }else if(idPerfil.equals("4")){
                            imageone.setImageResource(R.drawable.restaurantes);
                            textone.setText("Restaurantes");
                            idcat = 2;
                            imagetwo.setImageResource(R.drawable.museos);
                            texttwo.setText("Museos");
                            idcat1 = 4;
                            imagethree.setImageResource(R.drawable.bancos);
                            textthree.setText("Bancos");
                            idcat2 = 5;
                            imagefour.setImageResource(R.drawable.mall);
                            textfour.setText("Plazas Comerciales");
                            idcat3 = 8;
                        }else if(idPerfil.equals("5")){
                            imageone.setImageResource(R.drawable.restaurantes);
                            textone.setText("Restaurantes");
                            idcat = 2;
                            imagetwo.setImageResource(R.drawable.bancos);
                            texttwo.setText("Bancos");
                            idcat1 = 5;
                            imagethree.setImageResource(R.drawable.museos);
                            textthree.setText("Museos");
                            idcat2 = 4;
                            imagefour.setImageResource(R.drawable.mall);
                            textfour.setText("Plazas Comerciales");
                            idcat3 = 8;
                        }else if(idPerfil.equals("6")){
                            imageone.setImageResource(R.drawable.museos);
                            textone.setText("Museos");
                            idcat = 4;
                            imagetwo.setImageResource(R.drawable.monumentos);
                            texttwo.setText("Monumentos");
                            idcat1 = 3;
                            imagethree.setImageResource(R.drawable.restaurantes);
                            textthree.setText("Restaurantes");
                            idcat2 = 2;
                            imagefour.setImageResource(R.drawable.mall);
                            textfour.setText("Plazas Comerciales");
                            idcat3 = 8;
                        }else if(idPerfil.equals("7")){
                            imageone.setImageResource(R.drawable.museos);
                            textone.setText("Museos");
                            idcat = 4;
                            imagetwo.setImageResource(R.drawable.bancos);
                            texttwo.setText("Bancos");
                            idcat1 = 5;
                            imagethree.setImageResource(R.drawable.tiendas);
                            textthree.setText("Tiendas");
                            idcat2 = 7;
                            imagefour.setImageResource(R.drawable.mall);
                            textfour.setText("Plazas Comerciales");
                            idcat3 = 8;

                        }else if(idPerfil.equals("8")){
                            imageone.setImageResource(R.drawable.museos);
                            textone.setText("Museos");
                            idcat = 4;
                            imagetwo.setImageResource(R.drawable.parques);
                            texttwo.setText("Parques");
                            idcat1 = 9;
                            imagethree.setImageResource(R.drawable.monumentos);
                            textthree.setText("Monumentos");
                            idcat2 = 3;
                            imagefour.setImageResource(R.drawable.mall);
                            textfour.setText("Plazas Comerciales");
                            idcat3 = 8;

                        }else if(idPerfil.equals("10")){
                            imageone.setImageResource(R.drawable.museos);
                            textone.setText("Museos");
                            idcat = 4;
                            imagetwo.setImageResource(R.drawable.restaurantes);
                            texttwo.setText("Restaurantes");
                            idcat1 = 2;
                            imagethree.setImageResource(R.drawable.monumentos);
                            textthree.setText("Monumentos");
                            idcat2 = 3;
                            imagefour.setImageResource(R.drawable.mall);
                            textfour.setText("Plazas Comerciales");
                            idcat3 = 8;

                        }else if(idPerfil.equals("11")){
                            imageone.setImageResource(R.drawable.bancos);
                            textone.setText("Bancos");
                            idcat = 5;
                            imagetwo.setImageResource(R.drawable.tiendas);
                            texttwo.setText("Tiendas");
                            idcat1 = 7;
                            imagethree.setImageResource(R.drawable.restaurantes);
                            textthree.setText("Restaurantes");
                            idcat2 = 2;
                            imagefour.setImageResource(R.drawable.mall);
                            textfour.setText("Plazas Comerciales");
                            idcat3 = 8;
                        }else if(idPerfil.equals("13")){
                            imageone.setImageResource(R.drawable.restaurantes);
                            textone.setText("Restaurantes");
                            idcat = 2;
                            imagetwo.setImageResource(R.drawable.tiendas);
                            texttwo.setText("Tiendas");
                            idcat1 = 7;
                            imagethree.setImageResource(R.drawable.parques);
                            textthree.setText("Parques");
                            idcat2 = 9;
                            imagefour.setImageResource(R.drawable.mall);
                            textfour.setText("Plazas Comerciales");
                            idcat3 = 8;
                        }
                        else if(idPerfil.equals("14")){
                            imageone.setImageResource(R.drawable.farmacias);
                            textone.setText("Farmacias");
                            idcat = 6;
                            imagetwo.setImageResource(R.drawable.otros);
                            texttwo.setText("Otros");
                            idcat1 = 10;
                            imagethree.setImageResource(R.drawable.restaurantes);
                            textthree.setText("Restaurantes");
                            idcat2 = 2;
                            imagefour.setImageResource(R.drawable.museos);
                            textfour.setText("Museos");
                            idcat3 = 4;
                        }
                        // click en la imagen uno
                        imageone.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                fragment(idcat);
                            }
                        });


                        // click en la imagen dos
                        imagetwo.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                fragment(idcat1);
                            }
                        });
                        // click en la imagen tres
                        imagethree.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                fragment(idcat2);
                            }
                        });
                        //click en la imagen cuatro
                        imagefour.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                fragment(idcat3);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error_servidor", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("usuario", usuario);
                parametros.put("pass", pass);
                parametros.put("operacion", "perfiles");

                return parametros;
            }
        };

        rqt.add(strq);
    }


    public void fragment(int dato){
        SharedPreferences cat;
        cat = getContext().getSharedPreferences("categoria",MODE_PRIVATE);
        editarCat=cat.edit();
        editarCat.putInt("idCat",dato);
        editarCat.commit();
        String fragmentTemp="com.ut3.ehg.turismotepic.CategoriaActivity";
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.lframe, Fragment.instantiate(getContext(), fragmentTemp)).addToBackStack("tag");
        tx.commit();
        drawer.closeDrawer(GravityCompat.START);
    }
}


