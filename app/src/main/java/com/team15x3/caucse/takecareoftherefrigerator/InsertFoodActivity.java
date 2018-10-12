package com.team15x3.caucse.takecareoftherefrigerator;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertFoodActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    private final int NO_DATA = 3;
    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_BARCODE =49374;
    private static final int REQUEST_TAKE_PHOTO = 100;
    private static final int REQUEST_TAKE_ALBUM = 101;
    private static final int REQUEST_IMAGE_CROP = 102;
    final Calendar calendar = Calendar.getInstance();
    private String absolutePath;
    private Uri ImageCaptureUri, photoURI, albumURI;
    private int Day = calendar.get(Calendar.DAY_OF_MONTH);
    private int Month =calendar.get(Calendar.MONTH);
    private int Year = calendar.get(Calendar.YEAR);
    protected Food InsertFood = new Food();

    Button btnBarcode, btnAdd, btnCancel,btnFoodImage,btnExpirationDate;
    String myBarcode;
    Spinner spinQuantity, spinAlarmDate;
    EditText edtName;
    TextView tvExpirationDate;
    ImageView ivFoodImage;
    public APIProcessing mApiProcessing = new APIProcessing();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_food);

        btnBarcode = (Button)findViewById(R.id.btnBarcodeInsert);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        edtName = findViewById(R.id.edtFoodName);
        spinAlarmDate = findViewById(R.id.spinAlarmDate);
        spinQuantity = (Spinner)findViewById(R.id.spinQuantity);
        ivFoodImage = (ImageView)findViewById(R.id.ivFoodImage);
        btnFoodImage = (Button)findViewById(R.id.btnFoodImage);
        btnExpirationDate = (Button)findViewById(R.id.btnExpirationDate);
        tvExpirationDate = (TextView)findViewById(R.id.tvExpirationDate);




        final ArrayList<Integer> spinQuantityList = new ArrayList<>();
        for(int i = 0; i<100 ;i++){
            spinQuantityList.add(i+1);
        }

        ArrayAdapter spinQuantityAdapter;
        spinQuantityAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,spinQuantityList);
        spinQuantity.setAdapter(spinQuantityAdapter);




        final ArrayList<String> spinAlarmDateList = new ArrayList<>();
        for(int i = 0; i<15;i++){
            spinAlarmDateList.add((i+1)+"days before exp");
        }
        ArrayAdapter spinAlarmAdapter;
        spinAlarmAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,spinAlarmDateList);
        spinAlarmDate.setAdapter(spinAlarmAdapter);

        btnExpirationDate.setOnClickListener(this);
        btnBarcode.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnFoodImage.setOnClickListener(this);
        ivFoodImage.setOnClickListener(this);

        checkPermission();

    }


    @Override
    public void onClick(View view) {

        if(view == btnBarcode){
            new IntentIntegrator(this).initiateScan();
        }

        if(view == btnAdd){
            if(edtName.getText().toString().isEmpty()){
                Toast.makeText(this,"Please write food name",Toast.LENGTH_SHORT).show();
                return;
            }

            //Todo: check there is no error
            setFoodInformation();
            User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().add(InsertFood);
            Toast.makeText(this, "Add food completely", Toast.LENGTH_SHORT).show();
            Intent returnIntent = new Intent();
            setResult(RESULT_OK,returnIntent);
            finish();

        }

        if(view == btnCancel){
            setResult(RESULT_CANCELED);
            finish();
        }
        if(view == btnExpirationDate){
            Log.d("CHECK_DAY",Year+"/"+Month+"/"+Day);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, InsertFoodActivity.this, Year, Month, Day);
            datePickerDialog.show();
        }
        if(view == ivFoodImage | view == btnFoodImage){
            Log.d("Button Clicked","button clicked!");
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    doTakePhotoAction();
                }
            };

            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    doTakeAlbumAction();
                }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Image Upload");
            builder.setPositiveButton("Take picture",cameraListener);
            builder.setNeutralButton("Album",albumListener);
            builder.setNegativeButton("cancel",cancelListener);
            builder.show();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode !=RESULT_OK) return;

        switch (requestCode){
            case REQUEST_TAKE_ALBUM:{
                if(resultCode == RESULT_OK){
                    //Log.d("REQUEST_TAKE_ALBUM","OK");
                    if(data.getData()!=null){
                        //Log.d("REQUEST_TAKE_ALBUM","OK2");
                        try{
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            //Log.d("REQUEST_TAKE_ALBUM","OK3");
                            cropImage();
                        }catch (Exception e){
                            Log.e("TAKE_ALBUM_SINGLE ERROR",e.toString());
                        }
                    }
                }
                break;
            }
            case REQUEST_TAKE_PHOTO:{
                if(resultCode == RESULT_OK){
                    try{
                        Log.d("REQUEST_TAKE_PHOTO","OK");
                        galleryAddpic();

                        btnFoodImage.setVisibility(View.INVISIBLE);
                        ivFoodImage.setVisibility(View.VISIBLE);
                        ivFoodImage.setImageURI(ImageCaptureUri);
                        InsertFood.setIsFromGallery(true);

                    }catch (Exception e){
                        Log.d("REQUEST_TAKE_PHOTO",e.toString());
                    }
                }else{
                    Toast.makeText(this, "stop taking picture",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_IMAGE_CROP:{
                if(resultCode == RESULT_OK){
                    galleryAddpic();
                    InsertFood.setIsFromGallery(true);
                    ivFoodImage.setImageURI(albumURI);
                    btnFoodImage.setVisibility(View.INVISIBLE);
                    ivFoodImage.setVisibility(View.VISIBLE);
                }
                break;
            }
            case REQUEST_BARCODE:{
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                Log.d("BARCODE REQUESTCODE",requestCode+"");
                myBarcode = result.getContents(); //get barcode number
                Toast.makeText(getApplicationContext(), myBarcode, Toast.LENGTH_SHORT).show();
                //api parsing , get information

                class APIProcessing extends AsyncTask<String, String, Void> {
                    protected APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);

                    /* get food information from barcode */
                    public void parseJsonFromBarcode(String barcode) {
                        Call<EatSightAPI> call = mApiInterface.getFoodInformation("ALL", "barcode",
                                barcode, null, null, null,
                                null, 0, 2);

                        call.enqueue(new Callback<EatSightAPI>() {
                            @Override
                            public void onResponse(Call<EatSightAPI> call, Response<EatSightAPI> response) {
                                if (response.isSuccessful()) {
                                    EatSightAPI eatSightAPI = response.body();
                                    ArrayList<Food> foodArrayList = eatSightAPI.getFoodList();

                                    InsertFood = foodArrayList.get(0);
                                    edtName.setText(InsertFood.getFoodName());

                                    InsertFood.setIsFromGallery(false);
                                    Picasso.with(getApplicationContext())
                                            .load(InsertFood.getThumbnailUrl())
                                            .into(ivFoodImage);
                                    btnFoodImage.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onFailure(Call<EatSightAPI> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

                    public void parseJsonFromFoodID() {
                        Call<ResponseBody> call = mApiInterface.getDetailFoodInformation("10016026");

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        String jsonInfo = response.body().string();
                                        //tvExpirationDate.setText(jsonInfo);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

                    @Override
                    protected Void doInBackground(String... strings) {
                        parseJsonFromBarcode(strings[0]);
                        parseJsonFromFoodID();
                        return null;
                    }
                }

                APIProcessing api = new APIProcessing();
                api.execute(myBarcode);

                //InsertFood = mApiProcessing.parseJsonFromBarcode(myBarcode);

            }

        }
    }


    public void doTakePhotoAction(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent TakePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(TakePictureIntent.resolveActivity(getPackageManager())!=null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch(IOException e){
                    Log.e("captureCamera Error",e.toString());
                }
                if(photoFile != null){
                    Log.d("CHECK AUTHO",getPackageName());
                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(),photoFile);
                    ImageCaptureUri = providerURI;

                    TakePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(TakePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }else{
            Toast.makeText(this, "You cannot access to device",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        String msg = String.format("%d / %d / %d",year, monthOfYear+1, dayOfMonth);
        tvExpirationDate.setText(msg);
        Year = year;
        Month = monthOfYear;
        Day= dayOfMonth;
    }

    private void setFoodInformation(){
        InsertFood.setFoodName(edtName.getText().toString().trim());
        InsertFood.setCount(spinQuantity.getSelectedItemPosition()+1);
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/Pictures","TakeCareOfTheRefrigerator");

        if(!storageDir.exists()){
            Log.i("CurrentPhotoPath",storageDir.toString());
            storageDir.mkdir();
        }
        imageFile = new File(storageDir,imageFileName);
        absolutePath = imageFile.getAbsolutePath();
        InsertFood.setThumbnailUrl(absolutePath);
        return imageFile;
    }

    private void galleryAddpic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(absolutePath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "stored in Album completely",Toast.LENGTH_SHORT).show();

    }

    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            if((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))){
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다. ")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:"+getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},MY_PERMISSION_CAMERA);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_CAMERA:
                for(int i = 0; i<grantResults.length; i++){
                    if(grantResults[i] <0){
                        Toast.makeText(this, "해당권한을 활성화 해야합니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
        }
    }

    public void cropImage(){

        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI,"image/*");
        cropIntent.putExtra("outputX",200);
        cropIntent.putExtra("outputY",200);
        cropIntent.putExtra("aspectX",1);
        cropIntent.putExtra("aspectY",1);
        cropIntent.putExtra("scale",true);
        cropIntent.putExtra("output",albumURI);
        startActivityForResult(cropIntent,REQUEST_IMAGE_CROP);


    }

    public String getRealPathFromURI(Uri contentUri) {

        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Uri uri = Uri.fromFile(new File(path));

        cursor.close();
        return path;
    }
}
