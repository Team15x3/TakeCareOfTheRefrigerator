package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.zxing.integration.android.IntentIntegrator;

import java.lang.reflect.Array;
import java.util.ArrayList;

class FoodInfoDialog {
    private Context context;

    public FoodInfoDialog(Context context){
        this.context = context;
    }

    public void callFunction(Food food){
        final Dialog dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.food_info_dialog);
        dialog.show();

        final Button okButton = (Button)dialog.findViewById(R.id.okButton);
        final Button deleteButton = (Button)dialog.findViewById(R.id.deleteButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });


    }

}




