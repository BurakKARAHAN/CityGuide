package net.burakkarahan.cityguide;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.burakkarahan.cityguide.Model.ModelStructureMap;
import net.burakkarahan.cityguide.RestApi.ManagerAll;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapLarge extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentlocationmarker,Place;
    public  static final int REQEST_LOCATİON_CODE=88;
    int sayacc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_large);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        checklocationpermission();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getApplicationContext());
        mMap = googleMap;


        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected  synchronized  void buildGoogleApiClient()
    {
        client=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastlocation=location;

        if (currentlocationmarker!=null)
        {
            currentlocationmarker.remove();
        }

        final LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());

        MarkerOptions kullanici_konum=new MarkerOptions();
        kullanici_konum.position(latLng);
        // Log.i("TAG", "onLocationChanged: "+latLng);
        kullanici_konum.title("Bulunduğunuz Alan");
        kullanici_konum.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentlocationmarker =mMap.addMarker(kullanici_konum);


        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("sharedPreferences",0);
        final int id_structure = Integer.valueOf(sharedPreferences.getString("id_structure",""));
        //Toast.makeText(getApplicationContext(), ""+id_structure, Toast.LENGTH_LONG).show();


        if (sayacc == 0)
        {
            sayacc++;
            Call<ModelStructureMap> request = ManagerAll.getInstance().StructureMap(id_structure);
            request.enqueue(new Callback<ModelStructureMap>() {
                @Override
                public void onResponse(Call<ModelStructureMap> call, Response<ModelStructureMap> response) {

                    Double location1 = Double.parseDouble(response.body().getFirst_location());
                    Double location2 = Double.parseDouble(response.body().getSecond_location());

                    Log.i("ıııı", response.body().getFirst_location());

                    LatLng konum=new LatLng(location1,location2);
                    MarkerOptions  place = new MarkerOptions();
                    place.position(konum);
                    place.title(response.body().getName().toString());
                    //place.icon(BitmapDescriptorFactory.fromResource(R.drawable.place_position));
                    place.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Place = mMap.addMarker(place);

                    CalculationByDistance(latLng,konum);

                }

                @Override
                public void onFailure(Call<ModelStructureMap> call, Throwable t) {
                    //Toast.makeText(getApplicationContext(), "" + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    Log.i("xxxxxx",t.getMessage().toString());
                }
            });

        }

        else
        {

        }


        /*
        LatLng konum=new LatLng(38.254343,27.834384);
        MarkerOptions  market=new MarkerOptions();
        market.position(konum);
        market.title("burakkk deısıkkkk");
        market.icon(BitmapDescriptorFactory.fromResource(R.drawable.place_position));
        burak=mMap.addMarker(market);
        */

        //mMap.animateCamera(CameraUpdateFactory.zoomBy(100));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        if (client != null)
        {
            //LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
            getFusedLocationProviderClient(this).removeLocationUpdates(new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult.getLastLocation()!=null)
                    {
                        Toast.makeText(getApplicationContext(), "Beklenmeyen Hata: Devam ederse lütfen bildiriniz #152562", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest=new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {

            //LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);
            getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest,new LocationCallback(){
                        @Override
                        public void onLocationResult(LocationResult locationResult) {

                            onLocationChanged(locationResult.getLastLocation());
                        }
                    },
                    null);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case REQEST_LOCATİON_CODE:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    //izin verildi
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        if (client ==null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else //izin verilmediyse
                {
                    Toast.makeText(this, "İzin verilmedi", Toast.LENGTH_LONG ).show();
                }
                return;
        }
    }

    public boolean checklocationpermission()
    {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQEST_LOCATİON_CODE);

            }else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQEST_LOCATİON_CODE);
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);
        Log.i("xxxxx", " " + meterInDec );

        return Radius * c;
    }
}
