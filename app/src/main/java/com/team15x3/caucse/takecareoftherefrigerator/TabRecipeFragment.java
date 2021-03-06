package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TabRecipeFragment extends Fragment {

    //public static ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
    public static ArrayList<Recipe> recipeArrayList;
    private final int SHOW_RECIPE_INFORMATION_REQUEST = 3333;
    ArrayList<Food>   foodArrayList = new ArrayList<Food>();
    protected ArrayList<Recipe> cpRecipeList;
    private ListView lvRecipeList;
    private EditText edtSearch;
    private static RecipeAdapter adapter;
    private ArrayList<Integer> findIndex = new ArrayList<>();
    View view;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_recipe_fragment, container,false);
        lvRecipeList = (ListView)view.findViewById(R.id.lvRecipeList);
        edtSearch = (EditText)view.findViewById(R.id.edtRecipeSearch);
        //if(cpRecipeList != null && cpRecipeList.size() !=0) cpRecipeList.clear();

        class DataToActivity extends AsyncTask<Void, Void, Void> {
            private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            private DatabaseReference databaseReference = firebaseDatabase.getReference();
            ProgressDialog asyncDialog = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
                Log.d("Progress","onpreExcute called");
                asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                asyncDialog.setMessage("please wait..");
                asyncDialog.show();

                Log.d("Progress","progress showed");
                super.onPreExecute();
            }


            public void getRecipeIngredientFromFirebase() {
                databaseReference = firebaseDatabase.getReference("Recipe" + "/" + "Recipe3" + "/" + "data");
                databaseReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        recipeArrayList = new ArrayList<Recipe>();

                        for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                            String str = messageData.child("IRDNT_NM").getValue().toString();

                            ArrayList<String> foodClassifyNameList = new ArrayList<String>();

                            ArrayList<Allergy> allergyList = User.INSTANCE.getAllergyList();
                            foodArrayList = User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList();

                            for (int i = 0; i < foodArrayList.size(); i++) {
                                ArrayList<Allergy> tempList = foodArrayList.get(i).getAllergyList();
                                for (int j = 0; j < allergyList.size(); j++) {
                                    if (tempList.contains(allergyList.get(j))) {
                                        foodArrayList.remove(i);
                                    }
                                }
                            }

                            for (int i = 0; i < foodArrayList.size(); i++) {
                                if (foodArrayList.get(i).getFoodClassifyName().contains("/")) {
                                    String[] arr = foodArrayList.get(i).getFoodClassifyName().split("/");
                                    for (int j = 0; j < arr.length; j++) {
                                        if (!foodClassifyNameList.contains(arr[j])) {
                                            foodClassifyNameList.add(arr[j]);
                                        }
                                    }
                                } else {
                                    foodClassifyNameList.add(foodArrayList.get(i).getFoodClassifyName());
                                }
                            }

                            for (int j = 0; j < foodClassifyNameList.size(); j++) {
                                if (str.equals(foodClassifyNameList.get(j))) {
                                    String recipe_id = messageData.child("RECIPE_ID").getValue().toString();
                                    String ingredient_name = messageData.child("IRDNT_NM").getValue().toString();
                                    String ingredient_volume = messageData.child("IRDNT_CPCTY").getValue().toString();
                                    String ingredient_order_number = messageData.child("IRDNT_SN").getValue().toString();
                                    String ingredient_type_name = messageData.child("IRDNT_TY_NM").getValue().toString();
                                    String ingredient_type_code = messageData.child("IRDNT_TY_CODE").getValue().toString();

                                    Ingredient ingredient = new Ingredient(recipe_id, ingredient_name, ingredient_volume, ingredient_order_number, ingredient_type_name, ingredient_type_code);
                                    Recipe recipe = new Recipe();
                                    recipe.setRecipeID(recipe_id);
                                    recipe.getIngredientList().add(ingredient);
                                    recipeArrayList.add(recipe);
                                }
                            }

                            if(Integer.parseInt(messageData.child("RN").getValue().toString()) == 6105)
                                break;
                        }
                        getRecipeBasicFromFirebase();
                        asyncDialog.dismiss();

                        //setRecipeList();
                        //adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            public void getRecipeBasicFromFirebase() {
                databaseReference = firebaseDatabase.getReference("Recipe" + "/" + "Recipe2" + "/" + "data");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (int i = 0; i < recipeArrayList.size(); i++) {
                            for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                                if (recipeArrayList.get(i).getRecipeID().equals(messageData.child("RECIPE_ID").getValue().toString())) {
                                    recipeArrayList.get(i).setRecipeName(messageData.child("RECIPE_NM_KO").getValue().toString());
                                    recipeArrayList.get(i).setSummary(messageData.child("SUMRY").getValue().toString());
                                    recipeArrayList.get(i).setCookingTime(messageData.child("COOKING_TIME").getValue().toString());
                                    recipeArrayList.get(i).setQuantity(messageData.child("QNT").getValue().toString());
                                    recipeArrayList.get(i).setDifficulty(messageData.child("LEVEL_NM").getValue().toString());
                                    recipeArrayList.get(i).setImageURL(messageData.child("IMG_URL").getValue().toString());

                                    break;
                                }
                            }
                        }

                        for (int i = 0; i < recipeArrayList.size(); i++) {
                            if (recipeArrayList.get(i).getRecpieName() == null) {
                                recipeArrayList.remove(i);
                            }
                        }

                        setRecipeList();
                        getRecipeCourseFromFirebase();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            public void getRecipeCourseFromFirebase() {
                databaseReference = firebaseDatabase.getReference("Recipe" + "/" + "Recipe1" + "/" + "data");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (int i = 0; i < recipeArrayList.size(); i++) {
                            for (DataSnapshot messageData : dataSnapshot.getChildren()) {

                                if (i == 15) {
                                    Log.d("SS","SS");
                                }

                                if (recipeArrayList.get(i).getRecipeID().equals(messageData.child("RECIPE_ID").getValue().toString())) {
                                    String recipe_id = messageData.child("RECIPE_ID").getValue().toString();
                                    String cooking_no = messageData.child("COOKING_NO").getValue().toString();
                                    String cooking_dc = messageData.child("COOKING_DC").getValue().toString();

                                    String image_url = null;
                                    if (messageData.child("STRE_STEP_IMAGE_URL").getValue() != null) {
                                        image_url = messageData.child("STRE_STEP_IMAGE_URL").getValue().toString();
                                    }

                                    String tip = null;
                                    if (messageData.child("STEP_TIP").getValue() != null) {
                                        tip = messageData.child("STEP_TIP").getValue().toString();
                                    }

                                    Cooking cooking = new Cooking(recipe_id, cooking_no, cooking_dc, image_url, tip);
                                    recipeArrayList.get(i).getCookingCourseList().add(cooking);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            protected Void doInBackground(Void... voids) {
                getRecipeIngredientFromFirebase();
                return null;
            }
        }

        DataToActivity dataToActivity = new DataToActivity();

        if (User.INSTANCE.getRefrigeratorList().size() == 0) {
            Toast.makeText(this.getContext(), "empty",Toast.LENGTH_SHORT).show();
        }
        else if (User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().size() != 0) {
            dataToActivity.execute();
        }else{
            Toast.makeText(this.getContext(), "empty",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void showRecipeInformation(int idx ){
        Intent intent = new Intent(view.getContext(),RecipeInfoActivity.class);
        if(edtSearch.getText().toString().length() == 0){
            intent.putExtra("list_number",idx);
        }else{
            intent.putExtra("list_number",findIndex.get(idx));
        }
        startActivityForResult(intent, SHOW_RECIPE_INFORMATION_REQUEST);
    }

    private void setRecipeList(){

        try{
           cpRecipeList = new ArrayList<Recipe>();
           cpRecipeList.clear();
           cpRecipeList.addAll(recipeArrayList);

           if(recipeArrayList.isEmpty()){
               Toast.makeText(getContext(),"empty",Toast.LENGTH_SHORT).show();
           } else {
               adapter = new RecipeAdapter(view.getContext(), R.layout.recipe_list, cpRecipeList);
               lvRecipeList.setAdapter(adapter);
               lvRecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Log.d("scs","scs");
                       Toast.makeText(getContext(), position + " item clicked ", Toast.LENGTH_SHORT).show();
                       showRecipeInformation(position);
                       //todo
                   }
               });

               edtSearch.addTextChangedListener(new TextWatcher() {
                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) { }
                   @Override
                   public void afterTextChanged(Editable s) {
                        String str = edtSearch.getText().toString();
                        search(str);

                   }
               });

           }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void search(String text){
        Log.d("SEARCH",text);
        findIndex.clear();
        cpRecipeList.clear();
        if(text.length() == 0){
            cpRecipeList.addAll(recipeArrayList);
        }else{
            for(int i = 0; i<recipeArrayList.size();i++){

                if(recipeArrayList.get(i).getRecpieName() == null) continue;
                if(recipeArrayList.get(i).getRecpieName().toLowerCase().contains(text)){
                    cpRecipeList.add(recipeArrayList.get(i));
                    findIndex.add(i);
                }
            }
        }
        adapter.notifyDataSetChanged();
        Log.d("SEARCH","notify clear");
    }
}
