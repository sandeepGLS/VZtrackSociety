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

import vztrack.gls.com.vztracksociety.BaseActivity;
import vztrack.gls.com.vztracksociety.R;

/**
 * Created by Santosh on 06-Oct-15.
 */
public class GetData extends AsyncTask {
    String url;
    String result = "";
    String callFor = "";
    String extendedUrl = "";
    BaseActivity activity;
    ProgressDialog progressDialog;

    public GetData(BaseActivity activity, String callFor, String extendedUrl){
        this.activity = activity;
        this.callFor = callFor;
        this.extendedUrl = extendedUrl;
        url = createURL(callFor);
    }

    private String createURL(String callFor) {
        url = Constants.BASE_URL;

        if(callFor==CallFor.FLATSLIST){
            url = url+URL.FLAT_NUMBER;
        }
        if(callFor==CallFor.SEARCHVEHICLE){
            url = url+URL.SEARCH_VEHICLE+extendedUrl;
        }
        return url;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity,"","Loading...");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            result = ServerConnection.giveResponse(url,"");
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
        view.setBackgroundResource(R.color.red);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(20);
        toastTV.setTypeface(null, Typeface.BOLD);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
