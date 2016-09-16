package vztrack.gls.com.vztracksociety;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vztrack.gls.com.vztracksociety.adapter.AdapterForSpinner;
import vztrack.gls.com.vztracksociety.adapter.FlatsListAdapter;
import vztrack.gls.com.vztracksociety.beans.UserBean;
import vztrack.gls.com.vztracksociety.beans.VisitorBean;
import vztrack.gls.com.vztracksociety.profile.AddUserResponce;
import vztrack.gls.com.vztracksociety.profile.FlatsList;
import vztrack.gls.com.vztracksociety.profile.SheredPref;
import vztrack.gls.com.vztracksociety.utils.CallFor;
import vztrack.gls.com.vztracksociety.utils.CheckConnection;
import vztrack.gls.com.vztracksociety.utils.Constants;
import vztrack.gls.com.vztracksociety.utils.DatabaseHandler;
import vztrack.gls.com.vztracksociety.utils.DbHelper;
import vztrack.gls.com.vztracksociety.utils.LoadImage;
import vztrack.gls.com.vztracksociety.utils.PostData;

public class GetDetailsActivity extends BaseActivity {

    private String strCallFrom;
    private String strMobileNo;
    private String strFirst_Name;
    private String strLast_Name;
    private String strVisit_Purpose;
    private String strFlatNo;
    private String strVehicle_No;
    private String strTotal_Visiters;
    private String strPhoto_String;
    private String strDoc_Url_String;
    private String strPhoto_Url;
    private String strDoc_Url;
    private String WatchmenId;
    private String strFrom;
    private String strWhom;
    private String strSociety_Id;
    private TextView tvName, tvMobileNo;
    private EditText etVehicel_no1, etVehicel_no2, etVehicel_no3, etVehicel_no4, etTotal_Visiters, etWhom, etFrom;
    private ImageView imgPhoto,imgId;
    private Spinner etVisit_Purpose;
    private AutoCompleteTextView etFlat_no;
    private String selectedPurpose;
    private String clickedPhoto;
    Context context;
    DatabaseHandler db;
    DbHelper dbHelper;
    static String encodedPhotoImage = "";
    int DocCheckFlag=0,PhotoCheckFlag = 0, visitors_Count = 0;
    int flat_flag;
    String[] Visitor_Purpose_Array_t1 = {"", "Meeting", "Personal", "Official","Owner", "Courier", "Carpenter", "Driver", "Electrician", "Food Delivery", "Grocery Delivery", "House Maid", "Online Delivery", "Plumber", "Postman", "Pick up","Housekeeping","Painting","Class","Cook","Internet","Maintenance","Pest control","Water Tanker","Other"};
    String[] Visitor_Purpose_Array_t2 = {"SELECT PURPOSE", "मिटिंग", "पर्सनल", "ऑफिस", "मालिक" ,"कूरियर", "कारपेंटर", "ड्राइवर", "इलेक्ट्रीशिय", "फ़ूड  डिलीवरी", "ग्रोसरी  डिलीवरी", "हाउस  मेड", "ऑनलाइन  डिलीवरी ", "प्लम्बर", "पोस्टमैन", "पिक  उप","गृह व्यवस्था","रंग काम","ट्युशन","रसोइया","इंटरनेट","हिफ़ाज़त","कीटक नियंत्रण","पाणी टँकर","ऑथर्"};
    String[] Visitor_Purpose_Array_t3 = {"", "मुलाकात", "निजी", "दफ्तर का", "मालक" ,"संदेशवाहक", "सुतार", "चालक", "इलेक्ट्रीशियन", "भोजन पहुचना", "किराने की डिलीवरी", "घर नौकरानी", "ऑनलाइन वितरण", "प्लम्बर", "डाकिया", "पिक अप","घरकाम","रंग काम","शिक्षण","शिजणे","इंटरनेट","देखभाल","कीटक नियंत्रण","पाणी टँकर","अन्य"};
    String[] FLat_Array;
    List<FlatsList> flatsLists;
    FlatsListAdapter adapter;
    Button btnSave;
    ImageView imgHidden;
    String flatno = "";
    String selection;
    int audioFlag = 0;
    public  static int photo_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = GetDetailsActivity.this;

        dbHelper = new DbHelper(this);
        db = new DatabaseHandler(this);
        flatsLists = null;
        flatsLists = db.getAllFlatList();

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        imgId = (ImageView) findViewById(R.id.imgId);
        tvName = (TextView) findViewById(R.id.etName);
        tvMobileNo = (TextView) findViewById(R.id.etMobileNo);
        btnSave = (Button) findViewById(R.id.btnSave);
        etVisit_Purpose = (Spinner) findViewById(R.id.etPurpose);
        etFlat_no = (AutoCompleteTextView) findViewById(R.id.etOwerName);
        etVehicel_no1 = (EditText) findViewById(R.id.etVehilceNo1);
        etVehicel_no2 = (EditText) findViewById(R.id.etVehilceNo2);
        etVehicel_no3 = (EditText) findViewById(R.id.etVehilceNo3);
        etVehicel_no4 = (EditText) findViewById(R.id.etVehilceNo4);
        etTotal_Visiters = (EditText) findViewById(R.id.etVisiters);
        etWhom = (EditText) findViewById(R.id.etWhom);
        etFrom = (EditText) findViewById(R.id.etFrom);
        imgHidden = (ImageView) findViewById(R.id.imgHidden);

        etFlat_no.setFocusableInTouchMode(false);
        etVehicel_no1.setFocusableInTouchMode(false);
        etVehicel_no2.setFocusableInTouchMode(false);
        etVehicel_no3.setFocusableInTouchMode(false);
        etVehicel_no4.setFocusableInTouchMode(false);
        etTotal_Visiters.setFocusableInTouchMode(false);
        etWhom.setFocusableInTouchMode(false);
        etFrom.setFocusableInTouchMode(false);

        etFlat_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etFlat_no.setFocusableInTouchMode(true);
                return false;
            }
        });

        etVehicel_no1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etVehicel_no1.setFocusableInTouchMode(true);
                return false;
            }
        });


        etVehicel_no2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etVehicel_no2.setFocusableInTouchMode(true);
                return false;
            }
        });

        etVehicel_no3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etVehicel_no3.setFocusableInTouchMode(true);
                return false;
            }
        });

        etVehicel_no4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etVehicel_no4.setFocusableInTouchMode(true);
                return false;
            }
        });

        etTotal_Visiters.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etTotal_Visiters.setFocusableInTouchMode(true);
                return false;
            }
        });

        etWhom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etWhom.setFocusableInTouchMode(true);
                return false;
            }
        });

        etFrom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etFrom.setFocusableInTouchMode(true);
                return false;
            }
        });
        etVisit_Purpose.setAdapter(new AdapterForSpinner(GetDetailsActivity.this, R.layout.custom_spinner, Visitor_Purpose_Array_t1, Visitor_Purpose_Array_t2, Visitor_Purpose_Array_t3));

        etVisit_Purpose.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        selectedPurpose = Visitor_Purpose_Array_t1[position];
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        // IF value for addapter not found then set nothing to it
        if (flatsLists.size() != 0) {
            for (int i = 0; i < flatsLists.size(); i++) {
                if (i == 0) {
                    FLat_Array = new String[flatsLists.size()];
                    FLat_Array[i] = flatsLists.get(i).getFlats().replace(" ", "_");
                }
                FLat_Array[i] = flatsLists.get(i).getFlats().replace(" ", "_");
            }

            adapter = new FlatsListAdapter(this, R.id.etOwerName, R.id.lbl_name, flatsLists);

            etFlat_no.setThreshold(1);
            etFlat_no.setAdapter(adapter);

        }

        etFlat_no.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selection = flatsLists.get(position).getFlats();
                try {
                    flatno = selection.split("-")[0];
                } catch (Exception ex) {
                }
                etWhom.setText(selection.split("-")[1]);
                etVehicel_no1.requestFocus();
                etVehicel_no1.setSelection(etVehicel_no1.getText().length());
                etFlat_no.setSelection(0);

            }
        });

        etVehicel_no1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                etVehicel_no2.setFocusableInTouchMode(true);

                if (et.toString().length() == 2) {
                    String s = et.toString();
                    if (!s.equals(s.toUpperCase())) {
                        s = s.toUpperCase();
                        etVehicel_no1.setText(s);
                    }
                    etVehicel_no2.requestFocus();
                    etVehicel_no2.setSelection(etVehicel_no2.getText().length());
                }
            }
        });

        etVehicel_no2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etVehicel_no3.setFocusableInTouchMode(true);

                if (s.toString().length() == 2) {
                    etVehicel_no3.requestFocus();
                    etVehicel_no3.setSelection(etVehicel_no3.getText().length());
                }
            }
        });

        etVehicel_no3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                etVehicel_no4.setFocusableInTouchMode(true);

                if (et.toString().length() == 2) {
                    String s = et.toString();
                    if (!s.equals(s.toUpperCase())) {
                        s = s.toUpperCase();
                        etVehicel_no3.setText(s);
                    }

                    etVehicel_no4.requestFocus();
                    etVehicel_no4.setSelection(etVehicel_no4.getText().length());
                }
            }
        });

        etVehicel_no4.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
                etVehicel_no3.setText(etVehicel_no3.getText().toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable s) {
                etTotal_Visiters.setFocusableInTouchMode(true);

                if (s.toString().length() == 4) {

                    if (strCallFrom.equalsIgnoreCase("Search_Activity")) {

                    } else {
                        etTotal_Visiters.requestFocus();
                        etTotal_Visiters.setSelection(etTotal_Visiters.getText().length());
                    }
                }
            }
        });

        WatchmenId = SheredPref.getUserOrWatchmenId(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        strCallFrom = extras.getString("CALL_FROM");
        if (strCallFrom.equalsIgnoreCase("Add_User")) {

            photo_flag=1;
            strFirst_Name = extras.getString("FIRST_NAME");
            strLast_Name = extras.getString("LAST_NAME");
            strMobileNo = extras.getString("MOBILE_NO");

            strFlatNo = flatno;
            strVehicle_No = etVehicel_no1.getText().toString().trim() + "-" + etVehicel_no2.getText().toString().trim() + " " + etVehicel_no3.getText().toString().trim() + "-" + etVehicel_no4.getText().toString().trim();

            //strTotal_Visiters = etTotal_Visiters.getText().toString().trim();
            strTotal_Visiters = "1";

            strPhoto_String = extras.getString("PHOTO_URL");
            strDoc_Url_String = extras.getString("DOC_URL");

            byte[] encodeByte = Base64.decode(strPhoto_String, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            imgPhoto.setImageBitmap(bitmap);

            if(strDoc_Url_String.equals(""))
            {
                Log.e("PHOTO1 ","  BLANK");
                imgHidden.setImageResource(R.drawable.no_img);
            }
            else
            {
                Log.e("PHOTO1 "," NOT BLANK");
                byte[] encodeByte1 = Base64.decode(strDoc_Url_String, Base64.DEFAULT);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(encodeByte1, 0, encodeByte1.length);
                Bitmap roundBitmap =  getRoundedCornerBitmap(bitmap1);
                imgId.setImageBitmap(roundBitmap);
            }

            tvName.setText(strFirst_Name + " " + strLast_Name);
            tvMobileNo.setText(strMobileNo);
            visitors_Count = Integer.parseInt(etTotal_Visiters.getText().toString());

        }
        if (strCallFrom.equalsIgnoreCase("Search_Activity")) {
            photo_flag=0;
            strFirst_Name = extras.getString("FIRST_NAME");
            strLast_Name = extras.getString("LAST_NAME");
            strMobileNo = extras.getString("MOBILE_NO");
            strVisit_Purpose = extras.getString("VISIT_PURPOSE");
            strFlatNo = extras.getString("FLAT_NO");

            strVehicle_No = extras.getString("VEHICLE_NO");
            strTotal_Visiters = "1";
            strPhoto_String = extras.getString("PHOTO_URL");
            strDoc_Url_String = extras.getString("DOC_URL");
            strWhom = extras.getString("WHOM");
            strFrom = extras.getString("FROM");

            strPhoto_Url = Constants.BASE_IMG_URL + "/" + strPhoto_String;
            strDoc_Url = Constants.BASE_IMG_URL + "/" + strDoc_Url_String;

            if (strVehicle_No.equals("NO VEHICLE")) {
                etVehicel_no1.setText("MH");
            } else {
                String[] stringArray;
                String[] first_number = new String[0];
                String[] second_number = new String[0];

                try {
                    stringArray = strVehicle_No.trim().split("\\s+");
                    first_number = stringArray[0].split("-");
                    second_number = stringArray[1].split("-");

                    etVehicel_no1.setText(first_number[0]);
                    etVehicel_no2.setText(first_number[1]);
                    etVehicel_no3.setText(second_number[0]);
                    etVehicel_no4.setText(second_number[1]);
                } catch (Exception ex) {
                }
            }


            new LoadImage().loadImage(context, R.drawable.circularimage, strPhoto_Url, imgPhoto, null);

            new UrlToStringImage(strPhoto_Url,GetDetailsActivity.this).execute();

            if(!strDoc_Url.trim().equals("/uploads/docs/vzd_"))
            {
                new LoadImage().loadImage(this, R.drawable.no_img, strDoc_Url, imgHidden, null);
                new LoadImage().loadImage(context, R.drawable.no_img, strDoc_Url, imgId, null);

            }
            else
            {
                imgHidden.setImageResource(R.drawable.no_img);
            }

            tvName.setText(strFirst_Name + " " + strLast_Name);
            tvMobileNo.setText(strMobileNo);
            etFlat_no.setText(strFlatNo);
            flatno = strFlatNo;

            etTotal_Visiters.setText(strTotal_Visiters);
            etFrom.setText(strFrom);
            etWhom.setText(strWhom);
            visitors_Count = Integer.parseInt(etTotal_Visiters.getText().toString());
        }
        strSociety_Id = SheredPref.getSociety_Id(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        etFlat_no.requestFocus();
        etFlat_no.requestFocusFromTouch();
    }

    public void Remove(View v) {
        if (visitors_Count == 1) {
            etTotal_Visiters.setText("" + visitors_Count);
        } else {
            visitors_Count = visitors_Count - 1;
            etTotal_Visiters.setText("" + visitors_Count);
        }

    }

    public void Add(View v) {
        visitors_Count = visitors_Count + 1;
        etTotal_Visiters.setText("" + visitors_Count);
    }

    public void Save(View v) {
        btnSave.setEnabled(false);
        flat_flag = 0;
        for (int i = 0; i < flatsLists.size(); i++)
        {
            if (flatsLists.get(i).getFlats().equals(etFlat_no.getText().toString().trim()) || flatsLists.get(i).getFlats().split("-")[0].equals(etFlat_no.getText().toString().trim())) {
                flat_flag = 1;
            }
        }

        String newFlatNo = flatno;
        try {
            flatno = newFlatNo.split("-")[0].trim();
        } catch (Exception ex) {
        }

        if (selectedPurpose.equals("")) {
            ShowToast("कृपया आने का उद्देश्य दर्ज करें");
            btnSave.setEnabled(true);
        } else if (etFlat_no.getText().toString().equals("") || flatno.equals("")) {
            ShowToast(" कृपया फ्लैट नंबर दर्ज करें");
            btnSave.setEnabled(true);
        }
        else if (photo_flag==0) {
            ShowToast(" कृपया मुलाक़ाती का फोटो दर्ज करें");
            btnSave.setEnabled(true);
        }
        else if (flat_flag == 0) {
            ShowToast("कृपया वैध फ्लैट नं. चुनिये");
            btnSave.setEnabled(true);
        } else if (etTotal_Visiters.getText().toString().equals("")) {
            ShowToast(" कृपया मुलाक़ाती की संख्या दर्ज करें");
            btnSave.setEnabled(true);
        } else if (etTotal_Visiters.getText().toString().equals("0")) {
            ShowToast(" मुलाक़ाती की गिनती शून्य नहीं होनी चाहिए ");
            btnSave.setEnabled(true);
        } else if (etFrom.getText().toString().equals("")) {
            ShowToast(" कृपया कहा से दर्ज करें");
            btnSave.setEnabled(true);
        } else if (selectedPurpose.toString().equals("SELECT PURPOSE")) {
            ShowToast(" कृपया आने का उद्देश्य दर्ज करें");
        } else {
            CheckConnection ccAccess = new CheckConnection(getApplicationContext());
            Boolean isInternetAvailable = ccAccess.isConnectingToInternet();
            if (isInternetAvailable) {

                VisitorBean visitorBean = new VisitorBean();
                visitorBean.setFirst_name(strFirst_Name);
                visitorBean.setLast_name(strLast_Name);
                visitorBean.setMobile_no(strMobileNo);

                visitorBean.setVisit_purpose(selectedPurpose);
                visitorBean.setFlat_no(flatno);
                strVehicle_No = etVehicel_no1.getText().toString().trim() + "-" + etVehicel_no2.getText().toString().trim() + " " + etVehicel_no3.getText().toString().trim() + "-" + etVehicel_no4.getText().toString().trim();


                if (strVehicle_No.equals("MH- -") || strVehicle_No.equals("- -")) {
                    strVehicle_No = "NO VEHICLE";
                }

                visitorBean.setVehicle_no(strVehicle_No);
                visitorBean.setNo_of_visitors(Integer.parseInt(etTotal_Visiters.getText().toString().trim()));
                visitorBean.setFrom(etFrom.getText().toString().trim().substring(0, 1).toUpperCase() + etFrom.getText().toString().trim().substring(1).toLowerCase());
                visitorBean.setWhomeToVisit(etWhom.getText().toString().trim());
                visitorBean.setWatchman_id(Integer.parseInt(WatchmenId));


                if (strCallFrom.equalsIgnoreCase("Add_User")) {
                    if (PhotoCheckFlag == 0) {
                        visitorBean.setOld(false);
                        visitorBean.setPhoto_url(strPhoto_String);
                    }
                    if (PhotoCheckFlag == 1) {
                        visitorBean.setOld(false);
                        visitorBean.setPhoto_url(encodedPhotoImage);
                    }
                    if (DocCheckFlag == 0) {
                        visitorBean.setOldDoc(false);
                        if(strDoc_Url_String.equals(""))
                        {
                            visitorBean.setDoc_url("");
                        }
                        else {
                            visitorBean.setDoc_url(strDoc_Url_String);
                        }
                    }
                    if (DocCheckFlag == 1) {
                        visitorBean.setOldDoc(false);
                        Bitmap bitmap = ((BitmapDrawable) imgHidden.getDrawable()).getBitmap();
                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                        byte[] ba = bao.toByteArray();
                        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
                        visitorBean.setOld(false);
                        visitorBean.setDoc_url(ba1);
                    }
                }
                if (strCallFrom.equalsIgnoreCase("Search_Activity")) {
                    if (PhotoCheckFlag == 0) {
                        visitorBean.setOld(true);
                        visitorBean.setPhoto_url(strPhoto_String);
                        visitorBean.setDoc_url(strDoc_Url_String);
                    }

                    String encodedDocImage = null;
                    if (PhotoCheckFlag == 1) {
                        visitorBean.setOld(false);
                        visitorBean.setPhoto_url(encodedPhotoImage);
                    }

                    if (DocCheckFlag == 0) {
                        visitorBean.setOldDoc(true);
                        if(strDoc_Url_String.equals(""))
                        {
                            visitorBean.setDoc_url("");
                        }
                        else {
                            visitorBean.setDoc_url(strDoc_Url_String);
                        }
                    }
                    if (DocCheckFlag == 1) {
                        Bitmap bitmap = ((BitmapDrawable) imgHidden.getDrawable()).getBitmap();

                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                        byte[] ba = bao.toByteArray();
                        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

                        visitorBean.setOldDoc(false);
                        visitorBean.setDoc_url(ba1);

                    }
                }
                visitorBean.setSocity_id(Integer.parseInt(strSociety_Id));

                new PostData(new Gson().toJson(visitorBean), GetDetailsActivity.this, CallFor.ADD_NEW_USER).execute();
                btnSave.setEnabled(true);
            } else {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
                String strFlatNo = flatno;
                String strVehicel = etVehicel_no1.getText().toString().trim() + "-" + etVehicel_no2.getText().toString().trim() + " " + etVehicel_no3.getText().toString().trim() + "-" + etVehicel_no4.getText().toString().trim();
                if (strVehicel.equals("MH- -") || strVehicel.equals("- -")) {
                    strVehicel = "NO VEHICLE";
                }
                String strVisitersNo = etTotal_Visiters.getText().toString().trim();
                String strFrom = etFrom.getText().toString().trim();
                String strWhom = etWhom.getText().toString().trim();
                String strSocietyId = SheredPref.getSociety_Id(getApplicationContext());
                ;
                String strPhotoUrl = null;
                if (PhotoCheckFlag == 0) {
                    strPhotoUrl = strPhoto_String;
                }

                if (PhotoCheckFlag == 1) {
                    strPhotoUrl = encodedPhotoImage;
                }
                //String strDocUrl = strPhoto_String;
                String strDate = sdf.format(c.getTime());

                if (dbHelper.insertData(strFirst_Name, strLast_Name, strMobileNo, selectedPurpose, strFlatNo, strVehicel, strVisitersNo, strFrom, strWhom, WatchmenId, strSocietyId, strPhotoUrl, strDoc_Url_String, strDate)) {
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Problem in saving", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                btnSave.setEnabled(true);
                Intent Go = new Intent(GetDetailsActivity.this, SearchActivity.class);
                startActivity(Go);

                SheredPref.setCheck(this, "Show");

                etFlat_no.setText("");
                etWhom.setText("");
                etVehicel_no2.setText("");
                etVehicel_no3.setText("");
                etVehicel_no4.setText("");
                etTotal_Visiters.setText("");
                etFrom.setText("");

            }

        }
    }

    public void ClearData(View v){
        etFlat_no.setText("");
    }
    public void ShowDocument(View v) {
        final Dialog settingsDialog = new Dialog(this, R.style.DialogTheme);

        settingsDialog.getWindow().getAttributes().windowAnimations = R.style.animation_id;

        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View showData = getLayoutInflater().inflate(R.layout.doc_image_layout, null);

        ImageView imageView = (ImageView) showData.findViewById(R.id.doc_imgImage);

        if (strCallFrom.equalsIgnoreCase("Add_User")) {
                if (DocCheckFlag == 0) {
                    if(strDoc_Url_String.equals(""))
                    {
                        imgId.setImageResource(R.drawable.no_img);
                    }
                    else
                    {
                        byte[] encodeByte = Base64.decode(strDoc_Url_String, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        imageView.setImageBitmap(bitmap);
                    }
                }
                if (DocCheckFlag == 1) {
                    Bitmap bitmap = ((BitmapDrawable) imgHidden.getDrawable()).getBitmap();
                    imageView.setImageBitmap(bitmap);
                }
        }
        if (strCallFrom.equalsIgnoreCase("Search_Activity")) {
            if (DocCheckFlag == 0) {
                if(!strDoc_Url.trim().equals("/uploads/docs/vzd_"))
                {
                    new LoadImage().loadImage(this, R.drawable.no_img, strDoc_Url, imageView, null);
                }
                else{
                    imageView.setPadding(30,30,30,30);
                    imageView.setImageResource(R.drawable.no_img);
                }

            }
            if (DocCheckFlag == 1) {
                Bitmap bitmap = ((BitmapDrawable) imgHidden.getDrawable()).getBitmap();
                imageView.setImageBitmap(bitmap);
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                settingsDialog.dismiss();

            }
        });

        settingsDialog.setContentView(showData);
        settingsDialog.setCanceledOnTouchOutside(false);
        settingsDialog.show();

    }


    public void ShowPhoto(View v) {
        final Dialog settingsDialog = new Dialog(this, R.style.DialogTheme);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().getAttributes().windowAnimations = R.style.animationName;
        View showData = getLayoutInflater().inflate(R.layout.image_layout, null);

        TextView ShowUserName = (TextView) showData.findViewById(R.id.tvNameAndNumber);
        ImageView imageView = (ImageView) showData.findViewById(R.id.imgImage);

        if (strCallFrom.equalsIgnoreCase("Add_User")) {
            if (PhotoCheckFlag == 0) {
                byte[] encodeByte = Base64.decode(strPhoto_String, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                imageView.setImageBitmap(bitmap);
            }

            if (PhotoCheckFlag == 1) {
                byte[] encodeByte = Base64.decode(encodedPhotoImage, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                imageView.setImageBitmap(bitmap);
            }
        }
        if (strCallFrom.equalsIgnoreCase("Search_Activity")) {
            if (PhotoCheckFlag == 0) {
                new LoadImage().loadImage(this, R.drawable.circularimage, strPhoto_Url, imageView, null);
            }
            if (PhotoCheckFlag == 1) {
                byte[] encodeByte = Base64.decode(encodedPhotoImage, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                imageView.setImageBitmap(bitmap);
            }

        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                settingsDialog.dismiss();

            }
        });

        ShowUserName.setText(strFirst_Name + " " + strLast_Name + "    " + strMobileNo);

        settingsDialog.setContentView(showData);
        settingsDialog.setCanceledOnTouchOutside(false);
        settingsDialog.show();
    }


    public void EditPhoto(View v) {
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(i, 200);
        clickedPhoto="PHOTO";
    }
    public void EditDoc(View v) {
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(i, 200);
        clickedPhoto="DOC";
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_CANCELED)
        {
            if(audioFlag==1)
            {
                audioFlag=0;
            }
        }

        if (resultCode == RESULT_OK) {

            if (audioFlag == 1) {
                ArrayList<String> matches = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String topResult = matches.get(0);
                etFrom.setText(topResult.toUpperCase());
                audioFlag=0;
            }
            else
            {
                if(clickedPhoto.equals("PHOTO"))
                {
                    Bundle extras = intent.getExtras();
                    Bitmap bmp = (Bitmap) extras.get("data");
                    imgPhoto.setImageBitmap(bmp);
                    GetDetailsActivity.photo_flag=1;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byte_arr = stream.toByteArray();
                    encodedPhotoImage = Base64.encodeToString(byte_arr, 0);
                    PhotoCheckFlag = 1;
                    DeleteFile();
                }
                if(clickedPhoto.equals("DOC"))
                {
                    Bundle extras = intent.getExtras();
                    Bitmap bmp = (Bitmap) extras.get("data");
                    imgHidden.setImageBitmap(bmp);
                    imgId.setImageBitmap(bmp);
                    DocCheckFlag=1;
                    DeleteFile();
                }
            }
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 20;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    @Override
    public void onGetResponse(String response, String callFor) {
        AddUserResponce addUserResponce = new Gson().fromJson(response, AddUserResponce.class);
        if (response == null) {
            return;
        }
        if (callFor.equals(CallFor.ADD_NEW_USER)) {
            btnSave.setEnabled(true);
            try {
                if (addUserResponce.getCode().equals("SUCCESS")) {
                    Intent Go = new Intent(GetDetailsActivity.this, SearchActivity.class);
                    startActivity(Go);
                    photo_flag = 0;
                    SheredPref.setCheck(this, "Show");

                    etFlat_no.setText("");
                    etWhom.setText("");
                    etVehicel_no2.setText("");
                    etVehicel_no3.setText("");
                    etVehicel_no4.setText("");
                    etTotal_Visiters.setText("");
                    etFrom.setText("");
                }
                if (addUserResponce.getCode().equals("NEED_LOGIN")) {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name( SheredPref.getUsername(getApplicationContext()));
                    userBean.setUser_password(SheredPref.getPassword(getApplicationContext()));
                    userBean.setUser_role_id("4");
                    new PostData(new Gson().toJson(userBean), GetDetailsActivity.this, CallFor.LOGIN).execute();
                }

            } catch (Exception ex) {
            }
        }
    }

    public void DeleteFile() {

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");

        if (dir.isDirectory()) {

            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        } else {
        }

        //dir.setLastModified(1);

//        File dir = this.getCacheDir();
//        if (dir != null && dir.isDirectory()) {
//            dir.delete();
//        }
        // new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Camera").notifyAll();
    }

    public void ShowToast(String Text) {
        Toast toast = Toast.makeText(this, Text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_bg);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(20);
        toastTV.setTypeface(null, Typeface.BOLD);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void SpeekFrom(View view) {
        audioFlag = 1;
        startVoiceRecognitionActivity();
    }
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);        //since you only want one, only request 1
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        startActivityForResult(intent, 1234);
    }
}
