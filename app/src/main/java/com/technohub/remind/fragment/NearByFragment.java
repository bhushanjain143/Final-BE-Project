package com.technohub.remind.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.technohub.remind.Product;
import com.technohub.remind.R;
import com.technohub.remind.adapter.ProductAdapter;
import com.technohub.remind.database.SqliteDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearByFragment extends Fragment implements  OnMapReadyCallback {
    private SqliteDatabase mDatabase;
    GoogleMap mGoogleMap;
    MapView mMapView;
    View v;
    public NearByFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        SqliteDatabase mDatabase = new SqliteDatabase(getActivity());
     v = inflater.inflate(R.layout.fragment_near_by, container, false);


        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mDatabase = new SqliteDatabase(getActivity());
        List<Product> allProducts = mDatabase.listProductsp();
        MapsInitializer.initialize(getActivity().getApplicationContext());
        mGoogleMap = googleMap;

        if (allProducts.size() > 0) {

            int a=allProducts.size();
            for(int i=0;i<a;i++) {
                final Product singleProduct = allProducts.get(i);

                String loc=singleProduct.getName();
               float value1=singleProduct.getLatlong();
               // double value1 = Double.parseDouble(ll);

                float value2=singleProduct.getLatlong();
              //  double value2 = Double.parseDouble(ll1);

                LatLng triCity = new LatLng(value1, value2);
                mGoogleMap.addMarker(new MarkerOptions().position(triCity).title(loc));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(triCity, 11));
            }

        } else
        {

            Toast.makeText(getActivity(), "There is no Task in the database. Start adding now", Toast.LENGTH_LONG).show();
            LatLng triCity = new LatLng(54.4158773,18.6337789);
            mGoogleMap.addMarker(new MarkerOptions().position(triCity).title("No Task"));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(triCity, 11));
        }


    }

}
