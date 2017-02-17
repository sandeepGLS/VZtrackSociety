package vztrack.gls.com.vztracksociety.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vztrack.gls.com.vztracksociety.beans.VisitPurposeBean;
import vztrack.gls.com.vztracksociety.profile.FlatsList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 22;
    private static final String DATABASE_NAME = "FlatList";
    private static final String TABLE_CONTACTS = "FlatNumbers";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "FlatName";

    private static final String TABLE = "purposes";
    private static final String KEY_NO = "id";
    private static final String KEY_ENGLISH = "englishText";
    private static final String KEY_HINDI = "hindiText";
    private static final String KEY_MARATHI = "marathiText";

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

        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_NO + " INTEGER PRIMARY KEY, " + KEY_ENGLISH + " TEXT, "+KEY_HINDI+" TEXT, "+KEY_MARATHI+" TEXT "
                + ")";

        Log.e("CREATE_TABLE "," "+CREATE_TABLE);

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }


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

    public int getFlatCount() {
        int rowCnt = 0;
        String getRowCountQuery = "select count(*) as count from "+TABLE_CONTACTS+";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(getRowCountQuery, null);
        if (cursor.moveToFirst()) {
            do {
                rowCnt = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return rowCnt;
    }

    public String getFlatInfo(String flatNo) {
        String flatInfo = null;
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +" where FlatName like '"+flatNo.trim()+"-%'";
        Log.e("QUERY "," "+selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                flatInfo = cursor.getString(1);
            } while (cursor.moveToNext());
        }
        Log.e("GETTED "," "+flatInfo);
        return flatInfo;
    }

    // Deleting all records from table
    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_CONTACTS);
        db.close();
    }

    public int getPurposeTableCount() {
        int rowCnt = 0;
        String getRowCountQuery = "select count(*) as count from "+TABLE+";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(getRowCountQuery, null);
        if (cursor.moveToFirst()) {
            do {
                rowCnt = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        Log.e("GETTED "," "+rowCnt);
        return rowCnt;
    }

    public void insertIntoPurpose(String[] english , String[] hindi, String[] marathi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i=0;i<english.length;i++){
            values.put(KEY_ENGLISH, english[i]);
            values.put(KEY_HINDI, hindi[i]);
            values.put(KEY_MARATHI, marathi[i]);
            db.insert(TABLE, null, values);
        }
        db.close();
    }

    public ArrayList<VisitPurposeBean> getPurposeData() {
        ArrayList<VisitPurposeBean> visitPurposeResponces = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                VisitPurposeBean visitPurposeBean = new VisitPurposeBean();
                visitPurposeBean.setEnglishText(cursor.getString(1));
                visitPurposeBean.setHindiText(cursor.getString(2));
                visitPurposeBean.setMarathiText(cursor.getString(3));
                visitPurposeResponces.add(visitPurposeBean);
            } while (cursor.moveToNext());
        }
        return  visitPurposeResponces;
    }

    public void deletePurposeData() {
        Log.e("DELETE "," PURPOSE DATA");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE);
        db.close();
    }
}