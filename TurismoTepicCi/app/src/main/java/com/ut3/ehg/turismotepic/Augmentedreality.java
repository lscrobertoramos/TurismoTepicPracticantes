package com.ut3.ehg.turismotepic;

import android.*;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.ut3.ehg.turismotepic.rc.rc_pois;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;


public class Augmentedreality extends Activity implements ArchitectViewHolderInterface {
    protected ArchitectView architectView;
    protected ArchitectView.SensorAccuracyChangeListener sensorAccuracyListener;
    protected Location lastKnownLocaton;
    protected ArchitectViewHolderInterface.ILocationProvider locationProvider;
    protected LocationListener locationListener;
    private rc_pois poisdb;
    Cursor datos;
    private static ArrayList<String> array = new ArrayList<String>();
    //ArrayList<String> array = new ArrayList<String>();
    ArrayList<String> array2 = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmentedreality);

        System.out.println("La informacion de la camara es " + this.getCameraPosition());

        System.out.println("La version del SDK es de " + getWikitudeSDKLicenseKey());


        //JSONArray jsonArray = new JSONArray(array);
        aumentedreality();
        //System.out.println(jsonArray);




    }

    public void aumentedreality(){

        poisdb= new rc_pois(getApplicationContext());
        poisdb.open();
        datos=poisdb.getPois();
        datos.moveToFirst();
        while(!datos.isAfterLast()) {
            array2.clear();
            for(int i=0;i<12;i++){
                array2.add(datos.getString(i));
            }
            JSONArray conv = new JSONArray(array2);

            array.add(conv.toString());
            datos.moveToNext();
        }

        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
        final StartupConfiguration config = new StartupConfiguration(this.getWikitudeSDKLicenseKey(), StartupConfiguration.Features.Geo, this.getCameraPosition());
        System.out.println("el resultado de config es " + config);
        this.architectView.callJavascript( "newData('" +array.toString() + "');" );
        try {
            this.architectView.onCreate( config );

        } catch (RuntimeException ex)
        {
            this.architectView = null;
            Toast.makeText(getApplicationContext(), "Tu dispositivo no es compatible con las funciones de realidad aumentada.", Toast.LENGTH_SHORT).show();
            finish();
        }

        this.sensorAccuracyListener = this.getSensorAccuracyListener();
        this.locationListener = new LocationListener() {

            @Override
            public void onStatusChanged( String provider, int status, Bundle extras ) {
            }

            @Override
            public void onProviderEnabled( String provider ) {
            }

            @Override
            public void onProviderDisabled( String provider ) {
            }

            @Override
            public void onLocationChanged( final Location location ) {
                // forward location updates fired by LocationProvider to architectView, you can set lat/lon from any location-strategy
                if (location!=null) {
                    // sore last location as member, in case it is needed somewhere (in e.g. your adjusted project)
                    Augmentedreality.this.lastKnownLocaton = location;
                    if ( Augmentedreality.this.architectView != null ) {
                        // check if location has altitude at certain accuracy level & call right architect method (the one with altitude information)
                        if ( location.hasAltitude() && location.hasAccuracy() && location.getAccuracy()<7) {
                            Augmentedreality.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy() );
                        } else {
                            Augmentedreality.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.hasAccuracy() ? location.getAccuracy() : 1000 );
                        }
                    }
                }
            }
        };
        this.locationProvider = getLocationProvider(this.locationListener);
    }


    @Override
    protected void onPostCreate( final Bundle savedInstanceState ) {
        super.onPostCreate(savedInstanceState);

        if ( this.architectView != null ) {
            // call mandatory live-cycle method of architectView
            this.architectView.onPostCreate();
            try {
                this.architectView.load( this.getARchitectWorldPath() );
                // rango de visio del poi
                this.architectView.setCullingDistance(4 * 1000); /* 4km */
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( this.architectView != null ) {
            this.architectView.onResume();
            if (this.sensorAccuracyListener!=null) {
                this.architectView.registerSensorAccuracyChangeListener( this.sensorAccuracyListener );
            }
        }
        if ( this.locationProvider != null ) {
            this.locationProvider.onResume();
        }
    }

   @Override
    protected void onPause() {
        super.onPause();
        if ( this.architectView != null ) {
            this.architectView.onPause();
           if ( this.sensorAccuracyListener != null ) {
                this.architectView.unregisterSensorAccuracyChangeListener(this.sensorAccuracyListener);
            }
        }
        if ( this.locationProvider != null ) {
            this.locationProvider.onPause();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        // call mandatory live-cycle method of architectView
        if ( this.architectView != null ) {
            this.architectView.onDestroy();
        }
    }

    protected String getWikitudeSDKLicenseKey() {
        return WikitudeSDKConstants.WIKITUDE_SDK_KEY;
    }
    protected String getARchitectWorldPath() {
        return "wikitude/index.html"; /* assets folder */
    }
    protected StartupConfiguration.CameraPosition getCameraPosition() {
        return StartupConfiguration.CameraPosition.BACK;
    }

    //public ArchitectViewHolderInterface.ILocationProvider getLocationProvider (final LocationListener locationListener){
    //  return  new com.example.ygb.touristarnay.LocationProvider(this, locationListener);
    //}
    public ArchitectViewHolderInterface.ILocationProvider getLocationProvider(final LocationListener locationListener) {
        return new com.ut3.ehg.turismotepic.LocationProvider(this, locationListener);
    }

    private long lastCalibrationToastShownTimeMillis = System.currentTimeMillis();

    public ArchitectView.SensorAccuracyChangeListener getSensorAccuracyListener() {
        return new ArchitectView.SensorAccuracyChangeListener() {
            @Override
            public void onCompassAccuracyChanged( int accuracy ) {
				/* UNRELIABLE = 0, LOW = 1, MEDIUM = 2, HIGH = 3 */
                if ( accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM && Augmentedreality.this != null && !Augmentedreality.this.isFinishing() && System.currentTimeMillis() - Augmentedreality.this.lastCalibrationToastShownTimeMillis > 5 * 1000) {
                    //Toast.makeText( MainActivity.this, R.string.compass_accuracy_low, Toast.LENGTH_LONG ).show();
                    Augmentedreality.this.lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
                }
            }
        };
    }
}