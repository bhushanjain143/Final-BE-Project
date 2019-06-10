package com.technohub.remind;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.technohub.remind.database.*;
public class AddTaskActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private Spinner spinner1;
   private SqliteDatabase mDatabase;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    EditText nameField;
    EditText quantityField;
    EditText lalong;
    EditText lattude;
    ImageButton loc;
    AppCompatButton Addtadk;
    String placename;
    String latitude1;
    String longitude1;
    String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        final SqliteDatabase mDatabase = new SqliteDatabase(this);
        nameField=(EditText)findViewById(R.id.enter_name);
        quantityField=(EditText)findViewById(R.id.enter_quantity);
        lalong=(EditText)findViewById(R.id.enter_quantityww);
        lattude=(EditText)findViewById(R.id.enter_quantitywww);
        loc=(ImageButton)findViewById(R.id.locationwork);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {

                    startActivityForResult(builder.build(AddTaskActivity.this), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        Addtadk=(AppCompatButton)findViewById(R.id.addtask);
        Addtadk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String priority = spinner1.getSelectedItem().toString();
                final String name = nameField.getText().toString();
                final String quantity = quantityField.getText().toString();
                float  latlong = Float.parseFloat(lalong.getText().toString());
                float  longitude= Float.parseFloat(lattude.getText().toString());
             //   Toast.makeText(AddTaskActivity.this, (int) longitude, Toast.LENGTH_LONG).show();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(quantity) ||  TextUtils.isEmpty(priority) )
                {
                    Toast.makeText(AddTaskActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else
                    {
                    Product newProduct = new Product(name,quantity,latlong,longitude,priority);
                    mDatabase.addProduct(newProduct);

                    //refresh the activity
                    finish();
                    startActivity(getIntent());
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                placename = String.format("%s", place.getName());
                latitude1 = String.valueOf(place.getLatLng().latitude);
                longitude1 = String.valueOf(place.getLatLng().longitude);
                address = String.format("%s", place.getAddress());
                quantityField.setText(placename+","+address);
                lalong.setText(latitude1);
                lattude.setText(longitude1);



            }
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
