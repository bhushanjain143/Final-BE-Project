package com.technohub.remind;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.technohub.remind.database.SqliteDatabase;
import com.technohub.remind.fragment.ActiveTaskFragment;
import com.technohub.remind.fragment.CompletedFragment;
import com.technohub.remind.fragment.FavouritesFragment;
import com.technohub.remind.fragment.FeaturesFragment;
import com.technohub.remind.fragment.HomeFragment;
import com.technohub.remind.fragment.NearByFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, TextToSpeech.OnInitListener {
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private SqliteDatabase mDatabase;
    private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;
    TextToSpeech t1;
    private TextToSpeech tts;
    String spk;
    String sp;
    AudioManager am;
    SQLiteDatabase db,db1;
    private static final String TABLE_PRODUCTS = "Task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }
    //speak the user text
    private void speakWords(String speech) {

        //speak straight away
        Toast.makeText(this, speech, Toast.LENGTH_SHORT).show();
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)

    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            HomeFragment cameraImportFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, cameraImportFragment)
                    .addToBackStack(null)
                    .commit();
            // Handle the camera action
        } else if (id == R.id.Activetask) {
            ActiveTaskFragment activeTaskFragment = new ActiveTaskFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, activeTaskFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.NearBy) {
            NearByFragment nearByFragment = new NearByFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, nearByFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.completed) {
            CompletedFragment completedFragment = new CompletedFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, completedFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.favourites) {
            FavouritesFragment favouritesFragment = new FavouritesFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, favouritesFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.settings) {
          /*  SettingsFragment settingsFragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, settingsFragment)
                    .addToBackStack(null)
                    .commit();*/
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.Features) {
            FeaturesFragment featuresFragment = new FeaturesFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, featuresFragment)
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
        if (mGoogleApiClient != null) {
            //  LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }


    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        //  Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        //mLocationRequest.setSmallestDisplacement(0.1F);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("locationtesting", "accuracy: " + location.getAccuracy() + " lat: " + location.getLatitude() + " lon: " + location.getLongitude());
        float latitude = Float.parseFloat(String.valueOf(location.getLatitude()));
        float longitude = Float.parseFloat(String.valueOf(location.getLongitude()));

        // Toast.makeText(this,"Location Changed  Start",Toast.LENGTH_SHORT).show();
        updateData(latitude, longitude);


    }


    public void updateData(float latitude, float longitude) {


        String querycheck = "SELECT name FROM sqlite_master WHERE type='table' AND name='Task';";
        SQLiteDatabase db1 = openOrCreateDatabase("MAPTASK", Context.MODE_PRIVATE, null);
        Cursor res1 = db1.rawQuery(querycheck, null);
        if (res1.getCount() == 0) {
            Toast.makeText(this, "Task Not Created ", Toast.LENGTH_SHORT).show();

        } else {

                     double lati1=latitude-0.0003;
                            double lati2=latitude+0.0003;
                    double loti1=longitude-0.0003;
                    double loti2=longitude+0.0003;

            String query = "SELECT * FROM Task WHERE (latitude  BETWEEN '"+lati1+"' AND '"+lati2+"' ) AND  (longitude BETWEEN '"+loti1+"' AND '"+loti2+"') AND STATUS='pending'";

            Log.d("locationtesting", "accuracy: " + query);
            Toast.makeText(this, "Waiting for target", Toast.LENGTH_SHORT).show();
             db = openOrCreateDatabase("MAPTASK", Context.MODE_PRIVATE, null);
            db1 = openOrCreateDatabase("MAPTASK", Context.MODE_PRIVATE, null);
            Cursor res = db.rawQuery(query, null);


            //  Toast.makeText(this,aa,Toast.LENGTH_SHORT).show();
            if (res.getCount() == 0) {
                Toast.makeText(this, "Location Changed Not Found ", Toast.LENGTH_SHORT).show();

            } else {
                if (res.moveToFirst()) {
                    //looping through all the records
                    do {
                        //pushing each record in the employee list
                       final int id = Integer.parseInt(res.getString(0));

                       float l1= Float.parseFloat(res.getString(3));
                       float l2=Float.parseFloat(res.getString(4));
                        Log.d("Welcome12", String.valueOf(l1));
                        Log.d("Welcome12", String.valueOf(l2));

                        spk = "Completed" + res.getString(2);
                        sp = res.getString(5);
                        myTTS.speak(spk, TextToSpeech.QUEUE_FLUSH, null);
                        LayoutInflater inflater = LayoutInflater.from(this);
                        View subView = inflater.inflate(R.layout.task, null);


                      //  final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
                       // final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
                      //  final EditText lalong = (EditText)subView.findViewById(R.id.enter_quantityww);
                      //  final  EditText latilong= (EditText)subView.findViewById(R.id.enter_quantitywww);
                       // final Spinner spinner1 = (Spinner)subView.findViewById(R.id.spinner1);

                     //   nameField.setText(id);
                      //  quantityField.setText(spk);
                       // lalong.setText(sp);
                       // latilong.setText(String.valueOf(product.getLongitude()));

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Alert");
                        builder.setView(subView);
                        builder.create();

                        final SQLiteDatabase finalDb = db1;
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {



                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               // final String pp = lalong.getText().toString();
                               // final String ss = quantityField.getText().toString();
                              //  final int idc = Integer.parseInt(nameField.getText().toString());


                                String qupdate = "UPDATE Task SET Status ='Completed',TaskStatus = 'Completed',Taskupdate='" + getDateTime() + "' WHERE id = '" + id + "'";
                                finalDb.execSQL(qupdate);

                                finish();
                                startActivity(getIntent());





                                }

                        });

                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(getIntent());
                            }
                        });
                        builder.show();





                    } while (res.moveToNext());
                }

            }


        }


        // db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });

    }

    private String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    public void onInit(int initStatus) {

        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
            }
            else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }
}