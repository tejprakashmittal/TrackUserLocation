package com.example.tez.trackuserlocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    EditText editText;

    private GoogleMap mMap;
    private Location lastlocation;
    private Marker currentLocationMarker;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private static final int REQUEST_LOCATION_CODE=99;
    private DatabaseReference myDatabaseReference;
    private Button btnSendLoc;
    private LatLng latLng1,latLng2;
    private TextView txtName,txtRollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        myDatabaseReference= FirebaseDatabase.getInstance().getReference("Attendance");
        btnSendLoc=findViewById(R.id.btnSend);
        txtName=findViewById(R.id.xmlTextName);
        txtRollno=findViewById(R.id.xmlTextRollno);

        btnSendLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String rollno_string = extras.getString("EXTRA_RollNo");
                String name_string = extras.getString("EXTRA_Name");
                String status="";
                if(latLng1==latLng2)
                    status="Present";
                else
                    status="Absent";

                   if(!(TextUtils.isEmpty(rollno_string))||(TextUtils.isEmpty(name_string))){
                       String id=myDatabaseReference.push().getKey();
                       Attendance_Data attendance_data=new Attendance_Data(rollno_string,name_string,status);
                       myDatabaseReference.child(id).setValue(attendance_data);
                       Toast.makeText(getApplicationContext(),"Attendance sent successfully",Toast.LENGTH_LONG).show();
                   }
                   else {
                       Toast.makeText(getApplicationContext(),"Please click on location button",Toast.LENGTH_LONG).show();
                   }
               }

        });



        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void onClick(View view)
    {
        List<Address> addressList = null;
        if(view.getId()==R.id.btnFind)
        {
            editText=findViewById(R.id.TF_Location);
            String location=editText.getText().toString();

            MarkerOptions markerOptions=new MarkerOptions();

            if(!location.equals(""))
            {
                Geocoder geocoder=new Geocoder(this);
                try {
                    addressList=geocoder.getFromLocationName(location,5);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<addressList.size();i++)
                {
                    Address address=addressList.get(i);
                    latLng1=new LatLng(address.getLatitude(),address.getLongitude());
                    markerOptions.position(latLng1);
                    markerOptions.title(""+latLng1);
                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
                    Toast.makeText(this, ""+location, Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        if(client==null)
                            buildGoogleClient();
                        mMap.setMyLocationEnabled(true);

                    }
                }
                else
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            buildGoogleClient();
            mMap.setMyLocationEnabled(true);
            return;
        }

    }

    protected synchronized void buildGoogleClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastlocation=location;
        if(currentLocationMarker!=null)
             currentLocationMarker.remove();
        latLng2=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng2);
        markerOptions.title(""+latLng2);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        currentLocationMarker=mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(50));
        Toast.makeText(this, "LatLong : "+latLng2, Toast.LENGTH_SHORT).show();

        if(client!=null)
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);            // to handle the case where the user grants the permission. See the documentation
            return;
        }

    }

    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);

            }
            else
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            return false;
        }
        else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
