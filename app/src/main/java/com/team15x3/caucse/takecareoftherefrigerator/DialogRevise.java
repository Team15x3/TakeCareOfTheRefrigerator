package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.Month;

public class DialogRevise implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Context context;
    private EditText edtFoodName;
    private TextView tvExpirationDate;
    private Button btnExpirationDate;
    private Spinner spinQuantitiy;
    private Spinner spinAlarmDate;
    private Button btnCancel;
    private Button btnRevise;
    int Year, Month, Day;
    private Dialog dialog;
    private Food food;

    public DialogRevise(Context context){
        this.context = context;
    }
    public void callFunction(Food food){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_revise);
        dialog.show();
        this.food = food;

        edtFoodName = (EditText)dialog.findViewById(R.id.edtFoodName);
        tvExpirationDate = (TextView)dialog.findViewById(R.id.tvExpirationDate);
        btnExpirationDate = (Button)dialog.findViewById(R.id.btnExpirationDate);
        spinQuantitiy = (Spinner)dialog.findViewById(R.id.spinQuantity);
        spinAlarmDate = (Spinner)dialog.findViewById(R.id.spinAlarmDate);
        btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
        btnRevise = (Button)dialog.findViewById(R.id.btnRevise);

        //InsertFoodActivity.setSpinners(context, spinQuantitiy, spinAlarmDate);
        edtFoodName.setText(food.getFoodName());
        tvExpirationDate.setText(food.getSellByDate());
        spinAlarmDate.setSelection(food.getD_Day()-1);
        spinQuantitiy.setSelection(food.getCount()-1);
        int exp = Integer.parseInt(food.getSellByDate());
        Year = (int)(exp/10000);
        exp = exp%10000;
        Month = (int)(exp/100);
        exp = exp%100;
        Day = (int)(exp);


        btnCancel.setOnClickListener(this);
        btnExpirationDate.setOnClickListener(this);
        btnRevise.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {
        if(view == btnExpirationDate){
            DatePickerDialog picker = new DatePickerDialog(context, this, Year, Month-1, Day);
            picker.show();
        }else if(view == btnCancel){
            dialog.dismiss();
        }else if(view == btnRevise){
            food.setFoodName(edtFoodName.getText().toString().trim());
            food.setSellByDate(Integer.toString(Year*10000 + (Month+1)*100 + Day));
            food.setD_Day(spinAlarmDate.getSelectedItemPosition()+1);
            food.setCount(spinQuantitiy.getSelectedItemPosition()+1);
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
            dialog.dismiss();
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


}
