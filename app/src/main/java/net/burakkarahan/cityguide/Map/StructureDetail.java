package net.burakkarahan.cityguide.Map;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import net.burakkarahan.cityguide.Account.Login;
import net.burakkarahan.cityguide.Adapter.SliderAdapter;
import net.burakkarahan.cityguide.MapLarge;
import net.burakkarahan.cityguide.Model.ModelFavorite;
import net.burakkarahan.cityguide.Model.ModelFavoriteAdd;
import net.burakkarahan.cityguide.Model.ModelFavoriteDelete;
import net.burakkarahan.cityguide.Model.ModelStructure;
import net.burakkarahan.cityguide.Model.ModelStructureImage;
import net.burakkarahan.cityguide.Model.ModelStructureMap;
import net.burakkarahan.cityguide.R;
import net.burakkarahan.cityguide.RestApi.ManagerAll;

import java.text.DecimalFormat;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class StructureDetail extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentlocationmarker,Place;
    public  static final int REQEST_LOCATİON_CODE=88;

    ViewPager detaySlider;
    CardView cardview1, cardview2, cardview3;
    LinearLayout structure_detail_layout;
    ImageView image, favorite, non_favorite;
    TextView name, city_county, InformationSummary, InformationContinue, Information, InformationChange, InformationSummaryTR, InformationTR, FullMap;
    int sayac = 0;
    int SayacChange = 0;
    String informationTR, informationENG;
    String nameTR, nameENG;
    int sayacc = 0;
    List<ModelStructureImage> list;
    SliderAdapter sliderAdapter;
    CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure_detail);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Tanimla();
        checklocationpermission();
        InformationVisible();
        InformationChange();
        Call();
        getImage();

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("sharedPreferences",0);
        final int id_structure = Integer.valueOf(sharedPreferences.getString("id_structure",""));

        SharedPreferences sharedPreferencess = getApplication().getSharedPreferences("login",0);
        final int id_user = Integer.valueOf(sharedPreferencess.getString("id_user",""));


        Favorite(id_user, id_structure);

        non_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteAdd(id_user, id_structure);

                favorite.setVisibility(View.VISIBLE);
                non_favorite.setVisibility(View.GONE);
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteDelete(id_user, id_structure);

                favorite.setVisibility(View.GONE);
                non_favorite.setVisibility(View.VISIBLE);
            }
        });

        FullMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StructureDetail.this, MapLarge.class);
                startActivity(i);
            }
        });

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

                    //CalculationByDistance(latLng,konum);

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
                        Toast.makeText(StructureDetail.this, "Beklenmeyen Hata: Devam ederse lütfen bildiriniz #152562", Toast.LENGTH_SHORT).show();
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

    public void Tanimla()
    {
        image = (ImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
        city_county = (TextView) findViewById(R.id.city_county);
        InformationSummary = (TextView) findViewById(R.id.InformationSummary);
        InformationContinue = (TextView) findViewById(R.id.InformationContinue);
        Information = (TextView) findViewById(R.id.Information);
        favorite = (ImageView) findViewById(R.id.favorite);
        non_favorite = (ImageView) findViewById(R.id.non_favorite);
        InformationChange = (TextView) findViewById(R.id.InformationChange);
        InformationSummaryTR = (TextView) findViewById(R.id.InformationSummaryTR);
        InformationTR = (TextView) findViewById(R.id.InformationTR);
        structure_detail_layout = (LinearLayout) findViewById(R.id.structure_detail_layout);
        cardview1 = (CardView) findViewById(R.id.cardview1);
        cardview2 = (CardView) findViewById(R.id.cardview2);
        cardview3 = (CardView) findViewById(R.id.cardview3);
        detaySlider = (ViewPager) findViewById(R.id.detaySlider);
        circleIndicator = (CircleIndicator) findViewById(R.id.sliderNokta);
        FullMap = (TextView) findViewById(R.id.FullMap);

    }

    public void InformationVisible()
    {
        InformationContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sayac++;

                if(sayac % 2 == 1)
                {
                    InformationSummary.setVisibility(View.GONE);
                    InformationContinue.setVisibility(View.VISIBLE);
                    Information.setVisibility(View.VISIBLE);
                    InformationContinue.setText("HIDE");
                }
                else
                {
                    InformationSummary.setVisibility(View.VISIBLE);
                    InformationContinue.setVisibility(View.VISIBLE);
                    Information.setVisibility(View.GONE);
                    InformationContinue.setText("CONTINUE");
                }
            }
        });
    }

    public void InformationChange()
    {
        InformationChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SayacChange++;
                if (SayacChange % 2 == 1)
                {
                    Information.setText(informationTR);
                    InformationSummary.setText(informationTR);
                    name.setText(nameTR);
                }
                else
                {
                    Information.setText(informationENG);
                    InformationSummary.setText(informationENG);
                    name.setText(nameENG);
                }
            }
        });
    }

    public void Favorite(int id_user, int id_structure)
    {

        Call<ModelFavorite> request = ManagerAll.getInstance().Favorite(id_user, id_structure);
        request.enqueue(new Callback<ModelFavorite>() {
            @Override
            public void onResponse(Call<ModelFavorite> call, Response<ModelFavorite> response) {

                if (response.body().isTf() == true)
                {
                    favorite.setVisibility(View.VISIBLE);
                    non_favorite.setVisibility(View.GONE);
                }
                else
                {
                    favorite.setVisibility(View.GONE);
                    non_favorite.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ModelFavorite> call, Throwable t) {

            }
        });
    }

    public void FavoriteAdd(int id_user, int id_structure)
    {
        Call<ModelFavoriteAdd> request = ManagerAll.getInstance().FavoriteAdd(id_user, id_structure);
        request.enqueue(new Callback<ModelFavoriteAdd>() {
            @Override
            public void onResponse(Call<ModelFavoriteAdd> call, Response<ModelFavoriteAdd> response) {

            }

            @Override
            public void onFailure(Call<ModelFavoriteAdd> call, Throwable t) {

            }
        });
    }

    public void FavoriteDelete(int id_user, int id_structure)
    {
        Call<ModelFavoriteDelete> request = ManagerAll.getInstance().FavoriteDelete(id_user, id_structure);
        request.enqueue(new Callback<ModelFavoriteDelete>() {
            @Override
            public void onResponse(Call<ModelFavoriteDelete> call, Response<ModelFavoriteDelete> response) {

            }

            @Override
            public void onFailure(Call<ModelFavoriteDelete> call, Throwable t) {

            }
        });
    }

    public void Call()
    {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("sharedPreferences",0);
        final int id_structure = Integer.valueOf(sharedPreferences.getString("id_structure",""));

        Call<ModelStructureMap> r = ManagerAll.getInstance().StructureMap(id_structure);
        r.enqueue(new Callback<ModelStructureMap>() {
            @Override
            public void onResponse(Call<ModelStructureMap> call, Response<ModelStructureMap> response) {

                Picasso.with(getApplication()).load("http://www.cityguide.ml/StructureImage/" + response.body().getImage()).into(image);
                city_county.setText(response.body().getCity() + "/" + response.body().getCounty());

                name.setText(response.body().getName());
                InformationSummary.setText(response.body().getInformation());
                Information.setText(response.body().getInformation());

                informationTR = response.body().getInformation_tr();
                nameTR = response.body().getName_tr();

                informationENG = response.body().getInformation();
                nameENG = response.body().getName();

            }

            @Override
            public void onFailure(Call<ModelStructureMap> call, Throwable t) {

            }
        });
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

        return Radius * c;
    }

    public void getImage ()
    {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("sharedPreferences",0);
        final int id_structure = Integer.valueOf(sharedPreferences.getString("id_structure",""));

        final Call<List<ModelStructureImage>> request = ManagerAll.getInstance().StructureImage(id_structure);
        request.enqueue(new Callback<List<ModelStructureImage>>() {
            @Override
            public void onResponse(Call<List<ModelStructureImage>> call, Response<List<ModelStructureImage>> response) {
                list = response.body();
                sliderAdapter = new SliderAdapter(list,getApplicationContext());
                detaySlider.setAdapter(sliderAdapter);
                circleIndicator.setViewPager(detaySlider);
                circleIndicator.bringToFront();
            }

            @Override
            public void onFailure(Call<List<ModelStructureImage>> call, Throwable t) {

            }
        });
    }

}