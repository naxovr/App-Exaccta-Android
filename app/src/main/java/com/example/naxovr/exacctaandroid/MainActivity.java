package com.example.naxovr.exacctaandroid;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.naxovr.exacctaandroid.bd_ubicaciones.SQLHelper;
import com.example.naxovr.exacctaandroid.bd_ubicaciones.UbicacionDB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Cambio el nombre de la actionbar
        getSupportActionBar().setTitle("App Busca Embajadas");

        // Instanciamos todos los componentes
        Button botonLocalizar = findViewById(R.id.buttonLocalizame);
        Button botonBuscar = findViewById(R.id.buttonBuscar);
        Button botonHistorial = findViewById(R.id.buttonHistorial);
        Button botonMapa = findViewById(R.id.buttonMapa);
        final EditText editTextLongitud = findViewById(R.id.editTextLongitud);
        final EditText editTextLatitud = findViewById(R.id.editTextLatitud);
        final TextView textViewLongitud = findViewById(R.id.textViewLongitud);
        final TextView textViewLatitud = findViewById(R.id.textViewLatitud);
        final TextView textViewFecha = findViewById(R.id.textViewFecha);

        final SQLHelper sqlHelper = new SQLHelper(this);


        // Codigo boton localizar para agregar la longitud y latitud a sus editText correspondientes
        botonLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Buscando tu ubicación...",Toast.LENGTH_SHORT).show();

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


                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:MM", Locale.getDefault());
                        Date date = new Date();

                        String fecha = dateFormat.format(date);

                        textViewFecha.setText(fecha);

                     
                    //Toast.makeText(getApplicationContext(),fecha,Toast.LENGTH_LONG).show();

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {}

                    public void onProviderDisabled(String provider) {}
                    
                    
                    
                };


                // Comprobamos que la app tiene permisos de GPS
                int comprobarPermisos = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);





            }
        });



        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextLongitud.getText().toString().isEmpty()||editTextLatitud.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Rellena la longitud y latitud manualmente o " +
                            "pulsa el botón Localízame",Toast.LENGTH_LONG).show();

                }
                else{

                    // Gets the data repository in write mode
                    SQLiteDatabase db = sqlHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put(UbicacionDB.COLUMNA_LONGITUD, editTextLatitud.getText().toString());
                    values.put(UbicacionDB.COLUMNA_LATITUD, editTextLongitud.getText().toString());

// Insert the new row, returning the primary key value of the new row
                    long newRowId = db.insert(UbicacionDB.TABLE_NAME, null, values);
                    Toast.makeText(getApplicationContext(),"Metido en base de datos",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                }


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

     botonHistorial.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(MainActivity.this, HistorialActivity.class);
             startActivity(intent);
         }
     });

     botonMapa.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(editTextLongitud.getText().toString()));
             Uri.parse(editTextLatitud.getText().toString());
             Bundle bundle = new Bundle();
             bundle.putString("coordenadas",""+editTextLongitud.getText()+""+editTextLatitud.getText());
             intent.putExtras(bundle);
             startActivity(intent);
         }
     });

    }
}
