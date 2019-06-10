package com.technohub.remind.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.technohub.remind.Product;
import com.technohub.remind.R;
import com.technohub.remind.database.SqliteDatabase;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{
    private Spinner spinner1;
    private Context context;
    private List<Product> listProducts;

    private SqliteDatabase mDatabase;

    public ProductAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product singleProduct = listProducts.get(position);

        holder.name.setText(singleProduct.getName());

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteProduct(singleProduct.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }


    private void editTaskDialog(final Product product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.editlayout, null);



        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        final EditText lalong = (EditText)subView.findViewById(R.id.enter_quantityww);
        final  EditText latilong= (EditText)subView.findViewById(R.id.enter_quantitywww);
        spinner1 = (Spinner)subView.findViewById(R.id.spinner1);
        if(product != null)
        {
            nameField.setText(product.getName());
            quantityField.setText(String.valueOf(product.getQuantity()));
            lalong.setText(String.valueOf(product.getLatlong()));
            latilong.setText(String.valueOf(product.getLongitude()));

         //   latilong.setText(String.valueOf(product.getLongitude()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String quantity = quantityField.getText().toString();
                float latlong= Float.parseFloat(lalong.getText().toString());
                float latitude= Float.parseFloat(latilong.getText().toString());
                final String priority = spinner1.getSelectedItem().toString();

                if(TextUtils.isEmpty(name) ||  TextUtils.isEmpty(quantity) || TextUtils.isEmpty(priority)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateProduct(new Product(product.getId(), name, quantity,latlong,latitude,priority));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
