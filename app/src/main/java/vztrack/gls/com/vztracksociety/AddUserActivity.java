package vztrack.gls.com.vztracksociety;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import vztrack.gls.com.vztracksociety.utils.CheckConnection;

public class AddUserActivity extends BaseActivity {

    ImageView CapturedImage,CapturedID;
    int flag = 0;
    private String strPhoneNumber;
    private String strSociety_Id;
    EditText etPhoneNumber,etFirstName,etLastName;
    LinearLayout HideLayout,HideLayoutId;
    private String encodedPhotoImage="";
    private String encodedDocImage="";
    CheckConnection cc;
    private Uri fileUri;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "VZtrack";
    public static  String file_name;
    int audioFlag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HideLayout = (LinearLayout) findViewById(R.id.HideLayout);
        HideLayoutId = (LinearLayout) findViewById(R.id.HideLayoutId);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);

        etFirstName.setFocusableInTouchMode(false);
        etLastName.setFocusableInTouchMode(false);

        etFirstName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etFirstName.setFocusableInTouchMode(true);
                return false;
            }
        });

        etLastName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etLastName.setFocusableInTouchMode(true);
                return false;
            }
        });

        Bundle extras = getIntent().getExtras();
        strPhoneNumber= extras.getString("PhoneNumber");
        strSociety_Id= extras.getString("Society_Id");

        etPhoneNumber.setText(strPhoneNumber);
        etPhoneNumber.setKeyListener(null);
    }

    public void CaptureImage(View v)
    {
        flag = 1;
        try {
            StartIntent();
        }
        catch (Exception ex)
        {
        }

    }

    public void CaptureImageId(View v)
    {
        flag = 2;
        StartIntent();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_CANCELED)
        {
            if(audioFlag==1)
            {
                audioFlag=0;
            }
            if(audioFlag==2)
            {
                audioFlag=0;
            }
        }

        if (resultCode == RESULT_OK)
        {
            if(audioFlag==1)
            {
                ArrayList<String> matches = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String topResult = matches.get(0);
                etFirstName.setText(topResult.toUpperCase());
                audioFlag=0;
            }
            if(audioFlag==2)
            {
                ArrayList<String> matches = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String topResult = matches.get(0);
                etLastName.setText(topResult.toUpperCase());
                audioFlag=0;
            }

            if(flag==1)
            {
                HideLayout.setVisibility(View.GONE);
                CapturedImage = (ImageView) findViewById(R.id.CapturedImage);

                previewCapturedImage();
                File imagefile = new File(file_name);
                Bitmap bitmap = decodeFile(imagefile);
                // Bitmap to string conversion
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byte_arr = stream.toByteArray();
                encodedPhotoImage = Base64.encodeToString(byte_arr, 0);

                bitmap = Bitmap.createScaledBitmap(bitmap,400, 350, true);
                CapturedImage.setImageBitmap(bitmap);
                try
                {
                    deleteLatest();
                }
                catch (Exception ex)
                {
                }
                flag=0;
            }
            if(flag==2)
            {
                HideLayoutId.setVisibility(View.GONE);
                CapturedID = (ImageView) findViewById(R.id.CapturedImagePhotoID);

                previewCapturedImage();
                File imagefile = new File(file_name);
                Bitmap bitmap = decodeFile(imagefile);
                // Bitmap to string conversion
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byte_arr = stream.toByteArray();
                encodedDocImage = Base64.encodeToString(byte_arr, 0);

                bitmap = Bitmap.createScaledBitmap(bitmap,400, 350, true);
                CapturedID.setImageBitmap(bitmap);
                try
                {
                    deleteLatest();
                }
                catch (Exception ex)
                {
                }
                flag=0;
            }
        }

      //  DeleteFile();


    }

    public void StartIntent()
    {
       // Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
      //  startActivityForResult(i, 200);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }

    public void Submit(View v)
    {
        if(encodedPhotoImage.equalsIgnoreCase(""))
        {
            ShowToast("मुलाक़ाती फोटो की आवश्यकता");
        }
        else if(encodedPhotoImage.equalsIgnoreCase(""))
        {
            encodedPhotoImage="";
        }
        else if(etFirstName.getText().toString().trim().equalsIgnoreCase(""))
        {
            ShowToast("पहला नाम दर्ज करें");
        }
        else if(etLastName.getText().toString().trim().equalsIgnoreCase(""))
        {
            ShowToast(" अंतिम नाम दर्ज करें");
        }
        else
        {
            String First_name = etFirstName.getText().toString().trim();
            String Last_name = etLastName.getText().toString().trim();

            First_name = First_name.substring(0, 1).toUpperCase() + First_name.substring(1).toLowerCase();
            Last_name = Last_name.substring(0, 1).toUpperCase() + Last_name.substring(1).toLowerCase();

            //deleteLatest();
            DeleteFile();

            Intent Go = new Intent(AddUserActivity.this, GetDetailsActivity.class);
            Go.putExtra("CALL_FROM","Add_User");
            Go.putExtra("FIRST_NAME",First_name);
            Go.putExtra("LAST_NAME",Last_name);
            Go.putExtra("MOBILE_NO",strPhoneNumber);
            Go.putExtra("PHOTO_URL",encodedPhotoImage);
            Go.putExtra("DOC_URL",encodedDocImage);
            startActivity(Go);
        }

    }

    public void DeleteFile()
    {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
        else
        {
        }
    }

    private void deleteLatest() {
        // TODO Auto-generated method stub
        File f = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera" );
        File thumnails = new File(Environment.getExternalStorageDirectory() + "/DCIM/.thumbnails" );

        if (thumnails.isDirectory())
        {
            String[] children = thumnails.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(thumnails, children[i]).delete();
            }
        }
        else
        {
        }

        File [] files = f.listFiles();

        Arrays.sort( files, new Comparator<Object>()
        {
            public int compare(Object o1, Object o2) {
                if (((File)o1).lastModified() > ((File)o2).lastModified()) {
                    return -1;
                } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });
        files[0].delete();

    }


    public void ShowToast(String Text)
    {
        Toast toast = Toast.makeText(this, Text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.color.red);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(20);
        toastTV.setTypeface(null, Typeface.BOLD);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void previewCapturedImage() {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
	 * returning image / video
	 */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
        else {
            return null;
        }
        file_name = mediaFile.toString();
        return mediaFile;
    }


    private Bitmap decodeFile(File f){
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale++;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

    public void SpeekFirst(View view)
    {
        audioFlag=1;
        startVoiceRecognitionActivity();
    }

    public void SpeekLast(View view)
    {
        audioFlag=2;
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
