package com.ut3.ehg.turismotepic;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class EncuestaActivity extends Fragment {

    RatingBar rb1,rb2,rb3,rb4,rb5;
    ImageButton btnEnviar;
    //rc_encuesta rcEncuesta;
    SharedPreferences user,loginPreferences;
    float r1,r2,r3,r4,r5;
    private EditText opinion;
    private RequestQueue rqt;
    private Context ctx;

    private String url = "https://arcadia.cicese.mx/WebServiceT2/proceso.php ";//"http://158.97.121.65/WebServiceT2/proceso.php";
    private StringRequest strq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root;


        root = (ViewGroup) inflater.inflate(R.layout.activity_encuesta, null);


        ctx = getActivity();

        rqt = Volley.newRequestQueue(ctx);



        rb1 = (RatingBar) root.findViewById(R.id.ratingb1);
        rb2 = (RatingBar) root.findViewById(R.id.ratingb2);
        rb3 = (RatingBar) root.findViewById(R.id.ratingb3);
        rb4 = (RatingBar) root.findViewById(R.id.ratingb4);
        opinion = (EditText) root.findViewById(R.id.opinionText);
        //rb5 = (RatingBar) root.findViewById(R.id.ratingb5);
        btnEnviar = (ImageButton) root.findViewById(R.id.btnEnviar);
       // String Opinion = opinion.getText().toString();
        user=this.getActivity().getSharedPreferences("user", MODE_PRIVATE);
        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs",MODE_PRIVATE);
        btnEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



        //System.out.println("el usuario eso"+ user);

                String Opinion = opinion.getText().toString();
                //int idUser=user.getInt("idUser",0);
                r1=rb1.getRating();
                r2=rb2.getRating();
                r3=rb3.getRating();
                r4=rb4.getRating();

                encuesta(r1,r2,r3,r4,Opinion);

               /* System.out.println("El varlos numerico es de "+r4);
                //r5=rb5.getRating();
                rcEncuesta= new rc_encuesta(getActivity().getApplicationContext());
                rcEncuesta.open();
                rcEncuesta.enviarEncuesta(idUser,r1,r2,r3,r4,Opinion);
                Toast.makeText(getActivity(), "Encuesta enviada", Toast.LENGTH_SHORT).show();
                */
                String fragmentTemp="com.ut3.ehg.turismotepic.HomeActivity";
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.lframe, Fragment.instantiate(getContext(), fragmentTemp));
                tx.commit();
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        return root;
    }

    private void encuesta(final float r1, final float r2, final float r3, final float r4, final String Opinion) {

        SharedPreferences cat=this.getActivity().getSharedPreferences("user",MODE_PRIVATE);
        final String user = cat.getString("user", "");


        strq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rta_servidor", response);
                        Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error_servidor", error.toString());
                Toast.makeText(ctx, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("usuario", user);
                parametros.put("r1", String.valueOf(r1));
                parametros.put("r2", String.valueOf(r2));
                parametros.put("r3", String.valueOf(r3));
                parametros.put("r4", String.valueOf(r4));
                parametros.put("opinion", Opinion);
                parametros.put("operacion", "encuesta");

                return parametros;
            }
        };

        rqt.add(strq);

    }



}