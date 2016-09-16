package vztrack.gls.com.vztracksociety;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import vztrack.gls.com.vztracksociety.utils.CheckConnection;

public class NoInternetConnection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);

        getSupportActionBar().hide();
    }

    public void Refresh(View v)
    {
        CheckConnection cc = new CheckConnection(getApplicationContext());
        Boolean isConnectingToInternet = cc.isConnectingToInternet();
        if(isConnectingToInternet)
        {
            CheckConnection ccAccess = new CheckConnection(getApplicationContext());
            Boolean isInternetAvailable = ccAccess.isConnectingToInternet();
            if(isInternetAvailable)
            {
                Intent ConnCheck = new Intent(this,SearchActivity.class);
                ConnCheck.addCategory(Intent.CATEGORY_HOME);
                ConnCheck.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ConnCheck);
            }
        }
    }

    public void Close(View v)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(NoInternetConnection.this);
        alert.setMessage("Are you sure exit from application?");
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("exit", "app EXIT");
                dialogInterface.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alert.show();
    }
}
