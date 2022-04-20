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
    void deleteClient(Client client){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENT,COLUMN_ID + " = ? ", new String[] {String.valueOf(client.getCode())} );

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
    void updateClient(Client client){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values  = new ContentValues();
        values.put(COLUMN_NAME,client.getName());
        values.put(COLUMN_PHONE,client.getPhone());
        values.put(COLUMN_EMAIL,client.getEmail());

        db.update(TABLE_CLIENT,values,COLUMN_ID + "  = ? ",
                new String[] {String.valueOf(client.getCode())});
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
        return clients;
    }
}
