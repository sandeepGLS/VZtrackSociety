package vztrack.gls.com.vztracksociety.utils;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import vztrack.gls.com.vztracksociety.BaseActivity;
import vztrack.gls.com.vztracksociety.R;

/**
 * Created by Santosh on 06-Oct-15.
 */
public class PostData extends AsyncTask {
    String url;
    String result = "";
    String callFor = "";
    String data = "";
    BaseActivity activity;
    ProgressDialog progressDialog;

    public PostData(String data, BaseActivity activity, String callFor){
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
        Log.e("url",url);
        return url;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity,"","Loading...");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e("URL ===>",url);
        try {
            result = ServerConnection.giveResponse(url,data);
        } catch (Exception e){
            Log.e("Error doInBackground",e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        try
        {
            super.onPostExecute(o);
            progressDialog.dismiss();
            if(result.contains("org.apache.http.conn.ConnectTimeoutException")){
                ShowToast("Please check intenet connection \n इंटरनेट कनेक्शन की जाँच करें");
            }else{
                activity.onGetResponse(result, callFor);
            }
        }catch (Exception ex)
        {

        }
    }

    public void ShowToast(String Text)
    {
        Toast toast = Toast.makeText(activity, Text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_bg);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(20);
        toastTV.setTypeface(null, Typeface.BOLD);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
