package com.team15x3.caucse.takecareoftherefrigerator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabRecipeFragment extends Fragment {

    ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
    ArrayList<Food>   foodArrayList = new ArrayList<Food>();

    TextView textView;

    Button btnButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_recipe_fragment, container,false);
        btnButton = (Button)view.findViewById(R.id.btnButton);
        textView  = (TextView)view.findViewById(R.id.textView);

        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                class DataToActivity extends AsyncTask<Void, Void, Void> {
                    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    private DatabaseReference databaseReference = firebaseDatabase.getReference();

                    public void getRecipeIngredientFromFirbase() {
                        databaseReference = firebaseDatabase.getReference("Recipe" + "/" + "Recipe3" + "/" + "data");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                                    String str = messageData.child("IRDNT_NM").getValue().toString();

                                    for (int i = 0 ; i < foodArrayList.size(); i++) {
                                        if (foodArrayList.get(i).getFoodName().equals(str)) {
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

                                    if(Integer.parseInt(messageData.child("RN").getValue().toString())==6105)
                                        break;
                                }

                                getRecipeBasicFromFirebase();
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
                        getRecipeIngredientFromFirbase();
                        return null;
                    }
                }

                FoodProcessing foodProcessing = User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodProcess();
                foodArrayList = foodProcessing.getFoodListNearExpirationDate();

                DataToActivity dataToActivity = new DataToActivity();
                dataToActivity.execute();
            }
        });

        return view;
    }
}
