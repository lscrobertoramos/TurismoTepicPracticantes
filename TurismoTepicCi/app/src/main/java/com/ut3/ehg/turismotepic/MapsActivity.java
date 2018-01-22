package com.ut3.ehg.turismotepic;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.*;
import java.util.logging.Handler;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

import static android.R.attr.tag;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.wearable.DataMap.TAG;

public class MapsActivity extends Fragment implements LocationListener, OnMapReadyCallback, DirectionFinderListener {
    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 10000;// * 60 * 1; // 12 segundos y se actualiza ***1 minute
    private LocationManager mLocationManager;

    //

    SharedPreferences ubic;
    ViewGroup root;
    Context cntx;
    LocationProvider loc;
    String latitud, longitud, destino;

    int cat;
    Location location;


    //prueba
    public Criteria criteria;
    public String bestProvider;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = (ViewGroup) inflater.inflate(R.layout.fragment_maps, null);
        cntx = container.getContext();

        progressDialog = ProgressDialog.show(getContext(), "Espera",
                "Buscando las indicaciones", true);
       // getLocation();


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        System.out.print("Cambio 2");


        return root;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "onResume");

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);



    }



    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.removeUpdates(this);


       /* latitud = String.valueOf(location.getLatitude());
        longitud=String.valueOf(location.getLongitude());*/

//        this.onCreateView(null,null,null);
        //ruta();
        //Toast.makeText(getActivity(), "Tu ubicacion en pausa : "+latitud+","+longitud, Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), "Eliminando latitud y longitud", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

    }

    private void sendRequest() {
        ubic = this.getActivity().getSharedPreferences("ubic", MODE_PRIVATE);
        String loc = ubic.getString("loc", "");
        String origin = latitud + "," + longitud;
        String destination = loc;
        cat = ubic.getInt("cat", 0);
        destino = ubic.getString("destino", "");

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDirectionFinderStart() {


        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }

    }


    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
//        System.out.println("Cat: "+cat);
        String image = getIcono(cat);
        int id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
        // Drawable drawable = getResources().getDrawable(id);
        //iv1.setImageDrawable(drawable);
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {


            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 14));
            ((TextView) getActivity().findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) getActivity().findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.inicio))
                    .title("Tu ubicación")
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(id))
                    .title(destino)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.parseColor("#F29100")).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    public String getIcono(int cat) {
        String icono = "";
        switch (cat) {
            case 1:
                icono = "hoteles_ico";
                break;
            case 2:
                icono = "restaurantes_ico";
                break;
            case 3:
                icono = "monumentos_ico";
                break;
            case 4:
                icono = "museos_ico";
                break;
            case 5:
                icono = "bancos_ico";
                break;
            case 6:
                icono = "farmacias_ico";
                break;
            case 7:
                icono = "tiendas_ico";
                break;
            case 8:
                icono = "mall_ico";
                break;
            case 9:
                icono = "parques_ico";
                break;
            case 10:
                icono = "otros_ico";
                break;

        }
        return icono;
    }



    public void getLocation() {
        // lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        //Adecuaciones
        criteria = new Criteria();
        bestProvider = String.valueOf(mLocationManager.getBestProvider(criteria, false)).toString();
//


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            } else {
                location = mLocationManager.getLastKnownLocation(bestProvider);
                //location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            }

        } else {
            location = mLocationManager.getLastKnownLocation(bestProvider);
            //location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }


        //prueba
        if (location != null) {
            Log.e("TAG", "GPS is on");
            //latitude = location.getLatitude();
            //longitude = location.getLongitude();
            latitud = String.valueOf(location.getLatitude());
            longitud = String.valueOf(location.getLongitude());
            sendRequest();
        } else {
            //This is what you need:
            mLocationManager.requestLocationUpdates(bestProvider, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        }

/*
        latitud = String.valueOf(location.getLatitude());
        longitud = String.valueOf(location.getLongitude());
        System.out.println("Ubicacion: "+latitud+","+longitud);
        //Toast.makeText(getActivity(), "Ubicacion: "+latitud+","+longitud, Toast.LENGTH_LONG).show();
*/

    }

    @Override
    public void onLocationChanged(Location location) {


        Log.i(TAG, "entro" + String.valueOf(location.getLatitude()));
        Log.i(TAG, "entro" + String.valueOf(location.getLongitude()));

        latitud = String.valueOf(location.getLatitude());
        longitud = String.valueOf(location.getLongitude());



        sendRequest();


        /*
        //when the location changes, update the map by zooming to the location
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
        mMap.moveCamera(center);

        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);
        */
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle) {
        // Log.i(TAG, "Provider " + provider + " has now status: " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        //  Log.i(TAG, "Provider " + provider + " is enabled");

    }

    @Override
    public void onProviderDisabled(String provider) {
      Toast.makeText(getActivity(), "GPS DESACTIVADO", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();



    }


}
