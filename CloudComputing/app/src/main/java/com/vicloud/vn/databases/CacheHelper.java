package com.vicloud.vn.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vicloud.vn.models.Server;

import java.util.ArrayList;

/**
 * Created by huunc on 7/22/16.
 */
public class CacheHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "serversManager";
    private static final String TABLE_SERVER= "server";
    private static final String INSTANCE_ID = "instance_id";
    private static final String INSTANCE_NAME = "display_name";
    private static final String IP = "ip_public";
    private static final String STATUS = "status";
    private static final String IMAGE_NAME = "image";
    private static final String MAC_ADDRESS = "mac";
    private static final String FLAVOR = "flavor";
    private static final String VM_STATE = "vm_state";

    public CacheHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * CREATE TABLE
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INSTANCE_TABLE = "CREATE TABLE " + TABLE_SERVER + "(id integer primary key,"
                + INSTANCE_ID + " TEXT," + INSTANCE_NAME + " TEXT,"
                + IP + " TEXT," + STATUS + " TEXT, " + IMAGE_NAME + " TEXT,"
                + MAC_ADDRESS + " TEXT," + FLAVOR + " TEXT," + VM_STATE + " TEXT" + ")";
        db.execSQL(CREATE_INSTANCE_TABLE);
    }
    /**
     * UPGRADE TABLE
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVER);
        onCreate(db);
    }

    public void addServer(Server server) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INSTANCE_ID, server.getServerID());
        values.put(INSTANCE_NAME, server.getServerName());
        values.put(STATUS, server.getStatus());
        values.put(IP, server.getIp());
        values.put(IMAGE_NAME, server.getImageName());
        values.put(MAC_ADDRESS, server.getAddressMac());
        values.put(FLAVOR, server.getFlavor());
        values.put(VM_STATE, server.getVmState());
        db.insert(TABLE_SERVER, null, values); //Insert query to store the record in the database
        db.close();
    }

    /*getUser() will return he user's object if id matches*/
    public Server getServer(String instance_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SERVER, new String[]{INSTANCE_ID,
                        INSTANCE_NAME, STATUS, IP, IMAGE_NAME, MAC_ADDRESS,
        FLAVOR, VM_STATE, TABLE_SERVER}, INSTANCE_ID + "=?",
                new String[]{instance_id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Server server = new Server(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8));
        return server;
    }

    /*getAllUsers() will return the list of all users*/
    public ArrayList<Server> getAllServer() {
        ArrayList<Server> usersList = new ArrayList<Server>();
        String selectQuery = "SELECT  * FROM " + TABLE_SERVER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String instance_id = cursor.getString(cursor.getColumnIndex(INSTANCE_ID));
                String instance_name = cursor.getString(cursor.getColumnIndex(INSTANCE_NAME));
                String mac = cursor.getString(cursor.getColumnIndex(MAC_ADDRESS));
                String ip = cursor.getString(cursor.getColumnIndex(IP));
                String flavor = cursor.getString(cursor.getColumnIndex(FLAVOR));
                String image = cursor.getString(cursor.getColumnIndex(IMAGE_NAME));
                String status = cursor.getString(cursor.getColumnIndex(STATUS));
                String vm_state = cursor.getString(cursor.getColumnIndex(VM_STATE));
                Server user = new Server(instance_name, instance_id, status,ip,image,mac,flavor,vm_state);
                usersList.add(user);
            } while (cursor.moveToNext());
        }
        return usersList;
    }
    /*getUsersCount() will give the total number of records in the table*/
    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SERVER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

//    /*updateUser() will be used to update the existing user record*/
//    public int updateUser(Server server) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, user.getName());
//        values.put(KEY_EMAIL, user.getEmail());
//        // updating record
//        return db.update(TABLE_SERVER, values, INSTANCE_ID + " = ?", // update query to make changes to the existing record
//                new String[]{String.valueOf(server.getServerID())});
//    }

    /*deleteContact() to delete the record from the table*/
    public void deleteContact(Server server) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SERVER, INSTANCE_ID + " = ?",
                new String[]{server.getServerID()});
        db.close();
    }

}
