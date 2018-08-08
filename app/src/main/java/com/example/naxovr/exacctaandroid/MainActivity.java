package com.example.naxovr.exacctaandroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciamos todos los componentes
        Button botonLocalizar = findViewById(R.id.buttonLocalizame);
        Button botonBuscar = findViewById(R.id.buttonBuscar);
        Button botonHistorial = findViewById(R.id.buttonHistorial);
        final EditText editTextLongitud = findViewById(R.id.editTextLongitud);
        final EditText editTextLatitud = findViewById(R.id.editTextLatitud);
        final TextView textViewLongitud = findViewById(R.id.textViewLongitud);
        final TextView textViewLatitud = findViewById(R.id.textViewLatitud);

        // Codigo boton localizar para agregar la longitud y latitud a sus editText correspondientes
        botonLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acquire a reference to the system Location Manager
                LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

                // Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.

                        editTextLongitud.setText(""+location.getLongitude());
                        editTextLatitud.setText(""+location.getLatitude());
//                        textViewLongitud.setText("Longitud: "+location.getLongitude());
//                        textViewLatitud.setText("Latitud: "+location.getLatitude());


                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {}

                    public void onProviderDisabled(String provider) {}
                };

                // Register the listener with the Location Manager to receive location updates

                // Comprobamos que la app tiene permisos de GPS
                int comprobarPermisos = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        });
        int comprobarPermisos = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (comprobarPermisos == PackageManager.PERMISSION_DENIED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }





}
