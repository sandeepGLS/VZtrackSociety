package vztrack.gls.com.vztracksociety;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import vztrack.gls.com.vztracksociety.adapter.RecyclerViewAdapter;
import vztrack.gls.com.vztracksociety.beans.CheckOutBean;
import vztrack.gls.com.vztracksociety.beans.SearchBean;
import vztrack.gls.com.vztracksociety.beans.UserBean;
import vztrack.gls.com.vztracksociety.profile.FlatResponce;
import vztrack.gls.com.vztracksociety.profile.FlatsList;
import vztrack.gls.com.vztracksociety.profile.LoginResponse;
import vztrack.gls.com.vztracksociety.profile.SearchResponse;
import vztrack.gls.com.vztracksociety.profile.SheredPref;
import vztrack.gls.com.vztracksociety.utils.CallFor;
import vztrack.gls.com.vztracksociety.utils.CheckConnection;
import vztrack.gls.com.vztracksociety.utils.DatabaseHandler;
import vztrack.gls.com.vztracksociety.utils.DividerItemDecoration;
import vztrack.gls.com.vztracksociety.utils.GetData;
import vztrack.gls.com.vztracksociety.utils.PostData;

public class SearchActivity extends BaseActivity {
    private EditText etPhoneNumber;
    private String strValidation;
    private String strSociety_Id;
    private String strFirst_name,strLast_name;
    private String strMobile_no;
    private String strPerpose_Of_Visit;
    private String strFlat_No;
    private String strPhotoUrl;
    private String strDocUrl;
    private String strfrom;
    private String strWhomeToVisit;
    private String strVehicle_No;
    private int strTotalVisiters;
    private  String strUserName,strPassword;
    private  String strSocietyName;
    private  String strCheck;
    private  String strUserOrWatchmenId;
    private String SavedDate;
    private TextView tvSocietyName,counter;
    private ArrayList<String> NameAndFlat = new ArrayList<String>();
    private Button btnSubmit;
    CheckConnection cc;
    DatabaseHandler db;
    Button SearchVisitor;
    LinearLayout Main_Layout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<String> mDataSet;
    private  ArrayList<CheckOutBean> checkOutBeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        // Out Work
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("RecyclerView");
            }
        }

        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Item Decorator:
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInLeftAnimator());
        checkOutBeen = new ArrayList<CheckOutBean>();
        //checkOutBeen.
        // Adapter:
        String[] adapterData = new String[]{"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

       // mDataSet.
        mDataSet = new ArrayList<String>(Arrays.asList(adapterData));
        mAdapter = new RecyclerViewAdapter(this, mDataSet);
        ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);

        /* Listeners */
        recyclerView.setOnScrollListener(onScrollListener);





        db = new DatabaseHandler(this);


        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        SearchVisitor = (Button) findViewById(R.id.SearchVisitors);


        tvSocietyName  = (TextView) findViewById(R.id.tvSocietyName);

        counter  = (TextView) findViewById(R.id.counter);

        Main_Layout = (LinearLayout) findViewById(R.id.Main_Layout);

        etPhoneNumber = (EditText) findViewById(R.id.phoneNumber);

        etPhoneNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // this will show characters remaining
                counter.setText(s.toString().length() + " /10");
            }
        });
    }

    /* Substitute for our onScrollListener for RecyclerView
    */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };


    @Override
    public void onResume()
    {
        super.onResume();
        btnSubmit.setEnabled(true);

        cc = new CheckConnection(getApplicationContext());

        strValidation = SheredPref.getLoginInfo(getApplicationContext());

        strSociety_Id = SheredPref.getSociety_Id(getApplicationContext());

        if(strValidation.equals("LoggedIn"))
        {
            strUserName = SheredPref.getUsername(getApplicationContext());
            strPassword = SheredPref.getPassword(getApplicationContext());
        }
        else
        {
            Intent I = new Intent(SearchActivity.this,LoginActivity.class);
            startActivity(I);
            I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        strSocietyName = SheredPref.getSocietyName(getApplicationContext());

        strCheck = SheredPref.getCheck(getApplicationContext());

        strUserOrWatchmenId = SheredPref.getUserOrWatchmenId(getApplicationContext());

        tvSocietyName.setText(strSocietyName);


        if(strCheck.equalsIgnoreCase("Show"))
        {
            ShowToastOfEntry();
            SheredPref.setCheck(this,"Dont_Show");
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            SearchVisitor.setVisibility(View.INVISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   // getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etPhoneNumber, InputMethodManager.SHOW_IMPLICIT);
                    SearchVisitor.setVisibility(View.VISIBLE);
                }
            }, 2500);
        }
    }

    public void Logout(View v) {

        SheredPref.setUSername(this,"");
        SheredPref.setPassword(this,"");
        SheredPref.setLoginInfo(this,"LoggedOut");

        SheredPref.setDate(this,"");


        Intent I = new Intent(SearchActivity.this,LoginActivity.class);
        startActivity(I);
        this.finish();
        I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public void Search(View v)
    {
        btnSubmit.setEnabled(false);

        if(etPhoneNumber.getText().length()!=10)
        {
            ShowToast(" वैध मोबाइल नंबर दर्ज करें");
            btnSubmit.setEnabled(true);
        }
        else
        {
            if(cc.isConnectingToInternet())
            {
                SearchBean searchBean = new SearchBean();
                searchBean.setMobile_no(etPhoneNumber.getText().toString().trim());
                searchBean.setSocity_id(strSociety_Id);
                searchBean.setWatchman_id(strUserOrWatchmenId);

                new PostData(new Gson().toJson(searchBean), SearchActivity.this, CallFor.SEARCH).execute();
                btnSubmit.setEnabled(true);
            }
            else
            {
                Intent Go = new Intent(SearchActivity.this, AddUserActivity.class);
                Go.putExtra("PhoneNumber", etPhoneNumber.getText().toString());
                Go.putExtra("Society_Id", strSociety_Id);
                startActivity(Go);
                etPhoneNumber.setText("");
            }
        }
    }

    public void SearchVehicle(View v) {
        if(cc.isConnectingToInternet())
        {
            Intent intent = new Intent(SearchActivity.this, SearchVehicle.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.this);
        alert.setMessage("Your session is saved, Are you sure exit from application?");
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

    @Override
    public void onGetResponse(String response, String callFor) {
        if(cc.isConnectingToInternet()) {

            SearchResponse searchResponse = null;
            FlatResponce flatResponce = null;
            LoginResponse loginResponse = null;


            try {
                searchResponse = new Gson().fromJson(response, SearchResponse.class);
                flatResponce = new Gson().fromJson(response, FlatResponce.class);
                loginResponse=new Gson().fromJson(response,LoginResponse.class);
            }
            catch (Exception ex)
            {
            }


            if (response == null) {
                return;
            }

            if (callFor.equals(CallFor.LOGIN)) {

                try
                {
                    if (loginResponse.getCode().equals("SUCCESS")){

                        SearchBean searchBean = new SearchBean();
                        searchBean.setMobile_no(etPhoneNumber.getText().toString().trim());
                        searchBean.setSocity_id(strSociety_Id);
                        searchBean.setWatchman_id(strUserOrWatchmenId);

                        new PostData(new Gson().toJson(searchBean), SearchActivity.this, CallFor.SEARCH).execute();
                    }
                }
                catch (Exception ex)
                {

                }

            }


            if (callFor.equals(CallFor.SEARCH)) {

                btnSubmit.setEnabled(true);

                try {

                    if (searchResponse.getCode().equals("SUCCESS")) {

                        SavedDate = SheredPref.getDate(this);
                        if(SavedDate=="")
                        {
                            db.deleteContact();
                            SheredPref.setDate(this,GetTodayDate());
                            new GetData(SearchActivity.this, CallFor.FLATSLIST, "").execute();
                            NameAndFlat.clear();
                        }
                        if(compareToDay(GetTodayDate(),SavedDate)==1)
                        {
                            SheredPref.setDate(this,GetTodayDate());
                            db.deleteContact();
                            new GetData(SearchActivity.this, CallFor.FLATSLIST, "").execute();
                            NameAndFlat.clear();

                        }
                        if(compareToDay(GetTodayDate(),SavedDate)==0)
                        {

                        }

                        strFirst_name = searchResponse.getVisitors().get(0).getFirst_name();
                        strLast_name = searchResponse.getVisitors().get(0).getLast_name();
                        strMobile_no = searchResponse.getVisitors().get(0).getMobile_no();
                        strPerpose_Of_Visit = searchResponse.getVisitors().get(0).getVisit_purpose();
                        strFlat_No = searchResponse.getVisitors().get(0).getFlat_no();
                        strVehicle_No = searchResponse.getVisitors().get(0).getVehicle_no();
                        strTotalVisiters = searchResponse.getVisitors().get(0).getNo_of_visitors();
                        strPhotoUrl = searchResponse.getVisitors().get(0).getPhoto_url();
                        strDocUrl = searchResponse.getVisitors().get(0).getDoc_url();
                        strfrom = searchResponse.getVisitors().get(0).getFrom();
                        strWhomeToVisit = searchResponse.getVisitors().get(0).getWhomeToVisit();
                        String flatInfo = null;
                        try
                        {
                            flatInfo = db.getFlatInfo(strFlat_No);
                        }
                        catch (Exception ex)
                        {
                        }
                        Intent Go = new Intent(SearchActivity.this, GetDetailsActivity.class);
                        Go.putExtra("CALL_FROM", "Search_Activity");
                        Go.putExtra("FIRST_NAME", strFirst_name);
                        Go.putExtra("LAST_NAME", strLast_name);
                        Go.putExtra("MOBILE_NO", strMobile_no);
                        Go.putExtra("VISIT_PURPOSE", strPerpose_Of_Visit);
                        Go.putExtra("FLAT_NO", flatInfo);
                        Go.putExtra("VEHICLE_NO", strVehicle_No);
                        Go.putExtra("TOTAL_VISITERS", ""+strTotalVisiters);
                        Go.putExtra("Society_Id", strSociety_Id);
                        Go.putExtra("FROM", strfrom);
                        Go.putExtra("WHOM", strWhomeToVisit);
                        Go.putExtra("PHOTO_URL", strPhotoUrl);
                        Go.putExtra("DOC_URL", strDocUrl);
                        startActivity(Go);
                        etPhoneNumber.setText("");

                    } else if (searchResponse.getCode().equals("NEED_LOGIN")) {

                        UserBean userBean = new UserBean();
                        userBean.setUser_name(strUserName);
                        userBean.setUser_password(strPassword);
                        userBean.setUser_role_id("4");
                        new PostData(new Gson().toJson(userBean), SearchActivity.this, CallFor.LOGIN).execute();

                    } else {
                        Intent Go = new Intent(SearchActivity.this, AddUserActivity.class);
                        Go.putExtra("PhoneNumber", etPhoneNumber.getText().toString());
                        Go.putExtra("Society_Id", strSociety_Id);
                        startActivity(Go);
                        etPhoneNumber.setText("");
                    }
                } catch (Exception e) {
                    btnSubmit.setEnabled(true);
                }
            }  //End Of If

            if (callFor.equals(CallFor.FLATSLIST)) {

                for(int i=0;i<flatResponce.getAvailFlats().size();i++)
                {
                    NameAndFlat.add(flatResponce.getAvailFlats().get(i).getFlatNo()+"-"+flatResponce.getAvailFlats().get(i).getFlatOwnerName());
                }

                for(int i=0;i<NameAndFlat.size();i++)
                {
                    db.addFlatList(new FlatsList(NameAndFlat.get(i)));
                }

                //db.deleteContact();
            }

            }
    }

    public String  GetTodayDate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
       // Log.e("TOdays DAte "," "+formattedDate);
        //return "15-Apr-2016";
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


    public void ShowToast(String Text)
    {
        Toast toast = Toast.makeText(this, Text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_bg);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setGravity(Gravity.CENTER);
        toastTV.setTextSize(20);
        toastTV.setTypeface(null, Typeface.BOLD);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void ShowToastOfEntry()    {


        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.toast_layout_root));
        Toast toast = new Toast(getApplicationContext());
       // toast.setGravity(Gravity.FILL, 0, 0);
        toast.setGravity(Gravity.FILL_HORIZONTAL, -60, -60);
        //toast.setGravity(Gravity.FILL_HORIZONTAL, 40, 40);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


}
