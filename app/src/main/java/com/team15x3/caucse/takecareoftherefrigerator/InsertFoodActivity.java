package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InsertFoodActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    private final int NO_DATA = 3;
    private static final int REQUEST_TAKE_PHOTO = 100;
    private static final int PICK_FROM_ALBUM = 101;
    private static final int CROP_FROM_IMAGE = 102;
    final Calendar calendar = Calendar.getInstance();
    private String absolutePath;
    private Uri ImageCaptureUri;
    private int Day = calendar.get(Calendar.DAY_OF_MONTH);
    private int Month =calendar.get(Calendar.MONTH);
    private int Year = calendar.get(Calendar.YEAR);
    private Food InsertFood;

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
            case PICK_FROM_ALBUM:{
                ImageCaptureUri = data.getData();
            }
            case REQUEST_TAKE_PHOTO:{
                if(resultCode == RESULT_OK){
                    try{
                        Log.d("REQUEST_TAKE_PHOTO","OK");
                        galleryAddpic();

                        ivFoodImage.setImageURI(ImageCaptureUri);
                    }catch (Exception e){
                        Log.d("REQUEST_TAKE_PHOTO",e.toString());
                    }
                }else{
                    Toast.makeText(this, "stop taking picture",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case CROP_FROM_IMAGE:{
                if(resultCode !=RESULT_OK) return;

                final Bundle extras = data.getExtras();
                String filePath  = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TakeCareOfTheRefrigerator/"+System.currentTimeMillis()+"jpg";
                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    ivFoodImage.setImageBitmap(photo);
                    ivFoodImage.setVisibility(View.VISIBLE);
                    btnFoodImage.setVisibility(View.INVISIBLE);
                    storeCropImage(photo,filePath);
                    absolutePath = filePath;
                    break;
                }
                File f = new File(ImageCaptureUri.getPath());
                if(f.exists()) f.delete();

            }
        }
            //IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            //myBarcode = result.getContents(); //get barcode number
            //Toast.makeText(getApplicationContext(), myBarcode, Toast.LENGTH_SHORT).show();
            //api parsing , get information


        //Food f = mApiProcessing.parseJsonFromBarcode(myBarcode);
        //edtName.setText(f.getFoodName().toString());
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
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }




    private void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/FoodPicture";
        File directory_FoodPicture = new File(dirPath);

        if(!directory_FoodPicture.exists()) directory_FoodPicture.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            sendBroadcast(new Intent (Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
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
        InsertFood = new Food();
        InsertFood.setThumbnailUrl(absolutePath);
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
}
