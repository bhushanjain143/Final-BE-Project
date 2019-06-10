package com.technohub.remind.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.technohub.remind.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SqliteDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String	DATABASE_NAME = "MAPTASK";
    private	static final String TABLE_PRODUCTS = "Task";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "Task";
    public static final String COL_3 = "Location";
    public static final String COL_4 = "latitude";
    public static final String COL_5 = "longitude";
    public static final String COL_6 = "Priority";
    public static final String COL_7 = "Createdate";
    public static final String COL_8 = "Status";
    public static final String COL_9 = "TaskStatus";
    public static final String COL_10 = "Taskupdate";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_PRODUCTS_TABLE = "CREATE	TABLE " + TABLE_PRODUCTS + "(" + COL_1 + "  INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT," + COL_3 + " TEXT," +COL_4 +" FLOAT( 10, 8 ) NOT NULL,"+COL_5 +" FLOAT( 10, 8 ) NOT NULL,"+COL_6 +" TEXT,"+COL_7+" DATETIME DEFAULT CURRENT_TIMESTAMP,"+COL_8+" TEXT,"+COL_9+" TEXT,"+COL_10+"  DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public List<Product> listProducts(){
        String pp ="pending";
        String sql = "select * from Task where Status='pending' "  ;
        Log.d("locationtesting", "accuracy: " +  sql);
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                Log.d("locationtesting", "accuracysushant: " + id);
                String name = cursor.getString(1);
                String quantity = cursor.getString(2);
                float latlong = Float.parseFloat(cursor.getString(3));
                float longitude= Float.parseFloat(cursor.getString(4));

                String priority = cursor.getString(5);
                Log.d("locationtesting", "accuracysushant: " + latlong);
                Log.d("locationtesting", "accuracysushant: " + longitude);
                storeProducts.add(new Product(id, name, quantity,latlong,longitude,priority));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }
    public List<Product> listProductsp(){
        String pp ="pending";
        String sql = "select * from Task where Status='pending' "  ;

        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String quantity = cursor.getString(2);
                float latlong = Float.parseFloat(cursor.getString(3));
                String priority = cursor.getString(5);
                float longitude = Float.parseFloat(cursor.getString(4));
                storeProducts.add(new Product(id, name, quantity,latlong,longitude,priority));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }
    public List<Product> listProductsC(){
        String pp ="Completed";
        String sql = "select * from Task where Status='Completed'"   ;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String quantity = cursor.getString(2);
                Log.d("locationtesting", "Complted Work: " +  quantity);
                float latlong = Float.parseFloat(cursor.getString(3));
                String priority = cursor.getString(5);
                float longitude = Float.parseFloat(cursor.getString(4));
                storeProducts.add(new Product(id, name, quantity,latlong,longitude,priority));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }
    public List<Product> listProductsF(){
        String sql = "select * from " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String quantity = cursor.getString(2);
                float latlong = Float.parseFloat(cursor.getString(3));
                String priority = cursor.getString(5);
               float longitude = Float.parseFloat(cursor.getString(4));
                storeProducts.add(new Product(id, name, quantity,latlong,longitude,priority));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }
    public void addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COL_2, product.getName());
        values.put(COL_3, product.getQuantity());
        values.put(COL_4, product.getLatlong());
        values.put(COL_5, product.getLongitude());
        values.put(COL_6, product.getPriority());
        values.put(COL_7, getDateTime());
        values.put(COL_8, "pending");
        values.put(COL_9, "pending");
        values.put(COL_10, getDateTime());

        Log.d("locationtesting", "Sushant: " +  product.getLongitude());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
    }
    private String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public void updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COL_2, product.getName());
        values.put(COL_3, product.getQuantity());
        values.put(COL_4,product.getLatlong());
        values.put(COL_5,product.getLongitude());
        values.put(COL_6,product.getPriority());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRODUCTS, values, COL_1	+ "	= ?", new String[] { String.valueOf(product.getId())});
    }

    public Product findProduct(String name){
        String query = "Select * FROM "	+ TABLE_PRODUCTS + " WHERE " + COL_2 + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Product mProduct = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String productName = cursor.getString(1);
            String productQuantity =cursor.getString(2);
            float latlong = Float.parseFloat(cursor.getString(3));
            String priority = cursor.getString(5);
            float latilong = Float.parseFloat(cursor.getString(4));
            mProduct = new Product(id, productName, productQuantity,latlong,latilong,priority);
        }
        cursor.close();
        return mProduct;
    }

    public void deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COL_1	+ "	= ?", new String[] { String.valueOf(id)});
    }

}
