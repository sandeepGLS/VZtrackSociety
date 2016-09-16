package vztrack.gls.com.vztracksociety.utils;

/**
 * Created by sandeep on 21/4/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Santosh on 25-Sep-15.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION =9;

    public static final String DATABASE_NAME = "vztrack_database.db";

    public static final String TABLE_NAME = "vztrack";

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String MOBILE_NO = "mobile_no";
    public static final String PURPOSE = "purpose";
    public static final String FLAT_NO = "flat_no";
    public static final String VEHICLE_NO = "vehicle_no";
    public static final String VISITORS_NO = "visitors_no";
    public static final String FROM = "Visitorfrom";
    public static final String WHOM = "whom";
    public static final String WATCHMEN_ID = "watchmen_id";
    public static final String SOCIETY_ID = "society_id";
    public static final String PHOTO_STRING = "photo_string";
    public static final String DOC_STRING = "doc_string";
    public static final String SET_OLD = "set_old";
    public static final String DATE = "date";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
         db.execSQL("create table "+TABLE_NAME+"("+ID+" integer primary key, "+FIRST_NAME+" text, "+LAST_NAME+" text, "+MOBILE_NO+" text, "+PURPOSE+" text, "+ FLAT_NO + " text, "+ VEHICLE_NO + " text, "+VISITORS_NO+" text, "+FROM+" text, "+WHOM+" text, "+WATCHMEN_ID+" text, "+SOCIETY_ID+" text, "+PHOTO_STRING+" text, "+DOC_STRING+" text, "+SET_OLD+" text, "+DATE+" text"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    public boolean insertData(String strFname,String strLname,String strMobile,String strPurpose,String strFlatNo,String strVehicel,String strVisitersNo,String strFrom,String strWhom,String strWatchmentId,String strSocietyId,String strPhotoUrl,String strDocUrl,String strDate)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, strFname);
        contentValues.put(LAST_NAME, strLname);
        contentValues.put(MOBILE_NO, strMobile);
        contentValues.put(PURPOSE, strPurpose);
        contentValues.put(FLAT_NO, strFlatNo);
        contentValues.put(VEHICLE_NO, strVehicel);
        contentValues.put(VISITORS_NO, strVisitersNo);
        contentValues.put(FROM, strFrom);
        contentValues.put(WHOM, strWhom);
        contentValues.put(WATCHMEN_ID, strWatchmentId);
        contentValues.put(SOCIETY_ID, strSocietyId);
        contentValues.put(PHOTO_STRING, strPhotoUrl);
        contentValues.put(DOC_STRING, strDocUrl);
        contentValues.put(SET_OLD, "false");
        contentValues.put(DATE, strDate);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }


    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where id="+id+"", null );
        return res;
    }

    public int getNumberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public Integer deleteData (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"id = ? ",new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllId()
    {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select ID from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ID)));
            res.moveToNext();
        }
        return array_list;
    }
}