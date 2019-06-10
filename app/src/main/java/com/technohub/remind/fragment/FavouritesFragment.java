package com.technohub.remind.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.technohub.remind.Product;
import com.technohub.remind.R;
import com.technohub.remind.adapter.ProductAdapter;
import com.technohub.remind.database.SqliteDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {

    private SqliteDatabase mDatabase;
    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SqliteDatabase mDatabase = new SqliteDatabase(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);



        RecyclerView productView = (RecyclerView)rootView.findViewById(R.id.product_listf);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);

        mDatabase = new SqliteDatabase(getActivity());
        List<Product> allProducts = mDatabase.listProductsF();

        if (allProducts.size() > 0) {
            productView.setVisibility(View.VISIBLE);
            ProductAdapter mAdapter = new ProductAdapter(getActivity(), allProducts);
            productView.setAdapter(mAdapter);

        } else {
            productView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no Task in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

}
