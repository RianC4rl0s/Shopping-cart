package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

//import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private static final int VERSION =1;
    private static final String DATABASE = "db_mobile";

    private static final String TABLE_CLIENT = "tb_client";
    private static final String COLUMN_ID = "cl_id";
    private static final String COLUMN_NAME = "cl_name";
    private static final String COLUMN_PHONE = "cl_phone";
    private static final String COLUMN_EMAIL = "cl_email";


    private static final String TABLE_PRODUCT = "tb_product";
    private static final String COLUMN_PRODUCT_ID = "cl_id";
    private static final String COLUMN_PRODUCT_NAME = "cl_name";
    private static final String  COLUMN_PRODUCT_PRICE= "cl_price";
    private static final String COLUMN_PRODUCT_QTD = "cl_qtd";
    private static final String COLUMN_PRODUCT_MARCA = "cl_marca";
    public DataBase( Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String QUERY_TABLE = "CREATE TABLE " + TABLE_CLIENT + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME +  " TEXT,"
                + COLUMN_PHONE + " TEXT, "
                + COLUMN_EMAIL + " TEXT )";
        sqLiteDatabase.execSQL(QUERY_TABLE);
        QUERY_TABLE = "CREATE TABLE " + TABLE_PRODUCT + " ( "
                + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_PRODUCT_NAME +  " TEXT,"
                + COLUMN_PRODUCT_PRICE + " REAL, "
                + COLUMN_PRODUCT_QTD + " INT,"
                + COLUMN_PRODUCT_MARCA + " TEXT )";
        sqLiteDatabase.execSQL(QUERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    
    void addClient(Client client){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values  = new ContentValues();

        values.put(COLUMN_NAME,client.getName());
        values.put(COLUMN_PHONE,client.getPhone());
        values.put(COLUMN_EMAIL,client.getEmail());

        sqLiteDatabase.insert(TABLE_CLIENT,null,values);
        sqLiteDatabase.close();

    }
    void addProduct(Product p){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values  = new ContentValues();

        values.put(COLUMN_PRODUCT_NAME,p.getName());
        values.put(COLUMN_PRODUCT_PRICE,p.getPrice());
        values.put(COLUMN_PRODUCT_QTD,p.getQdt());
        values.put(COLUMN_PRODUCT_MARCA,p.getMarca());

        sqLiteDatabase.insert(TABLE_PRODUCT,null,values);
        sqLiteDatabase.close();

    }
    void deleteClient(Client client){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENT,COLUMN_PRODUCT_ID + " = ? ", new String[] {String.valueOf(client.getCode())} );

    }
    void deleteProduct(Product p){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT,COLUMN_ID + " = ? ", new String[] {String.valueOf(p.getCode())} );

    }
    Client selectClient(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLIENT,new String[] {COLUMN_ID,COLUMN_NAME,COLUMN_PHONE,COLUMN_EMAIL},
                COLUMN_ID + " = ?" , new String[] {String.valueOf(id)},
                null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Client client1 = new Client(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        cursor.close();
        return client1;
    }
    Product selectProduct(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCT,new String[] {COLUMN_PRODUCT_ID,COLUMN_PRODUCT_NAME,COLUMN_PRODUCT_PRICE,COLUMN_PRODUCT_QTD,COLUMN_PRODUCT_MARCA},
                COLUMN_ID + " = ?" , new String[] {String.valueOf(id)},
                null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Product p = new Product(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Double.parseDouble(cursor.getString(2)),Integer.parseInt(cursor.getString(3)),cursor.getString(4));
        cursor.close();
        return p;
    }
    void updateClient(Client client){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values  = new ContentValues();
        values.put(COLUMN_NAME,client.getName());
        values.put(COLUMN_PHONE,client.getPhone());
        values.put(COLUMN_EMAIL,client.getEmail());

        db.update(TABLE_CLIENT,values,COLUMN_ID + "  = ? ",
                new String[] {String.valueOf(client.getCode())});
    }
    void updateProduct(Product p){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values  = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME,p.getName());
        values.put(COLUMN_PRODUCT_PRICE,p.getPrice());
        values.put(COLUMN_PRODUCT_QTD,p.getQdt());
        values.put(COLUMN_PRODUCT_MARCA,p.getQdt());

        db.update(TABLE_CLIENT,values,COLUMN_ID + "  = ? ",
                new String[] {String.valueOf(p.getCode())});
    }
    public List<Client> listaAllClients(){
        List<Client> clients = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CLIENT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                Client client = new Client(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                clients.add(client);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return clients;
    }
    public List<Product> listProducts(){
        List<Product> products = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                Product p = new Product(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Double.parseDouble(cursor.getString(2)),Integer.parseInt(cursor.getString(3)),cursor.getString(4));
                products.add(p);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }
}
