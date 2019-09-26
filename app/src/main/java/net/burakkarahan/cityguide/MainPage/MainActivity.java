package net.burakkarahan.cityguide.MainPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.burakkarahan.cityguide.Account.Login;
import net.burakkarahan.cityguide.Fragment.FragmentPlace;
import net.burakkarahan.cityguide.Fragment.FragmentFavorite;
import net.burakkarahan.cityguide.Fragment.FragmentProfile;
import net.burakkarahan.cityguide.Fragment.FragmentTravel;
import net.burakkarahan.cityguide.Map.StructureDetail;
import net.burakkarahan.cityguide.Model.ModelStructureIdFind;
import net.burakkarahan.cityguide.Model.ModelTravelAdd;
import net.burakkarahan.cityguide.Model.ModelUserInformation;
import net.burakkarahan.cityguide.R;
import net.burakkarahan.cityguide.RestApi.ManagerAll;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MainActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 100;
    SharedPreferences sharedPref;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FloatingActionButton fab;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.getTabAt(0).setIcon(R.drawable.icon_place);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_favorite);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_profile);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon_travel);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //Camera Button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                SharedPreferences sharedPreferencess = getApplicationContext().getSharedPreferences("login",0);
                final int id_user = Integer.valueOf(sharedPreferencess.getString("id_user",""));

                Call<ModelUserInformation> request = ManagerAll.getInstance().UserInformation(id_user);
                request.enqueue(new Callback<ModelUserInformation>() {
                    @Override
                    public void onResponse(Call<ModelUserInformation> call, Response<ModelUserInformation> response) {

                        int status = Integer.valueOf(response.body().getStatus());

                        if (status == 1)
                        {
                            pDialog.cancel();
                            //Intent intent = new Intent(MainActivity.this, Camera.class);
                            //startActivity(intent);

                            pDialog.show();
                            //openImageChooser();
                            pDialog.cancel();

                        }
                        else
                        {
                            pDialog.cancel();
                            SweetAlertDialog sa = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                            sa.setTitleText("Wrong!");
                            sa.setContentText("Please enter the activation code sent to your email in the appropriate location in the profile");
                            sa.setConfirmText("OK");
                            sa.setConfirmClickListener(null);
                            sa.setCancelable(false);
                            sa.show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ModelUserInformation> call, Throwable t) {

                    }
                });

            }
        });

    }

    //Select Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Dark Thema
        if (id == R.id.action_darkthema)
        {
            sharedPref = getApplicationContext().getSharedPreferences("thema",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("thema","dark");
            editor.commit();

            //Intent i = new Intent(MainActivity.this,MainActivity.class);
            //startActivity(i);

        }
        //Light Thema
        else if (id == R.id.action_lightthema)
        {
            sharedPref = getApplicationContext().getSharedPreferences("thema",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("thema","light");
            editor.commit();

            //Intent i = new Intent(MainActivity.this,MainActivity.class);
            //startActivity(i);

        }
        //Logout
        else if (id == R.id.action_logout) {

            SweetAlertDialog sa = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
            sa.setCancelable(false);
            sa.setTitleText("Wrong!");
            sa.setContentText("Are you sure you want to log out?");
            sa.setConfirmText("Yes");
            sa.setCancelClickListener(null);
            sa.setCancelText("No");
            sa.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("login",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.commit();

                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);

                    finish();

                }
            });
            sa.show();
        }
        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment
    {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() { }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0){

                FragmentPlace FragmentAnasayfa = new FragmentPlace();
                return FragmentAnasayfa;
            }

            if (position == 1){

                FragmentFavorite FragmentFavorite = new FragmentFavorite();
                return FragmentFavorite;
            }

            if (position == 2){

                FragmentProfile FragmentProfile = new FragmentProfile();
                return FragmentProfile;
            }


            if (position == 3){

                FragmentTravel FragmentTravel = new FragmentTravel();
                return FragmentTravel;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onBackPressed() {

        SweetAlertDialog sa = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
        sa.setCancelable(false);
        sa.setTitleText("Wrong!");
        sa.setContentText("Are you sure you want to log out?");
        sa.setConfirmText("Yes");
        sa.setCancelClickListener(null);
        sa.setCancelText("No");
        sa.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);

                finish();
            }
        });
        sa.show();
    }
}
