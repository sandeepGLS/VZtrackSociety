package vztrack.gls.com.vztracksociety.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import vztrack.gls.com.vztracksociety.SearchActivity;
import vztrack.gls.com.vztracksociety.profile.AddUserResponce;

/**
 * Created by Santosh on 06-Oct-15.
 */
public class PostDataForOffline extends AsyncTask {
    String url;
    public static String result = "";
    String callFor = "";
    String data = "";
    Context activity;
    int id;
    ProgressDialog progressDialog;
    DbHelper dbHelper;
    CheckConnection cc;

    public PostDataForOffline(String data, Context activity, String callFor,int id){
        this.activity = activity;
        this.callFor = callFor;
        this.data = data;
        this.id=id;
        url = createURL(callFor);
    }

    private String createURL(String callFor) {
        url = Constants.BASE_URL;

        if(callFor==CallFor.LOGIN){
            url = url+URL.LOGIN;
        }
        if(callFor==CallFor.SEARCH){
            url = url+URL.SEARCH_VISITERS;
        }
        if(callFor==CallFor.ADD_NEW_USER){
            url = url+URL.ADD_NEW_USER;
        }
        if(callFor==CallFor.ADD_NEW_OFFLINE){
            url = url+URL.ADD_NEW_USER_OFFLINE;
        }
        return url;
    }

    @Override
    protected void onPreExecute() {
        cc = new CheckConnection(activity);
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            result = ServerConnectionOffline.giveResponse(url,data);

            if(cc.isConnectingToInternet())
            {
                Log.e("Inside Net Conn Call","");

                AddUserResponce addUserResponce=new Gson().fromJson(result,AddUserResponce.class);

                if (addUserResponce.getCode().equals("SUCCESS")) {

                    Log.e(" Inside Delete ","");
                    dbHelper = new DbHelper(activity);
                    dbHelper.deleteData(id);
                }
            }
            else
            {
                Log.e("Inside Net not Conn Call","");
            }

        } catch (Exception e){
            Log.e("Error doInBackground",e.toString());
            //e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
