package vztrack.gls.com.vztracksociety.profile;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sandeep on 16/3/16.
 */
public class SheredPref {

    public static String getLoginInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.VALIDATION, Context.MODE_PRIVATE);
        return sp.getString(Finals.LOGIN_CHECKED,"");
    }
    public static void setLoginInfo(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.VALIDATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.LOGIN_CHECKED,city);
        editor.commit();
    }


    public static String getSociety_Id(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOCIETYID, Context.MODE_PRIVATE);
        return sp.getString(Finals.SOCIETYIDCHECK,"");
    }
    public static void setSociety_Id(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOCIETYID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.SOCIETYIDCHECK,city);
        editor.commit();
    }


    public static String getUsername(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.USERNAME, Context.MODE_PRIVATE);
        return sp.getString(Finals.USERNAME,"");
    }
    public static void setUSername(Context context,String username){
        SharedPreferences sp = context.getSharedPreferences(Finals.USERNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.USERNAME,username);
        editor.commit();
    }


    public static String getPassword(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.PASSWORD, Context.MODE_PRIVATE);
        return sp.getString(Finals.PASSWORD,"");
    }
    public static void setPassword(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.PASSWORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.PASSWORD,city);
        editor.commit();
    }

    public static String getSocietyName(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOCIETYNAME, Context.MODE_PRIVATE);
        return sp.getString(Finals.SOCIETYNAME,"");
    }
    public static void setSocietyName(Context context,String Sname){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOCIETYNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.SOCIETYNAME,Sname);
        editor.commit();
    }


    public static String getCheck(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.CHECK, Context.MODE_PRIVATE);
        return sp.getString(Finals.CHECK,"");
    }
    public static void setCheck(Context context,String check){
        SharedPreferences sp = context.getSharedPreferences(Finals.CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.CHECK,check);
        editor.commit();
    }


    public static String getUserOrWatchmenId(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.USERORWATCHMENID, Context.MODE_PRIVATE);
        return sp.getString(Finals.USERORWATCHMENID,"");
    }
    public static void setUserOrWatchmenId(Context context,String check){
        SharedPreferences sp = context.getSharedPreferences(Finals.USERORWATCHMENID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.USERORWATCHMENID,check);
        editor.commit();
    }

    public static String getDate(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE, Context.MODE_PRIVATE);
        return sp.getString(Finals.DATE,"");
    }
    public static void setDate(Context context,String date){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.DATE,date);
        editor.commit();
    }

    public static String getExecute(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.EXECUTE, Context.MODE_PRIVATE);
        return sp.getString(Finals.EXECUTE,"");
    }
    public static void setExecute(Context context,String date){
        SharedPreferences sp = context.getSharedPreferences(Finals.EXECUTE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.EXECUTE,date);
        editor.commit();
    }
}