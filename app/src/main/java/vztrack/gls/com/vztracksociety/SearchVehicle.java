package vztrack.gls.com.vztracksociety;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.Collections;

import vztrack.gls.com.vztracksociety.adapter.CustomList;
import vztrack.gls.com.vztracksociety.beans.UserBean;
import vztrack.gls.com.vztracksociety.profile.LoginResponse;
import vztrack.gls.com.vztracksociety.profile.SearchResponse;
import vztrack.gls.com.vztracksociety.profile.SheredPref;
import vztrack.gls.com.vztracksociety.utils.CallFor;
import vztrack.gls.com.vztracksociety.utils.CheckConnection;
import vztrack.gls.com.vztracksociety.utils.GetData;
import vztrack.gls.com.vztracksociety.utils.PostData;

public class SearchVehicle extends BaseActivity {

    private Spinner spinnerDay;
    private String selectedDay;
    private String[] DayArray = {"Today - आज", "Yesterday - कल", "Day before yesterday - परसो"};
    private Context context;
    private ListView list;
    private int InternetCheckFlag = 0;
    private EditText etVehicel_no1,etVehicel_no2,etVehicel_no3,etVehicel_no4;
    private String strVehicleNo;
    private String[] FlatNo ;
    private String[] VehicleNo ;
    private String[] Time ;
    private String[] OutTime ;
    private String[] OwnerName ;
    private TextView tvOut ;
    private TextView tvShowHide ;
    private String showUI ;
    TextView mTitleTextView,tvFlatAndEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vehicle);
        Bundle extras = getIntent().getExtras();
        showUI= extras.getString("UI_VAL");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerDay = (Spinner) findViewById(R.id.spinnerDay);

        list = (ListView) findViewById(R.id.listVehicle);

        etVehicel_no1 = (EditText) findViewById(R.id.etVehilceNo1);
        etVehicel_no2 = (EditText) findViewById(R.id.etVehilceNo2);
        etVehicel_no3 = (EditText) findViewById(R.id.etVehilceNo3);
        etVehicel_no4 = (EditText) findViewById(R.id.etVehilceNo4);
        tvOut = (TextView) findViewById(R.id.tvOut);
        tvFlatAndEmp = (TextView) findViewById(R.id.tvFlatAndEmp);
        tvShowHide = (TextView) findViewById(R.id.tvShowHide);
        context = SearchVehicle.this;

        getSupportActionBar().setTitle("Loading...");


        if(showUI.equals("0")){
            tvOut.setVisibility(View.GONE);
        }

        if(SheredPref.getType(context).equals("0")){
            tvFlatAndEmp.setText("Flat Number");
        }
        if(SheredPref.getType(context).equals("1")){
            tvFlatAndEmp.setText("Employee");
        }

        // Check Internet Connection
        CheckConnection cc = new CheckConnection(getApplicationContext());

        Boolean isConnectingToInternet = cc.isConnectingToInternet();

        if (isConnectingToInternet) {
            CheckConnection ccAccess = new CheckConnection(getApplicationContext());

            Boolean isInternetAvailable = ccAccess.isConnectingToInternet();

            if (isInternetAvailable) {
                InternetCheckFlag = 1;
            } else {
                InternetCheckFlag = 0;
            }
        } else {
            InternetCheckFlag = 0;
        }


        etVehicel_no1.addTextChangedListener(new TextWatcher()
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
            public void afterTextChanged(Editable et)
            {
                if(et.toString().length()==2)
                {
                    String s=et.toString();
                    if(!s.equals(s.toUpperCase()))
                    {
                        s=s.toUpperCase();
                        etVehicel_no3.setText(s);
                    }

                    etVehicel_no2.requestFocus();
                }
            }
        });


        etVehicel_no2.addTextChangedListener(new TextWatcher()
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
                if(s.toString().length()==2)
                {
                    etVehicel_no3.requestFocus();
                }
            }
        });

        etVehicel_no3.addTextChangedListener(new TextWatcher()
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
            public void afterTextChanged(Editable et)
            {

                if(et.toString().length()==2)
                {
                    String s=et.toString();
                    if(!s.equals(s.toUpperCase()))
                    {
                        s=s.toUpperCase();
                        etVehicel_no3.setText(s);
                    }

                    etVehicel_no4.requestFocus();
                }
            }
        });

        etVehicel_no4.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft)
            {
                etVehicel_no3.setText(etVehicel_no3.getText().toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(s.toString().length()==4)
                {

                }
            }
        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, DayArray);
        spinnerDay.setAdapter(arrayAdapter);

        spinnerDay.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        etVehicel_no2.setText("");
                        etVehicel_no3.setText("");
                        etVehicel_no4.setText("");

                        if (InternetCheckFlag == 1) {


                            if (position == 0) {


                                new GetData(SearchVehicle.this, CallFor.SEARCHVEHICLE, "TOD").execute();

                            }
                            if (position == 1) {

                                new GetData(SearchVehicle.this, CallFor.SEARCHVEHICLE, "YES").execute();

                            }
                            if (position == 2) {

                                new GetData(SearchVehicle.this, CallFor.SEARCHVEHICLE, "DBY").execute();

                            }
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }
    public void SearchVehicleNo(View v)
    {

        if(etVehicel_no1.getText().toString().trim().equals("") ||etVehicel_no2.getText().toString().trim().equals("") || etVehicel_no3.getText().toString().trim().equals("") || etVehicel_no4.getText().toString().trim().equals(""))
        {

            ShowToast("कृपया वाहन क्रमांक दर्ज करें");

            //Toast.makeText(SearchVehicle.this," कृपया वाहन क्रमांक दर्ज करें",Toast.LENGTH_LONG).show();
        }
        else
        {
            strVehicleNo = etVehicel_no1.getText().toString().trim()+"-"+etVehicel_no2.getText().toString().trim()+"%20"+etVehicel_no3.getText().toString().trim()+"-"+etVehicel_no4.getText().toString().trim();
            new GetData(SearchVehicle.this, CallFor.SEARCHVEHICLE, strVehicleNo).execute();
        }


    }

    @Override
    public void onGetResponse(String response, String callFor) {
        if (InternetCheckFlag == 1) {
            SearchResponse searchResponse = new Gson().fromJson(response, SearchResponse.class);
            LoginResponse loginResponse = new Gson().fromJson(response, LoginResponse.class);
            if (response == null) {
                return;
            }
            if (callFor.equals(CallFor.LOGIN)) {
                if (searchResponse.getCode().equals("SUCCESS")) {
                    new GetData(SearchVehicle.this, CallFor.SEARCHVEHICLE, "TOD").execute();
                }
            }

            if (callFor.equals(CallFor.SEARCHVEHICLE)) {
                if (searchResponse.getCode().equals("SUCCESS")) {
                    getSupportActionBar().setTitle("Searched Visitors Count : "+searchResponse.getVisitors().size());
                    for(int i=0;i<searchResponse.getVisitors().size();i++)
                    {
                        if(i==0)
                        {
                            FlatNo = new String[searchResponse.getVisitors().size()];
                            VehicleNo = new String[searchResponse.getVisitors().size()];
                            Time = new String[searchResponse.getVisitors().size()];
                            OutTime = new String[searchResponse.getVisitors().size()];
                            OwnerName = new String[searchResponse.getVisitors().size()];

                            FlatNo[i] = searchResponse.getVisitors().get(i).getFlat_no();
                            VehicleNo[i] =  searchResponse.getVisitors().get(i).getVehicle_no();
                            Time[i] =  searchResponse.getVisitors().get(i).getIn_time();
                            OutTime[i] =  searchResponse.getVisitors().get(i).getOut_time();
                            OwnerName[i] =  searchResponse.getVisitors().get(i).getFirst_name()+" "+searchResponse.getVisitors().get(i).getLast_name()+"\n"+searchResponse.getVisitors().get(i).getNo_of_visitors();
                        }

                        FlatNo[i] = searchResponse.getVisitors().get(i).getFlat_no();
                        VehicleNo[i] =  searchResponse.getVisitors().get(i).getVehicle_no();
                        Time[i] =  searchResponse.getVisitors().get(i).getIn_time();
                        OutTime[i] =  searchResponse.getVisitors().get(i).getOut_time();
                        OwnerName[i] =  searchResponse.getVisitors().get(i).getFirst_name()+" "+searchResponse.getVisitors().get(i).getLast_name()+"\n"+searchResponse.getVisitors().get(i).getNo_of_visitors();
                        Log.e("VAL NO "," "+searchResponse.getVisitors().get(i).getNo_of_visitors());
                    }

                    if(searchResponse.getVisitors().size()==0)
                    {
                        list.setVisibility(View.GONE);
                        tvShowHide.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        for (int i = 0; i < FlatNo.length / 2; i++) {
                            String temp = FlatNo[i]; // swap value
                            FlatNo[i] = FlatNo[FlatNo.length - 1 - i];
                            FlatNo[FlatNo.length - 1 - i] = temp;
                        }

                        // Reverse the Array

                        for (int i = 0; i < VehicleNo.length / 2; i++) {
                            String temp = VehicleNo[i]; // swap value
                            VehicleNo[i] = VehicleNo[VehicleNo.length - 1 - i];
                            VehicleNo[VehicleNo.length - 1 - i] = temp;
                        }

                        for (int i = 0; i < Time.length / 2; i++) {
                            String temp = Time[i]; // swap value
                            Time[i] = Time[Time.length - 1 - i];
                            Time[Time.length - 1 - i] = temp;
                        }

                        for (int i = 0; i < OutTime.length / 2; i++) {
                            String temp = OutTime[i]; // swap value
                            OutTime[i] = OutTime[OutTime.length - 1 - i];
                            OutTime[OutTime.length - 1 - i] = temp;
                        }

                        for (int i = 0; i < OwnerName.length / 2; i++) {
                            String temp = OwnerName[i]; // swap value
                            OwnerName[i] = OwnerName[OwnerName.length - 1 - i];
                            OwnerName[OwnerName.length - 1 - i] = temp;
                        }

                        list.setVisibility(View.VISIBLE);
                        tvShowHide.setVisibility(View.GONE);

                        CustomList adapter = new CustomList(SearchVehicle.this, FlatNo, VehicleNo,Time,OutTime,OwnerName,showUI);
                        list.setAdapter(adapter);
                    }

                }
                else if (searchResponse.getCode().equals("NEED_LOGIN")) {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name(SheredPref.getUsername(context));
                    userBean.setUser_password(SheredPref.getPassword(context));
                    userBean.setUser_role_id("4");
                    new PostData(new Gson().toJson(userBean), SearchVehicle.this, CallFor.LOGIN).execute();

                }
            }
        }
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

}
