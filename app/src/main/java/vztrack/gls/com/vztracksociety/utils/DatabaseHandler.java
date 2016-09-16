package vztrack.gls.com.vztracksociety.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import vztrack.gls.com.vztracksociety.profile.FlatsList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "FlatList";
    private static final String TABLE_CONTACTS = "FlatNumbers";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "FlatName";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


//    public Cursor getDataByFlat(String FlatNo){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select "+KEY_NAME+" from "+TABLE_CONTACTS+" where id="+FlatNo+"", null );
//        return res;
//    }


    // code to add the new contact
    public void addFlatList(FlatsList flatsList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, flatsList.getFlats()); // Flat Number

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to activity_get_details all contacts in a list view
    public List<FlatsList> getAllFlatList() {
        List<FlatsList> contactList = new ArrayList<FlatsList>();
        contactList.clear();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FlatsList contact = new FlatsList();
                contact.setFlats(cursor.getString(1));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
        //contactList.clear();
    }

    public String getFlatInfo(String flatNo) {
        String flatInfo = null;
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +" where FlatName like '"+flatNo+"-%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                flatInfo = cursor.getString(1);
            } while (cursor.moveToNext());
        }

        // return contact list
        return flatInfo;
        //contactList.clear();
    }

    // Deleting all records from table
    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_CONTACTS);
        db.close();
    }

}