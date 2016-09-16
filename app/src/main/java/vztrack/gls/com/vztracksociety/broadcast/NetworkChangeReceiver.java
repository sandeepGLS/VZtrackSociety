package vztrack.gls.com.vztracksociety.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import vztrack.gls.com.vztracksociety.SearchActivity;
import vztrack.gls.com.vztracksociety.beans.UserBean;
import vztrack.gls.com.vztracksociety.beans.VisitorBean;
import vztrack.gls.com.vztracksociety.profile.SheredPref;
import vztrack.gls.com.vztracksociety.utils.CallFor;
import vztrack.gls.com.vztracksociety.utils.CheckConnection;
import vztrack.gls.com.vztracksociety.utils.DbHelper;
import vztrack.gls.com.vztracksociety.utils.PostDataForCustumLogin;
import vztrack.gls.com.vztracksociety.utils.PostDataForOffline;

public class NetworkChangeReceiver extends BroadcastReceiver {

        private static final String LOG_TAG = "CheckNetworkStatus";
        private NetworkChangeReceiver receiver;
        private boolean isConnected = false;
        DbHelper dbHelper;
        Context context;

        @Override
        public void onReceive(final Context context, final Intent intent) {
            this.context = context;
            Log.v(LOG_TAG, "Receieved notification about network status");
            dbHelper= new DbHelper(context);
            isNetworkAvailable(context);

        }


        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if(!isConnected){

                                if(SheredPref.getExecute(context).equals("Execute"))
                                {
                                    PerformBackgroundTask();
                                    isConnected = true;
                                    SheredPref.setExecute(context,"NotExecute");
                                }
                                else
                                {
                                    LoginAttempt();
                                    SheredPref.setExecute(context,"Execute");
                                }
                            }
                            return true;
                        }
                    }
                }
            }
            isConnected = false;
            return false;
        }

    private void LoginAttempt() {

        UserBean userBean = new UserBean();
        userBean.setUser_name(SheredPref.getUsername(context));
        userBean.setUser_password(SheredPref.getPassword(context));
        userBean.setUser_role_id("4");
        new PostDataForCustumLogin(new Gson().toJson(userBean), context, CallFor.LOGIN).execute();

    }

    public void PerformBackgroundTask()
        {
            ArrayList<String> data_list = dbHelper.getAllId();
                    if(data_list.size()>0)
                    {
                        String strFname = null;
                        String strLname= null;
                        String strMobile= null;
                        String strPurpose= null;
                        String strFlatNo= null;
                        String strVehicel= null;
                        String strVisitersNo= null;
                        String strFrom= null;
                        String strWhom= null;
                        String strWatchmentId= null;
                        String strSocityId= null;
                        String strPhotoUrl1= null;
                        String strDocUrl1= null;
                        String strDate= null;

                        for(int i=0;i<data_list.size();i++)
                        {
                            Cursor rs = dbHelper.getData(Integer.parseInt(data_list.get(i)));
                            rs.moveToFirst();

                            String id = rs.getString(rs.getColumnIndex(dbHelper.ID));
                            strFname = rs.getString(rs.getColumnIndex(dbHelper.FIRST_NAME));
                            strLname = rs.getString(rs.getColumnIndex(dbHelper.LAST_NAME));
                            strMobile = rs.getString(rs.getColumnIndex(dbHelper.MOBILE_NO));
                            strPurpose = rs.getString(rs.getColumnIndex(dbHelper.PURPOSE));
                            strFlatNo = rs.getString(rs.getColumnIndex(dbHelper.FLAT_NO));
                            strVehicel = rs.getString(rs.getColumnIndex(dbHelper.VEHICLE_NO));
                            strVisitersNo = rs.getString(rs.getColumnIndex(dbHelper.VISITORS_NO));
                            strFrom = rs.getString(rs.getColumnIndex(dbHelper.FROM));
                            strWhom = rs.getString(rs.getColumnIndex(dbHelper.WHOM));
                            strWatchmentId = rs.getString(rs.getColumnIndex(dbHelper.WATCHMEN_ID));
                            strSocityId = rs.getString(rs.getColumnIndex(dbHelper.SOCIETY_ID));
                            strPhotoUrl1 = rs.getString(rs.getColumnIndex(dbHelper.PHOTO_STRING));
                            strDocUrl1 = rs.getString(rs.getColumnIndex(dbHelper.DOC_STRING));
                            strDate = rs.getString(rs.getColumnIndex(dbHelper.DATE));
                            rs.moveToNext();

                            if (!rs.isClosed())
                            {
                                rs.close();
                            }

                            VisitorBean visitorBean = new VisitorBean();
                            visitorBean.setFirst_name(strFname);
                            visitorBean.setLast_name(strLname);
                            visitorBean.setMobile_no(strMobile);

                            visitorBean.setVisit_purpose(strPurpose);
                            visitorBean.setFlat_no(strFlatNo);
                            visitorBean.setVehicle_no(strVehicel);
                            visitorBean.setNo_of_visitors(Integer.parseInt(strVisitersNo));
                            visitorBean.setFrom(strFrom);
                            visitorBean.setWhomeToVisit(strWhom);
                            visitorBean.setWatchman_id(Integer.parseInt(strWatchmentId));

                            visitorBean.setOld(false);
                            visitorBean.setPhoto_url(strPhotoUrl1);
                            visitorBean.setDoc_url(strDocUrl1);
                            visitorBean.setSocity_id(Integer.parseInt(strSocityId));
                            visitorBean.setIn_time(strDate);
                            new PostDataForOffline(new Gson().toJson(visitorBean), context , CallFor.ADD_NEW_OFFLINE, Integer.parseInt(data_list.get(i))).execute();
                        }
                    }
        }
}