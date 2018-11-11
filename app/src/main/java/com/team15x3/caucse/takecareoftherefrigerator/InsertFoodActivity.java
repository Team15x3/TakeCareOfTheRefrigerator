package com.team15x3.caucse.takecareoftherefrigerator;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    protected TextView tvIngredients, tvAllergyIngredient, tvNutrientServing;
    protected TableLayout table;

    final ArrayList<String> biggest = new ArrayList<String>(Arrays.asList("가공식품","냉장/냉동/반찬/간편식","건강/친환경 식품","정육/계란류","쌀/잡곡","채소","수산물/해산물/건어물"));

    Button btnBarcode, btnAdd, btnCancel,btnFoodImage,btnExpirationDate;
    String myBarcode;
    Spinner spinQuantity, spinAlarmDate, spinBiggest, spinMedium, spinSmallest;
    EditText edtName;
    TextView tvSellByDate;
    ImageView ivFoodImage;
    LinearLayout linShowInformation;

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


        setSpinners(this);

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
            InsertFood.setFoodName(edtName.getText().toString());
            InsertFood.setCount(spinQuantity.getSelectedItemPosition()+1);
            InsertFood.setExpirationDate(Integer.toString(Year * 10000 + (Month + 1) * 100 + Day));
            InsertFood.setD_Day(spinAlarmDate.getSelectedItemPosition()+1);
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
                                    parseJsonFromFoodID(InsertFood.getFoodID());
                                }
                            }

                            @Override
                            public void onFailure(Call<EatSightAPI> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

                    public void parseJsonFromFoodID(String foodID) {
                        Call<Food> call = mApiInterface.getDetailFoodInformation(foodID);

                        call.enqueue(new Callback<Food>() {
                            @Override
                            public void onResponse(Call<Food> call, Response<Food> response) {
                                if (response.isSuccessful()) {
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

                                }
                            }

                            @Override
                            public void onFailure(Call<Food> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

                    @Override
                    protected Void doInBackground(String... strings) {
                        parseJsonFromBarcode(strings[0]);
                        return null;
                    }
                }

                APIProcessing api = new APIProcessing();
                api.execute(myBarcode);
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
        tvSellByDate.setText(msg);
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





    private void setSpinners(Context context){
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

        ArrayAdapter spinCategoryAdapter;
        spinCategoryAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, biggest);
        spinBiggest.setAdapter(spinCategoryAdapter);
        SpinnerListener();



        spinSmallest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category =(String) spinSmallest.getSelectedItem();
                //todo : bring the category use-by-date
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SpinnerListener(){


        spinBiggest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> list = null;
                if(position == 0){
                    list = new ArrayList<String>(Arrays.asList("라면/컵라면/면식품","분유/두유/이유식",
                            "우유/요구르트/치즈/아이스크림","초콜릿/캔디/껌","과자/시리얼","커피/녹차/차",
                            "생수/음료/탄산수","안주류","견과류","밀가루/설탕/소금/조미료","식용유/참기름/파스타/소스",
                            "간장/고추장/장류","참치캔/통조림류","즉석밥/카레짜장/즉석식품","주류"));

                }else if(position == 1) {
                    list = new ArrayList<String>(Arrays.asList("간편가정식", "식빵/잼/베이커리",
                            "떡/특산물음식", "단무지/반찬", "김치", "만두/피자/냉동식품", "햄/어묵/맛살/면류 냉장식품"));

                }else if(position == 2){
                    list = new ArrayList<String>(Arrays.asList("꿀/로얄젤리","홍상/인삼/건강즙",
                            "친환경 가공식품","한차/건강차재료","다이어트/헬스보조식품","건강식품(성분별)"));
                }else if(position ==3){
                    list = new ArrayList<String>(Arrays.asList("알류/정육"));
                }else if(position == 4){
                    list = new ArrayList<String>(Arrays.asList("혼합곡/기능성잡곡","수수/조/깨/잡곡","콩/보리",
                            "찹쌀/현미/흑미","쌀"));
                }else if(position == 5){
                    list = new ArrayList<String>(Arrays.asList("두부/콩나물"));
                }else if(position == 6){
                    list = new ArrayList<String>(Arrays.asList("조미김/생김","건오징어/어포/육포"));
                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                spinMedium.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinMedium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> list = null;
                switch(spinBiggest.getSelectedItemPosition()){
                    case 0:
                        if(position == 0){
                            list = new ArrayList<>(Arrays.asList("냉면/메밀","파스타","당면","우동 숙면","쌀국수/월남쌈",
                                    "국수/칼국수/콩국수","컵라면","봉지라면"));
                        }else if(position == 1){
                            list = new ArrayList<>(Arrays.asList("분유이유식","아기과자","임산 수유부용 식품","냉장두유","선물용두유","아기두유",
                                    "성인두유","아기음료"));
                        }else if(position == 2){
                            list = new ArrayList<>(Arrays.asList("곡물우유","연유","아이스크림","버터/마가린/생크림","치즈",
                                    "떠먹는 요구르트","마시는 요구르트","멸균우유","딸기/초코/커피우유","어린이우유","고칼슘우유","저지방/무지방우유","우유"));
                        }else if(position == 3){
                            list = new ArrayList<>(Arrays.asList("엿","수입초콜릿/사탕","젤리","껌","막대/츄잉사탕","사탕","초코바/양갱","초콜릿","카라멜"));
                        }else if(position == 4) {
                            list = new ArrayList<>(Arrays.asList("상온소세지","시리얼","빵류","수입과자","한과/전통과자","맛밤",
                                    "파이/카스타드/소프트쿠키","쿠키/비스킷","쌀/옥수수과자","스낵"));
                        }else if(position == 5){
                            list = new ArrayList<>(Arrays.asList("커피믹스","헤즐넛,카푸치노향커피믹스","블랙믹스(설탕함유)","블랙믹스(무설탕)","원두커피","인스턴트커피","캡슐커피","코코아/핫초코","프림","아이스커피/아이스티","녹차/현미녹차",
                                    "둘글레차/옥수수차/메밀차","홍차/허브차/보이차","주전자용차(알곡/티백)","율무차/땅콩차/견과차","유자차/모과차","한차/생강차/전통차","기타차류","다이어트차"));
                        }else if(position == 6){
                            list = new ArrayList<>(Arrays.asList("토마토/망고/자몽주스","포도/사과주스","오렌지/감귤주스","탄산수","식혜/수정과/전통음료","수입생수","어린이음료","국내생수","음료세트","티음료","커피음료","무알콜맥주","스포츠/이온/비타민/숙취해소","환타/웰치스/레몬에이드류","콜라/사이다","냉장과일주스/쿨피스류","기타과일주스","매실/알로에/블루베리주스"));
                        }else if(position == 7){
                            list = new ArrayList<>(Arrays.asList("기타가공품","어육가공품","건조저장육류"));
                        }else if(position == 8){
                            list = new ArrayList<>(Arrays.asList("건포도/건과일","땅콩/아몬드류"));
                        }else if(position == 9){
                            list = new ArrayList<>(Arrays.asList("빵/떡믹스","밀가루/요리가루","기타조미료","식품첨가물","인공감미료","후추/향신료/와사비","조미료","참꺠/들깨","고춧가루","소금","설탕"));

                        }else if(position == 10){
                            list = new ArrayList<>(Arrays.asList("대두유/옥수수유","기타기름","드레싱","양념/소스","파스타소스","케챱/마요네즈",
                                    "물엿/액젓","식초/음용식초","올리브유/포도씨유","현미유/쌀눈유/해바라기유","카롤라유","참기름/들기름"));
                        }else if(position == 11){
                            list = new ArrayList<>(Arrays.asList("초고추장/볶음고추장","고추장","간장","쌈장","된장"));
                        }else if(position == 12){
                            list = new ArrayList<>(Arrays.asList("곡류가공품","팥빙수재료","농산통조림","과일통조림","고등어/꽁치/골뱅이/번데기","반찬통조림/닭가슴살","스팸/돈육통조림","참치캔"));
                        }else if(position == 13){
                            list = new ArrayList<>(Arrays.asList("카레/즉석카레","햇반/즉석밥/누룽지","선식","즉석국/밥양념","죽/스프","덮밥/덮밥소스","짜장/즉석짜장"));
                        }else if(position == 14){
                            list = new ArrayList<>(Arrays.asList("소주","맥주","막걸리","기타주류"));
                        }
                        break;
                    case 1:
                        if(position == 0){
                            list = new ArrayList<>(Arrays.asList("또띠아","간식/디저트/샐러드","보쌈/야식류","순대/족발","볶음류","삼각김밥/죽/면류","국/탕/찌개","냉동 밥/스파게티","찜"));
                        }else if(position == 1){
                            list = new ArrayList<>(Arrays.asList("식빵/일반빵","베이킹도구/재료","냉동생지/즉석빵","호두파이/와플/츄러스","베이클/머핀/도너츠","롤케익/카스텔라/쿠키","케익/케익선물세트","찐빵/호빵","과일잼","땅콩버터"));
                        }else if(position == 2){
                            list = new ArrayList<>(Arrays.asList("백설기떡","떡국떡/떡볶이떡","특산물음식","떡"));
                        }else if(position == 3){
                            list = new ArrayList<>(Arrays.asList("콩식품","청국장/찌게양념/소스","젓갈","간장/양념게장","수산반찬","축산반찬","농산반찬","단무지/무쌈/무절임"));
                        }else if(position == 4){
                            list = new ArrayList<>(Arrays.asList("양파/파/부추김치/기타","갓김치/고들빼기","물김치/백김치/동치미","깍두기/총각/열무김치","혼합김치","일반김치","절임배추/김치양념","묵은지","맛김치/볶음김치"));
                        }else if(position == 5){
                            list = new ArrayList<>(Arrays.asList("피자","만두피","냉동간식","냉동반찬","만두"));
                        }else if(position == 6){
                            list = new ArrayList<>(Arrays.asList("소시지/베이컨","햄/김밥재료","냉장간식","냉장면류","맛살","유부","어묵"));
                        }
                        break;
                    case 2:
                        if(position == 0){
                            list = new ArrayList<>(Arrays.asList("국산꿀","수입꿀","꿀가공품","로얄젤리/프로폴리스"));
                        }else if(position == 1){
                            list = new ArrayList<>(Arrays.asList("인삼차","인삼","건강즙/과일즙","산삼배양근","어린이홍삼","홍삼선물세트","홍인삼음료/홍삼차/사탕","홍삼절편/정과/양갱","홍삼분말/캡슐/환","홍삼액","홍삼뿌리군/농축액"));
                        }else if(position == 2){
                            list = new ArrayList<>(Arrays.asList("통조림/쨈","커피/차","과자/캔디","면류/시리얼/즉석식품","우유/요구르트/치즈/유가공","음료/건강식품","분말류/장류/유지류/소스류","친환경 가공 선물세트","냉장냉동"));
                        }else if(position == 3){
                            list = new ArrayList<>(Arrays.asList("한차","건강차재료"));
                        }else if(position ==4){
                            list = new ArrayList<>(Arrays.asList("다이어트보조식품","헬스보충식"));
                        }else if(position == 5){
                            list = new ArrayList<>(Arrays.asList("종합비타민","비오틴","비타민A","비파민B","비타민C","비타민D","비타민E","빌베리추출물","오메가3","감마리놀렌산","철분","엽산","칼슘","아연",
                                    "초유","마그네슘","쏘팔메토","글루코사민","루테인","콜라겐","하알루론산","코엔자임큐텐","키토산",
                                    "스쿠알렌","유산균","알로에","식이섬유","클로렐라","스피루리나","기타 건강식품","일반의약품","어린이건강","밀크씨슬","녹차추출물","옥타코사놀","엠에스엠","공액리놀렌산","대두이소플라본",
                                    "매실추출물","백수오","레시틴","가르시니아캄보지아추출물","회화나무 열매 추출물","홍경전 추출물","난소화성말토덱스트린","은행잎추출물","셀레늄","베타카로틴","피크노제놀-프랑스해안송껍질추출물","잔티젠"));
                        }
                        break;
                    case 3:
                        if(position == 0){
                            list = new ArrayList<>(Arrays.asList("메추리알"));
                        }else if(position == 1){
                            list = new ArrayList<>(Arrays.asList("계육"));
                        }
                        break;
                    case 4:
                        if(position == 0){
                            list = new ArrayList<>(Arrays.asList("혼합곡","기능성잡곡"));
                        }else if(position == 1){
                            list = new ArrayList<>(Arrays.asList("수수","조류","기장","깨","율무/녹두","기타잡곡"));
                        }else if(position == 2){
                            list = new ArrayList<>(Arrays.asList("보리","콩","팥","서리태"));
                        }else if(position == 3){
                            list = new ArrayList<>(Arrays.asList("찹쌀","현미","흑미"));
                        }else if(position == 4){
                            list = new ArrayList<>(Arrays.asList("쌀"));
                        }
                        break;
                    case 5:
                        if(position == 0){
                            list = new ArrayList<>(Arrays.asList("두부/콩나물"));
                        }
                        break;
                    case 6:
                        if(position == 0){
                            list = new ArrayList<>(Arrays.asList("조미김/생김"));
                        }else if(position == 1){
                            list = new ArrayList<>(Arrays.asList("건오징어/어포/육포"));
                        }

                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                spinSmallest.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
