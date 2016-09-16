package vztrack.gls.com.vztracksociety;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sandeep on 12/9/16.
 */

class UrlToStringImage extends AsyncTask<String, Void, String> {
    private String imageUrl;
    Bitmap image;
    int photo_flag;
    Context context;
    ProgressDialog progressDialog;

    public UrlToStringImage(String url , Context MainActivity) {
        this.imageUrl = url;
        context = MainActivity;
    }

    @Override
    protected String doInBackground(String... params) {

        try
        {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                image = BitmapFactory.decodeStream(input);

                if(image==null)
                {
                    GetDetailsActivity.photo_flag=0;
                    //Toast.makeText(context, "0", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    GetDetailsActivity.photo_flag=1;
                    //Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch (Exception ex)
        {
        }
        return ""+photo_flag;
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context,"","Loading...");

    }

}
