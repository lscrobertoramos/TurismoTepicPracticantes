package com.ut3.ehg.turismotepic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ut3.ehg.turismotepic.rc.rc_pois;

import static android.content.Context.MODE_PRIVATE;


public class DetalladoActivity extends Fragment implements View.OnClickListener {
    private rc_pois poisdb;
    Cursor datos;
    ViewGroup root;
    Context cntx;
    SharedPreferences poi,ubic;
    SharedPreferences.Editor editarUbic;
    int cat;
    String destino;
    LocationManager locationManager;

    //LocationManager locationManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //final AppBarLayout appBarLayout;
        root = (ViewGroup) inflater.inflate(R.layout.activity_detallado, null);
        cntx = container.getContext();
        poi=this.getActivity().getSharedPreferences("poi",MODE_PRIVATE);


        //Pata detectar el funcionamiento del GPS
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        ImageView iv1 = (ImageView)root.findViewById(R.id.iv1);
        String idPlace=poi.getString("idPoi","");
        TextView nombre = (TextView)root.findViewById(R.id.lugar);
        //TextView posicion= (TextView)root.findViewById(R.id.posicion);
        TextView horario =(TextView)root.findViewById(R.id.horario);
        TextView costo=(TextView)root.findViewById(R.id.costo);
        //TextView descripcion=(TextView)root.findViewById(R.id.descripcion);
        JustifiedTextView J = (JustifiedTextView)root.findViewById(R.id.descripcion);
        ImageButton bruta = (ImageButton)root.findViewById(R.id.bruta);
        //Obtención de los datos
        poisdb= new rc_pois(root.getContext());
        poisdb.open();
        datos=poisdb.getDatos(idPlace);
        datos.moveToFirst();
        cat=datos.getInt(1);
        destino=datos.getString(2);
        nombre.setText(datos.getString(2));
       //posicion.setText("("+datos.getString(5)+" "+datos.getString(6)+")");
        horario.setText("Horario: "+datos.getString(7));
        costo.setText("Costo: "+datos.getString(9));
        J.setText(datos.getString(4));
        J.setTextColor(Color.BLACK);
        J.setTextSize(13);
        //descripcion.setText(datos.getString(4));
        bruta.setOnClickListener(this);


        //Cosas de la imagen
        String image=datos.getString(11);
        if(!image.equals("")) {
            int id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
            Drawable drawable = getResources().getDrawable(id);
            iv1.setImageDrawable(drawable);
        }

        checkLocation();

        return root;
    }

    @Override
    public void onClick(View v) {

        //checkLocation();

        if(isNetDisponible()){
            //System.out.println("hay internet");
            String loc = datos.getString(5)+","+datos.getString(6);
            String fragmentTemp="com.ut3.ehg.turismotepic.MapsActivity";
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.lframe, Fragment.instantiate(getContext(), fragmentTemp)).addToBackStack("tag");
            tx.commit();
            drawer.closeDrawer(GravityCompat.START);
            ubic = getContext().getSharedPreferences("ubic",MODE_PRIVATE);
            editarUbic=ubic.edit();
            editarUbic.putString("loc",loc);
            editarUbic.putInt("cat",cat);
            editarUbic.putString("destino",destino);
            editarUbic.commit();
        }
        else
        {
            ///System.out.println("No hay intenerwe");
            Toast.makeText(getActivity(), "Su dispositivo no tiene conexion a internet, por favor habilitelo", Toast.LENGTH_LONG).show();

           /* new CountDownTimer(4000, 4000) {
                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {

                    String fragmentTemp="com.ut3.ehg.turismotepic.HomeActivity";
                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                    tx.replace(R.id.lframe, Fragment.instantiate(getContext(), fragmentTemp));
                    tx.commit();
                    drawer.closeDrawer(GravityCompat.START);
                }
            }.start();*/
            //getActivity().getSupportFragmentManager().popBackStack();
        }
    }


    // Funcion para revisar si el dispositivo esta conectado a internet
    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "para el correcto funcionamiento de esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



/*
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }
    */


}
