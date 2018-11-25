package com.team15x3.caucse.takecareoftherefrigerator;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertFoodActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener{
    DatePickerDialog datePickerDialog;
DatePicker datePicker;
/*    DbHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ArrayAdapter<String> adapter, adapter2, adapter3;*/
    private int j=0;
    DatePicker datePicker2;
    private boolean isBarcodeRecognized = false;
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
    protected TextView tvIngredients, tvAllergyIngredient, tvNutrientServing;
    protected TableLayout table;
    private DBController dbController;

    ArrayList<String> biggest = new ArrayList<String>(Arrays.asList("가공식품","냉장/냉동/반찬/간편식","건강/친환경 식품","정육/계란류","쌀/잡곡","채소","수산물/해산물/건어물"));
    ArrayList<String> medium_list = new ArrayList<>();
    ArrayList<String> smallest_list = new ArrayList<>();

    Button btnBarcode, btnAdd, btnCancel,btnFoodImage,btnExpirationDate;
    String myBarcode;
    Spinner spinQuantity, spinAlarmDate;
    Spinner spinBiggest, spinMedium, spinSmallest;
    ArrayAdapter bigadapter, mediadapter, smalladapter;
    EditText edtName;
    TextView tvSellByDate, tvUseByDate;
    ImageView ivFoodImage;
    LinearLayout linShowInformation;
    TimePickerFragment timePickerFragment = new TimePickerFragment();

    class APIProcessing extends AsyncTask<String, String, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(InsertFoodActivity.this);
        protected APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);

        @Override
        protected void onPostExecute(Void aVoid) {
            asyncDialog.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            Log.d("Progress","on preExecute called");
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("please wait..");
            asyncDialog.show();

            Log.d("Progress","progress showed");
            super.onPreExecute();
        }

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

                        if (foodArrayList.size() != 0) {
                            InsertFood = foodArrayList.get(0);
                            edtName.setText(InsertFood.getFoodName());
                            parseJsonFromFoodID(InsertFood.getFoodID());
                        } else {
                            Toast.makeText(getApplicationContext(),"we don't have information",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("Check Response","response not successful : "+response.code());
                    }
                }

                @Override
                public void onFailure(Call<EatSightAPI> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("CheckResponse","response on fail : ");
                }
            });
        }

        public void parseJsonFromFoodID(String foodID) {
            Call<Food> call = mApiInterface.getDetailFoodInformation(foodID);

            call.enqueue(new Callback<Food>() {
                @Override
                public void onResponse(Call<Food> call, Response<Food> response) {
                    if (response.isSuccessful()) {
                        InsertFood = response.body();

                        if (response.body().getAllergyList() != null) {
                            InsertFood.setAllergyList(response.body().getAllergyList());
                        }
                        if (response.body().getMaterialList() != null) {
                            InsertFood.setMaterialList(response.body().getMaterialList());
                        }

                        //setting information on the activity
                        InsertFood.setIsFromGallery(false);
                        Picasso.with(getApplicationContext())
                                .load(InsertFood.getThumbnailUrl())
                                .into(ivFoodImage);
                        btnFoodImage.setVisibility(View.INVISIBLE);
                        linShowInformation.setVisibility(View.VISIBLE);

                        FoodInfoActivity.SetInformationOfFood(InsertFood, tvIngredients, tvAllergyIngredient, tvNutrientServing, table, getApplicationContext());

                        if(InsertFood.getFoodClassifyName() != null) {
                            //setSpinners(getApplicationContext());
                            setAdapter(dbController.getParentIndex(InsertFood.getFoodClassifyName()));
                        }

                    } else {
                        Log.d("Check Response","response not successful : "+response.code());
                    }
                }

                @Override
                public void onFailure(Call<Food> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("Check Response","response not successful");
                }
            });
        }

        @Override
        protected Void doInBackground(String... strings) {
            parseJsonFromBarcode(strings[0]);

            return null;
        }
    }


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
        tvSellByDate = (TextView)findViewById(R.id.tvSellByDate);
        linShowInformation = (LinearLayout)findViewById(R.id.linShowInformation);
        tvIngredients = (TextView)findViewById(R.id.tvIngredients);
        tvAllergyIngredient = (TextView)findViewById(R.id.tvAllergyIngredient);
        tvNutrientServing = (TextView)findViewById(R.id.tvNutrientServing);
        table = (TableLayout)findViewById(R.id.table);
        spinBiggest = (Spinner)findViewById(R.id.spinBiggest);
        spinMedium = (Spinner)findViewById(R.id.spinMedium);
        spinSmallest = (Spinner)findViewById(R.id.spinSmallest);
        tvUseByDate = (TextView)findViewById(R.id.tvUseByDate);

        dbController = new DBController(this);
        setSpinners(this);
        spinSmallest.setOnItemSelectedListener(this);
        spinMedium.setOnItemSelectedListener(this);
        spinBiggest.setOnItemSelectedListener(this);
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
            onActivityResult(REQUEST_BARCODE, RESULT_OK ,  new Intent());
        }

        if(view == btnAdd){
            if(edtName.getText().toString().isEmpty()){
                Toast.makeText(this,"Please write food name",Toast.LENGTH_SHORT).show();
                return;
            }

            //Todo: check there is no error
            setFoodInformation();

            ArrayList<Food> exFoodList = User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList();

            int i = 0;
            boolean isFood = false;
            for (i = 0; i < exFoodList.size(); i++) {
                if (InsertFood.getFoodID() == null && InsertFood.getFoodName().equals(exFoodList.get(i).getFoodName())) {
                    if (InsertFood.getUseByDate().equals(exFoodList.get(i).getUseByDate()) && InsertFood.getFoodClassifyName().equals(exFoodList.get(i).getFoodClassifyName())) {
                        isFood = true;
                        break;
                    }
                }
                else if (InsertFood.getFoodID() != null && InsertFood.getFoodID().equals(exFoodList.get(i).getFoodID())) {
                    if (InsertFood.getUseByDate().equals(exFoodList.get(i).getUseByDate()) && InsertFood.getFoodClassifyName().equals(exFoodList.get(i).getFoodClassifyName())) {
                        isFood = true;
                        break;
                    }
                }
            }

            if (!isFood) {
                User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().add(InsertFood);
            } else {
                int count = User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().get(i).getCount();
                count += InsertFood.getCount();
                User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().get(i).setCount(count);
                InsertFood.setCount(count);
            }

            //todo:save
           final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase.getInstance().getReference().child("users").child(myUid).child("refriList").child(User.INSTANCE.getRefrigeratorList().
                    get(User.INSTANCE.getCurrentRefrigerator()).
                    getName()).child(InsertFood.getFoodName() + InsertFood.getUseByDate()).setValue(InsertFood);


            final String abc = User.INSTANCE.getRefrigeratorList().
                    get(User.INSTANCE.getCurrentRefrigerator()).
                    getName();
            FirebaseDatabase.getInstance().getReference("users").child(myUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                        if(snapshot.child("grouprefriList").getKey()!=null) {

                            Iterator<DataSnapshot> iter = snapshot.getChildren().iterator();

                            while (iter.hasNext()) {
                                DataSnapshot data = iter.next();
                                //그룹 냉장고
                                if(data.getKey().equals(abc))
                                {


                                    Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                                    while(iterator.hasNext())
                                    {
                                        DataSnapshot user_data = iterator.next();
                                        if(user_data.getKey().equals("users"))
                                        {
                                            for(long i=0;i<user_data.getChildrenCount();i++) {
                                                Iterator<DataSnapshot> useriter = user_data.getChildren().iterator();
                                                while (useriter.hasNext()) {
                                                    DataSnapshot user_uid= useriter.next();
                                                    Log.d("sss111",user_uid.getKey());
                                                    FirebaseDatabase.getInstance().getReference().child("users").child(user_uid.getKey()).child("refriList").child(abc).child(InsertFood.getFoodName() + InsertFood.getUseByDate()).setValue(InsertFood);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }else
                        {

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

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
                //Log.d("BARCODE REQUESTCODE",requestCode+"");
                myBarcode = result.getContents(); //get barcode number
                //myBarcode = "8887140112280";
                Toast.makeText(getApplicationContext(), myBarcode, Toast.LENGTH_SHORT).show();
                //api parsing , get information
                APIProcessing api = new APIProcessing();
                api.execute(myBarcode);
            }
        }
    }


    public void doTakePhotoAction() {
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
        String msg = String.format("%d - %d - %d",year, monthOfYear+1, dayOfMonth);
        tvSellByDate.setText(msg);

        Year = year;
        Month = monthOfYear + 1;
        Day = dayOfMonth;

        Calendar cal = Calendar.getInstance();
        cal.set(Year, Month, Day);

        SimpleDateFormat date_format = new SimpleDateFormat("yyyy - MM - dd", Locale.KOREA);
        String sellByDate = date_format.format(cal.getTime());
        //String sellByDate = Integer.toString(Year * 10000 + (Month + 1) * 100 + Day);
        InsertFood.setSellByDate(sellByDate);

        if(sellByDate != null) {
            String useDate = FoodProcessing.getUsebyDateFromSellbyDate(cal);
            String str = useDate.substring(0,4)+" - "+useDate.substring(4,6)+" - "+useDate.substring(6);
            tvUseByDate.setText(str);
        }
    }

    private void setFoodInformation(){
        InsertFood.setFoodName(edtName.getText().toString().trim());
        InsertFood.setCount(spinQuantity.getSelectedItemPosition()+1);
        InsertFood.setFoodClassifyName((String)spinSmallest.getSelectedItem());
        InsertFood.setUseByDate(tvUseByDate.getText().toString()); // .replace('/', '-'));
        InsertFood.setFoodName(edtName.getText().toString());
        InsertFood.setCount(spinQuantity.getSelectedItemPosition()+1);

        Calendar cal = Calendar.getInstance();
        cal.set(Year, Month, Day);
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy . MM . dd", Locale.KOREA);
        String sellByDate = date_format.format(cal.getTime());
        InsertFood.setSellByDate(sellByDate);

        //InsertFood.setSellByDate(Integer.toString(Year * 10000 + (Month + 1) * 100 + Day));
        InsertFood.setD_Day(spinAlarmDate.getSelectedItemPosition()+1);
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
                        .setTitle("notice")
                        .setMessage("Depot permission denied. If you want to use it, you need to allow the permission directly in the settings. ")
                        .setNeutralButton("setting", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:"+getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
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
                        Toast.makeText(this, "You need to enable that permission.",Toast.LENGTH_SHORT).show();
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

    private void setAdapter(ArrayList<Integer> list){
        synchronized (spinMedium) {
            synchronized (spinSmallest) {
                spinBiggest.setSelection(list.get(0));
                dbController.getMediumList(medium_list, list.get(0));
                mediadapter.notifyDataSetChanged();
                spinMedium.setSelection(list.get(1));
                dbController.getSmallList(smallest_list,list.get(0), list.get(1));
                smalladapter.notifyDataSetChanged();
                spinSmallest.setSelection(list.get(2));
            }
        }
    }

    private void setSpinners(Context context) {
        final ArrayList<Integer> spinQuantityList = new ArrayList<>();
        for(int i = 0; i<100 ;i++){
            spinQuantityList.add(i+1);
        }

        ArrayAdapter spinQuantityAdapter;
        spinQuantityAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item,spinQuantityList);
        spinQuantity.setAdapter(spinQuantityAdapter);

        final ArrayList<String> spinAlarmDateList = new ArrayList<>();
        for(int i = 0; i<15;i++){
            spinAlarmDateList.add((i+1)+"days before exp");
        }
        ArrayAdapter spinAlarmAdapter;
        spinAlarmAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item,spinAlarmDateList);
        spinAlarmDate.setAdapter(spinAlarmAdapter);

        bigadapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, biggest);
        spinBiggest.setAdapter(bigadapter);
        mediadapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, medium_list);
        spinMedium.setAdapter(mediadapter);
        smalladapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, smallest_list);
        spinSmallest.setAdapter(smalladapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getAdapter() == spinBiggest.getAdapter()){
            dbController.getMediumList(medium_list,position);
            mediadapter.notifyDataSetChanged();
            dbController.getSmallList(smallest_list,spinBiggest.getSelectedItemPosition(),position);
            smalladapter.notifyDataSetChanged();
        }else if(parent.getAdapter() == spinMedium.getAdapter()){
            dbController.getSmallList(smallest_list,spinBiggest.getSelectedItemPosition(),position);
            smalladapter.notifyDataSetChanged();
        }else if(parent.getAdapter() == spinSmallest.getAdapter()){

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
