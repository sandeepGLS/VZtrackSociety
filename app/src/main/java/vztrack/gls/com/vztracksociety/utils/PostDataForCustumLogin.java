package vztrack.gls.com.vztracksociety.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Santosh on 06-Oct-15.
 */
public class PostDataForCustumLogin extends AsyncTask {
    String url;
    public static String result = "";
    String callFor = "";
    String data = "";
    Context activity;
    ProgressDialog progressDialog;
    public static String LoginSuccess;

    public PostDataForCustumLogin(String data, Context activity, String callFor){
        this.activity = activity;
        this.callFor = callFor;
        this.data = data;
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
        Log.e("url Offline",url);
        return url;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            result = ServerConnectionOffline.giveResponse(url,data);
        } catch (Exception e){
            Log.e("Error doInBackground",e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

    }
}
