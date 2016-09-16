package vztrack.gls.com.vztracksociety;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vztrack.gls.com.vztracksociety.beans.UserBean;
import vztrack.gls.com.vztracksociety.profile.FlatResponce;
import vztrack.gls.com.vztracksociety.profile.FlatsList;
import vztrack.gls.com.vztracksociety.profile.LoginResponse;
import vztrack.gls.com.vztracksociety.profile.SheredPref;
import vztrack.gls.com.vztracksociety.utils.CallFor;
import vztrack.gls.com.vztracksociety.utils.CheckConnection;
import vztrack.gls.com.vztracksociety.utils.DatabaseHandler;
import vztrack.gls.com.vztracksociety.utils.GetData;
import vztrack.gls.com.vztracksociety.utils.PostData;

public class LoginActivity extends BaseActivity {

    private EditText etPassword;
    private EditText etUsername;
    private String StrSocietyId;
    private String StrUserOrWatchmenId;
    private String strUsername,strPassword;
    private int InternetCheckFlag=0;
    private TextView tvForget;
    private String SavedDate;
    DatabaseHandler db;
    private ArrayList<String> NameAndFlat = new ArrayList<String>();

    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHandler(LoginActivity.this);

        //SheredPref.setUSername(this,"");
        //SheredPref.setPassword(this,"");


        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        tvForget = (TextView) findViewById(R.id.tvForget);

        strUsername = etUsername.getText().toString().trim();
        strPassword = etPassword.getText().toString().trim();

        etUsername.setFocusableInTouchMode(false);
        etPassword.setFocusableInTouchMode(false);

        etUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etUsername.setFocusableInTouchMode(true);
                return false;
            }
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etPassword.setFocusableInTouchMode(true);
                return false;
            }
        });

        getSupportActionBar().hide();

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "Contact VZtrack on ", Snackbar.LENGTH_LONG).setAction("9821824818", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri number = Uri.parse("tel:9821824818");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);
                    }
                }).show();
            }
        });
    }



    public void Login(View v)
    {

        btnSubmit.setEnabled(false);


        if(etUsername.getText().toString().trim().equals("") || etPassword.getText().toString().trim().equals(""))
        {
            Toast.makeText(this,"Username or Password should not be blank",Toast.LENGTH_LONG).show();
            btnSubmit.setEnabled(true);
        }
        else
        {
            CheckConnection ccAccess = new CheckConnection(getApplicationContext());
            Boolean isInternetAvailable = ccAccess.isConnectingToInternet();
            if(isInternetAvailable)
            {
                SheredPref.setDate(this,"");
                attemptLogin();
            }
            else
            {
                Intent ConnCheck = new Intent(this,NoInternetConnection.class);
                startActivity(ConnCheck);
            }
        }

    }

    public void ForgetPassword(View v)
    {

                //Toast.makeText(this,"Forget ",Toast.LENGTH_LONG).show();

                Snackbar.make(v, "Contact VZtrack on ", Snackbar.LENGTH_LONG).setAction("90750 16367", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri number = Uri.parse("tel:9075016367");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);
                    }
                }).show();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setMessage("Are you sure exit from application?");
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alert.show();

    }

    private void attemptLogin() {

        UserBean userBean = new UserBean();
        userBean.setUser_name(etUsername.getEditableText().toString().trim());
        userBean.setUser_password(etPassword.getEditableText().toString().trim());
        userBean.setUser_role_id("4");

        new PostData(new Gson().toJson(userBean), LoginActivity.this, CallFor.LOGIN).execute();
    }

    @Override
    public void onGetResponse(String response, String callFor) {
        LoginResponse loginResponse=new Gson().fromJson(response,LoginResponse.class);
        FlatResponce flatResponce = new Gson().fromJson(response, FlatResponce.class);
        if (response == null) {
            return;
        }
        if (callFor.equals(CallFor.LOGIN)) {
            btnSubmit.setEnabled(true);
            try {
                if (loginResponse.getCode().equals("SUCCESS")){
                    StrSocietyId = loginResponse.getSocity().getSocity_id();
                    StrUserOrWatchmenId = loginResponse.getUser().getUser_id();
                        SheredPref.setLoginInfo(this,"LoggedIn");
                        SheredPref.setSociety_Id(this,StrSocietyId);
                        SheredPref.setUserOrWatchmenId(this,StrUserOrWatchmenId);
                        SheredPref.setSocietyName(this,loginResponse.getSocity().getSocity_name());
                        SheredPref.setUSername(this,etUsername.getEditableText().toString().trim());
                        SheredPref.setPassword(this,etPassword.getEditableText().toString().trim());
                        Intent intent = new Intent(LoginActivity.this,SearchActivity.class);
                        startActivity(intent);

                        etUsername.setText("");
                        etPassword.setText("");
                    SavedDate = SheredPref.getDate(this);
                    if(SavedDate=="")
                    {
                        db.deleteContact();
                        SheredPref.setDate(this,GetTodayDate());
                        new GetData(LoginActivity.this, CallFor.FLATSLIST, "").execute();
                        NameAndFlat.clear();
                    }
                    if(compareToDay(GetTodayDate(),SavedDate)==1)
                    {
                        SheredPref.setDate(this,GetTodayDate());
                        db.deleteContact();
                        new GetData(LoginActivity.this, CallFor.FLATSLIST, "").execute();
                        NameAndFlat.clear();

                    }
                    if(compareToDay(GetTodayDate(),SavedDate)==0)
                    {
                    }
                }
                else if (loginResponse.getCode().equals("ERROR")) {
                    Toast.makeText(this,loginResponse.getMessage(),Toast.LENGTH_SHORT).show();
                }
                else {
                 Toast.makeText(this,"Invalid Details",Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
            }
        }

        if (callFor.equals(CallFor.FLATSLIST)) {

            for(int i=0;i<flatResponce.getAvailFlats().size();i++)
            {
                NameAndFlat.add(flatResponce.getAvailFlats().get(i).getFlatNo()+"-"+flatResponce.getAvailFlats().get(i).getFlatOwnerName());

            }

            for(int i=0;i<NameAndFlat.size();i++)
            {
                db.addFlatList(new FlatsList(NameAndFlat.get(i)));
            }
        }

    }

    public String  GetTodayDate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static int compareToDay(String strdate1, String strdate2) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date1 = null;
        Date date2 = null;

        try
        {
            date1 = formatter.parse(strdate1);
            date2 = formatter.parse(strdate2);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }


        if (date1 == null || date2 == null) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(date1).compareTo(sdf.format(date2));
    }

}
