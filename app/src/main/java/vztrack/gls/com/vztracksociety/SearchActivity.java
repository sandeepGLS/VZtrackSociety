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
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import mehdi.sakout.fancybuttons.FancyButton;
import vztrack.gls.com.vztracksociety.adapter.RecyclerViewAdapter;
import vztrack.gls.com.vztracksociety.beans.SearchBean;
import vztrack.gls.com.vztracksociety.beans.UserBean;
import vztrack.gls.com.vztracksociety.beans.VisitPurposeResponce;
import vztrack.gls.com.vztracksociety.beans.VisitorBean;
import vztrack.gls.com.vztracksociety.profile.ExitResponce;
import vztrack.gls.com.vztracksociety.profile.FlatResponce;
import vztrack.gls.com.vztracksociety.profile.FlatsList;
import vztrack.gls.com.vztracksociety.profile.LoginResponse;
import vztrack.gls.com.vztracksociety.profile.SearchResponse;
import vztrack.gls.com.vztracksociety.profile.SheredPref;
import vztrack.gls.com.vztracksociety.utils.CallFor;
import vztrack.gls.com.vztracksociety.utils.CheckConnection;
import vztrack.gls.com.vztracksociety.utils.DatabaseHandler;
import vztrack.gls.com.vztracksociety.utils.GetData;
import vztrack.gls.com.vztracksociety.utils.PostData;

public class SearchActivity extends BaseActivity {
    private EditText etPhoneNumber;
    private String strValidation;
    private String strSociety_Id;
    private String strFirst_name, strLast_name;
    private String strMobile_no;
    private String strPerpose_Of_Visit;
    private String strFlat_No;
    private String strPhotoUrl;
    private String strDocUrl;
    private String strfrom;
    private String strWhomeToVisit;
    private String strVehicle_No;
    private int strTotalVisiters;
    private String strUserName, strPassword;
    private String strSocietyName;
    private String strCheck;
    private String strUserOrWatchmenId;
    private String SavedDate;
    private TextView tvSocietyName, counter;
    private ArrayList<String> NameAndFlat = new ArrayList<String>();
    private FancyButton btnSubmit;
    CheckConnection cc;
    DatabaseHandler db;
    Button SearchVisitor;
    LinearLayout Main_Layout;
    public static RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<String> mDataSet;
    private ArrayList<VisitorBean> visitorBeanDataSet = new ArrayList<VisitorBean>();
    private ArrayList<VisitorBean> visitorBeanDataSetSearched = new ArrayList<VisitorBean>();
    private String callCheck = "";
    public static TextView noVisitor;
    public static TextView noVisitorList;
    private EditText searchText;
    public static LinearLayout outLayout;
    private String UI;
    public static int ShowNoVisitor;
    boolean FromSearch = false;
    int textChangedCnt=0;
    public static Context context;
    private TextView tvTitle, tvOutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UI = SheredPref.getShowUI(this);

        try{
            if (UI.equals("0") || UI.equals(""))
            {
                setContentView(R.layout.activity_search_old);
            }

            if (UI.equals("1"))
            {
                setContentView(R.layout.activity_search);
                outLayout = (LinearLayout) findViewById(R.id.outLayout);
                noVisitorList = (TextView) findViewById(R.id.noVisitorList);
            }
        }catch (Exception ex){

        }

        context = SearchActivity.this;
        getSupportActionBar().hide();
        db = new DatabaseHandler(this);

        btnSubmit = (FancyButton) findViewById(R.id.btnSubmit);
        SearchVisitor = (Button) findViewById(R.id.SearchVisitors);
        tvSocietyName = (TextView) findViewById(R.id.tvSocietyName);
        counter = (TextView) findViewById(R.id.counter);
        tvTitle = (TextView) findViewById(R.id.title);
        tvOutName = (TextView) findViewById(R.id.flatAndEmp);
        Main_Layout = (LinearLayout) findViewById(R.id.Main_Layout);
        etPhoneNumber = (EditText) findViewById(R.id.phoneNumber);

        if(SheredPref.getType(context).equals("0") || SheredPref.getType(context).equals("")){
            tvTitle.setText("Society");
            if (UI.equals("1")) {
                tvOutName.setText("Flat No.");
            }
        }
        if(SheredPref.getType(context).equals("1")){
            tvTitle.setText("Company");
            if (UI.equals("1")) {
                tvOutName.setText("Employee");
            }
        }

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // this will show characters remaining
                counter.setText(s.toString().length() + " /10");
            }
        });

        if (UI.equals("1")) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            noVisitor = (TextView) findViewById(R.id.noVisitorTv);
            searchText = (EditText) findViewById(R.id.searchForExit);
            recyclerView.setVisibility(View.VISIBLE);
            noVisitor.setVisibility(View.GONE);

            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    visitorBeanDataSetSearched.clear();
                    String query = searchText.getText().toString().trim().toLowerCase();
                    textChangedCnt = searchText.getText().length();
                    if (visitorBeanDataSet.size() != 0) {
                        for (int i = 0; i < visitorBeanDataSet.size(); i++) {
                            String mobile = visitorBeanDataSet.get(i).getMobile_no().toLowerCase();
                            String vehicle = visitorBeanDataSet.get(i).getVehicle_no().toLowerCase();
                            String name = null;
                            if(SheredPref.getType(context).equals("0") || SheredPref.getType(context).equals("")){
                                name = visitorBeanDataSet.get(i).getFirst_name().toLowerCase() + " " + visitorBeanDataSet.get(i).getLast_name().toLowerCase();
                            }
                            if(SheredPref.getType(context).equals("1")){
                                name = visitorBeanDataSet.get(i).getFirst_name().toLowerCase() + " " + visitorBeanDataSet.get(i).getLast_name().toLowerCase() +"\n"+visitorBeanDataSet.get(i).getNo_of_visitors();
                            }
                            // String name = visitorBeanDataSet.get(i).getFirst_name().toLowerCase() + " " + visitorBeanDataSet.get(i).getLast_name().toLowerCase() +"\n"+visitorBeanDataSet.get(i).getNo_of_visitors();
                            String flatno = visitorBeanDataSet.get(i).getFlat_no().toLowerCase();
                            if (flatno.contains(query) || name.contains(query) ||mobile.contains(query) || vehicle.contains(query)) {
                                VisitorBean visitorBean = new VisitorBean();
                                visitorBean.setVisitor_id(visitorBeanDataSet.get(i).getVisitor_id());
                                visitorBean.setFirst_name(visitorBeanDataSet.get(i).getFirst_name());
                                visitorBean.setLast_name(visitorBeanDataSet.get(i).getLast_name());
                                visitorBean.setNameInHindi(visitorBeanDataSet.get(i).getNameInHindi());
                                if(!SheredPref.getType(context).equals("0")){
                                    visitorBean.setNo_of_visitors(visitorBeanDataSet.get(i).getNo_of_visitors());
                                }

                                visitorBean.setFlat_no(visitorBeanDataSet.get(i).getFlat_no());
                                visitorBean.setMobile_no(visitorBeanDataSet.get(i).getMobile_no());
                                visitorBean.setVehicle_no(visitorBeanDataSet.get(i).getVehicle_no());
                                visitorBean.setIn_time(visitorBeanDataSet.get(i).getIn_time());
                                visitorBeanDataSetSearched.add(visitorBean);
                            }
                        }
                        // Out Work

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            ActionBar actionBar = getActionBar();
                            if (actionBar != null) {
                                actionBar.setTitle("RecyclerView");
                            }
                        }
                        FromSearch =true;
                        // Layout Managers:
                        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

                        // Item Decorator:
                        //recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
                        recyclerView.setItemAnimator(new FadeInLeftAnimator());

                        ShowNoVisitor = 0;
                        mAdapter = new RecyclerViewAdapter(SearchActivity.this, visitorBeanDataSetSearched);
                        ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setOnScrollListener(onScrollListener);

                    } else {
                        if(textChangedCnt == 1){
                            ShowToast("No Visitor for Search \n\n खोज के लिए कोई आगंतुक नहीं");
                        }
                    }

                }
            });
        }
    }

    /* Substitute for our onScrollListener for RecyclerView
    */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        btnSubmit.setEnabled(true);

        cc = new CheckConnection(getApplicationContext());
        if (UI.equals("1")) {
            if (cc.isConnectingToInternet()) {
                outLayout.setVisibility(View.VISIBLE);
                noVisitorList.setVisibility(View.GONE);
                new GetData(SearchActivity.this, CallFor.EXIT_VISITOR_LIST, "").execute();
            }
            else
            {
                outLayout.setVisibility(View.GONE);
                noVisitorList.setVisibility(View.VISIBLE);
            }
        }

        strValidation = SheredPref.getLoginInfo(getApplicationContext());

        strSociety_Id = SheredPref.getSociety_Id(getApplicationContext());

        if (strValidation.equals("LoggedIn")) {
            strUserName = SheredPref.getUsername(getApplicationContext());
            strPassword = SheredPref.getPassword(getApplicationContext());
        } else {
            Intent I = new Intent(SearchActivity.this, LoginActivity.class);
            startActivity(I);
            I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        strSocietyName = SheredPref.getSocietyName(getApplicationContext());

        strCheck = SheredPref.getCheck(getApplicationContext());

        strUserOrWatchmenId = SheredPref.getUserOrWatchmenId(getApplicationContext());

        tvSocietyName.setText(strSocietyName);


        if (strCheck.equalsIgnoreCase("Show")) {
            ShowToastOfEntry();
            SheredPref.setCheck(this, "Dont_Show");
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            SearchVisitor.setVisibility(View.INVISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etPhoneNumber, InputMethodManager.SHOW_IMPLICIT);
                    SearchVisitor.setVisibility(View.VISIBLE);
                }
            }, 2500);
        }
    }

    public void Logout(View v) {

        if (cc.isConnectingToInternet()) {
            SheredPref.setUSername(this, "");
            SheredPref.setPassword(this, "");
            SheredPref.setLoginInfo(this, "LoggedOut");
            SheredPref.setDate(this, "");
            SheredPref.setShowUI(this,"");

            Intent I = new Intent(SearchActivity.this, LoginActivity.class);
            startActivity(I);
            this.finish();
            I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }else{
            ShowToast("Please check internet connection\nUnable to Logout \nइंटरनेट कनेक्शन की जाँच करें");
        }
    }

    public void Search(View v) {
        btnSubmit.setEnabled(false);

        if (etPhoneNumber.getText().length() != 10) {
            ShowToast(" वैध मोबाइल नंबर दर्ज करें ");
            btnSubmit.setEnabled(true);
        } else {
            if (cc.isConnectingToInternet()) {
                SearchBean searchBean = new SearchBean();
                searchBean.setMobile_no(etPhoneNumber.getText().toString().trim());
                searchBean.setSocity_id(strSociety_Id);
                searchBean.setWatchman_id(strUserOrWatchmenId);

                new PostData(new Gson().toJson(searchBean), SearchActivity.this, CallFor.SEARCH).execute();
                btnSubmit.setEnabled(true);
            } else {
                Intent Go = new Intent(SearchActivity.this, AddUserActivity.class);
                Go.putExtra("PhoneNumber", etPhoneNumber.getText().toString());
                Go.putExtra("Society_Id", strSociety_Id);
                startActivity(Go);
                etPhoneNumber.setText("");
            }
        }
    }

    public void SearchVehicle(View v) {
        if (cc.isConnectingToInternet()) {
            Intent intent = new Intent(SearchActivity.this, SearchVehicle.class);
            intent.putExtra("UI_VAL", UI);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        Log.e("RESP "," "+response);
        if (cc.isConnectingToInternet()) {
            SearchResponse searchResponse = null;
            FlatResponce flatResponce = null;
            LoginResponse loginResponse = null;
            ExitResponce exitResponce = null;
            VisitPurposeResponce visitPurposeResponce = null;

            try {
                searchResponse = new Gson().fromJson(response, SearchResponse.class);
                flatResponce = new Gson().fromJson(response, FlatResponce.class);
                loginResponse = new Gson().fromJson(response, LoginResponse.class);
                exitResponce = new Gson().fromJson(response, ExitResponce.class);
                visitPurposeResponce = new Gson().fromJson(response, VisitPurposeResponce.class);
            } catch (Exception ex) {
                Log.e("Exception "," "+ex);
            }

            if (response == null) {
                return;
            }

            if (callFor.equals(CallFor.LOGIN)) {
                try {
                    if (loginResponse.getCode().equals("SUCCESS")) {
                        SearchBean searchBean = new SearchBean();
                        searchBean.setMobile_no(etPhoneNumber.getText().toString().trim());
                        searchBean.setSocity_id(strSociety_Id);
                        searchBean.setWatchman_id(strUserOrWatchmenId);
                        if (callCheck.equals("SEARCH"))
                            new PostData(new Gson().toJson(searchBean), SearchActivity.this, CallFor.SEARCH).execute();
                        if (callCheck.equals("EXIT"))
                            new GetData(SearchActivity.this, CallFor.EXIT_VISITOR_LIST, "").execute();
                        if(db.getPurposeTableCount()!=loginResponse.getUser().getPurposeCount())
                        {
                            new GetData(SearchActivity.this,CallFor.PURPOSE_LIST,"").execute();
                        }
                        if(db.getFlatCount()!=loginResponse.getUser().getFlatCount())
                        {
                            new GetData(this, CallFor.FLATSLIST, "").execute();
                        }

                    }
                } catch (Exception ex) {
                }
            }

            if (callFor.equals(CallFor.SEARCH)) {
                btnSubmit.setEnabled(true);
                try {
                    if (searchResponse.getCode().equals("SUCCESS")) {
                        SavedDate = SheredPref.getDate(this);
                       /* if (SavedDate == "") {
                            SheredPref.setDate(this, GetTodayDate());
                            new GetData(SearchActivity.this, CallFor.FLATSLIST, "").execute();
                        }
                        if (compareToDay(GetTodayDate(), SavedDate) == 1) {
                            SheredPref.setDate(this, GetTodayDate());
                            //db.deleteContact();
                            new GetData(SearchActivity.this, CallFor.FLATSLIST, "").execute();
                            //NameAndFlat.clear();
                        }*/
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
                        //Log.e("strFlat_No "," "+strFlat_No);
                        String flatInfo = null;
                        String lastFlat = null;
                        try {
                            if(strFlat_No.contains(",")){
                                String fVal[] = strFlat_No.split(",");
                                lastFlat = db.getFlatInfo(strFlat_No.split(",")[fVal.length-1]);
                                flatInfo = strFlat_No;
                            }else{

                                flatInfo = db.getFlatInfo(strFlat_No);
                                lastFlat = flatInfo;
                            }
                        } catch (Exception ex) {
                            Log.e("Ex "," "+ex);
                        }
                        Intent Go = new Intent(SearchActivity.this, GetDetailsActivity.class);
                        Go.putExtra("CALL_FROM", "Search_Activity");
                        Go.putExtra("FIRST_NAME", strFirst_name);
                        Go.putExtra("LAST_NAME", strLast_name);
                        Go.putExtra("MOBILE_NO", strMobile_no);
                        Go.putExtra("VISIT_PURPOSE", strPerpose_Of_Visit);
                        Go.putExtra("FLAT_NO", flatInfo);
                        Go.putExtra("FLAT_NO_LAST", lastFlat);
                        Go.putExtra("VEHICLE_NO", strVehicle_No);
                        Go.putExtra("TOTAL_VISITERS", "" + strTotalVisiters);
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
                        callCheck = "SEARCH";
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
                db.deleteContact();
                NameAndFlat.clear();
                for (int i = 0; i < flatResponce.getAvailFlats().size(); i++) {
                    NameAndFlat.add(flatResponce.getAvailFlats().get(i).getFlatNo() + "-" + flatResponce.getAvailFlats().get(i).getFlatOwnerName());
                }

                for (int i = 0; i < NameAndFlat.size(); i++) {
                    db.addFlatList(new FlatsList(NameAndFlat.get(i)));
                }
            }

            if (callFor.equals(CallFor.EXIT_VISITOR_LIST)) {
                if (exitResponce.getCode().equals("SUCCESS")) {
                    visitorBeanDataSet.clear();
                    if (exitResponce.getVisitors().size() == 0) {
                        hideUI();
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        noVisitor.setVisibility(View.GONE);
                        for (int i = 0; i < exitResponce.getVisitors().size(); i++) {
                            VisitorBean visitorBean = new VisitorBean();
                            visitorBean.setVisitor_id(exitResponce.getVisitors().get(i).getVisitor_id());
                            visitorBean.setFirst_name(exitResponce.getVisitors().get(i).getFirst_name());
                            visitorBean.setLast_name(exitResponce.getVisitors().get(i).getLast_name());
                            visitorBean.setNameInHindi(exitResponce.getVisitors().get(i).getNameInHindi());
                            visitorBean.setNo_of_visitors(exitResponce.getVisitors().get(i).getNo_of_visitors());
                            visitorBean.setFlat_no(exitResponce.getVisitors().get(i).getFlat_no());
                            visitorBean.setMobile_no(exitResponce.getVisitors().get(i).getMobile_no());
                            visitorBean.setVehicle_no(exitResponce.getVisitors().get(i).getVehicle_no());
                            visitorBean.setIn_time(exitResponce.getVisitors().get(i).getIn_time());
                            visitorBeanDataSet.add(visitorBean);
                        }
                        // Out Work
                        FromSearch =false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            ActionBar actionBar = getActionBar();
                            if (actionBar != null) {
                                actionBar.setTitle("RecyclerView");
                            }
                        }

                        // Layout Managers:
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));

                        // Item Decorator:
                        //recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
                        recyclerView.setItemAnimator(new FadeInLeftAnimator());

                        ShowNoVisitor = 1;
                        mAdapter = new RecyclerViewAdapter(this, visitorBeanDataSet);
                        ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setOnScrollListener(onScrollListener);
                    }

                } else if (searchResponse.getCode().equals("NEED_LOGIN")) {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name(strUserName);
                    userBean.setUser_password(strPassword);
                    userBean.setUser_role_id("4");
                    callCheck = "EXIT";
                    new PostData(new Gson().toJson(userBean), SearchActivity.this, CallFor.LOGIN).execute();

                }
            }
            if (callFor.equals(CallFor.EXIT_VISITOR)) {

                if (exitResponce.getCode().equals("SUCCESS")) {
                    searchText.setText("");
                    searchText.clearFocus();
                    if(FromSearch == true){
                        new GetData(SearchActivity.this, CallFor.EXIT_VISITOR_LIST, "").execute();
                    }
                    ShowToastSuccess("Success");
                }
            }

            if (callFor.equals(CallFor.PURPOSE_LIST)) {
                Log.e("IN CALL","");
                if (visitPurposeResponce.getCode().equals("SUCCESS")) {
                    int size = visitPurposeResponce.getVisitPurposeBeans().size();
                    String eng[] = new String[size];
                    String hin[] = new String[size];
                    String mar[] = new String[size];
                    for(int i=0;i<size;i++){
                        if(i==0){
                            eng[i] = visitPurposeResponce.getVisitPurposeBeans().get(i).getEnglishText();
                            hin[i] = visitPurposeResponce.getVisitPurposeBeans().get(i).getHindiText();
                            mar[i] = visitPurposeResponce.getVisitPurposeBeans().get(i).getMarathiText();
                        }else{
                            eng[i] = visitPurposeResponce.getVisitPurposeBeans().get(i).getEnglishText();
                            hin[i] = visitPurposeResponce.getVisitPurposeBeans().get(i).getHindiText();
                            mar[i] = visitPurposeResponce.getVisitPurposeBeans().get(i).getMarathiText();
                        }
                    }
                    db.deletePurposeData();
                    db.insertIntoPurpose(eng,hin,mar);
                    Log.e("DATA CNT "," "+db.getPurposeTableCount());
                }
            }
        }
    }

    public String GetTodayDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
        //return "20-Oct-2016";
    }

    public static int compareToDay(String strdate1, String strdate2) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date1 = null;
        Date date2 = null;

        try {
            date1 = formatter.parse(strdate1);
            date2 = formatter.parse(strdate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1 == null || date2 == null) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(date1).compareTo(sdf.format(date2));
    }


    public void ShowToast(String Text) {
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

    public void ShowToastSuccess(String Text)
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_action, (ViewGroup) findViewById(R.id.custom));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.FILL_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void ShowToastOfEntry() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.FILL_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void hideUI() {
        recyclerView.setVisibility(View.GONE);
        noVisitor.setVisibility(View.VISIBLE);
    }

    public static void OutTime(int val){
        if(val==0){
            outLayout.setVisibility(View.VISIBLE);
            noVisitorList.setVisibility(View.GONE);
            new GetData((BaseActivity) context, CallFor.EXIT_VISITOR_LIST, "").execute();
        }else{
            outLayout.setVisibility(View.GONE);
            noVisitorList.setVisibility(View.VISIBLE);

        }
    }

}
