package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageFrigerActivity extends AppCompatActivity {

    Button btnAddRefrigerator;
    ListView listview;
    protected ArrayAdapter adapter;
    private final ArrayList<Refrigerator> LIST = User.INSTANCE.getRefrigeratorList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_friger);

        btnAddRefrigerator = (Button)findViewById(R.id.btnAddFriger);
        listview = (ListView)findViewById(R.id.lvRefrigeratorList);

        setRefrigeratorList(this);

        btnAddRefrigerator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //todo : add new refrigerator / name, search group, invite person

                show_dialog();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void show_dialog() {
        final EditText edit_text = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMessage("AlertDialog Content");
        builder.setView(edit_text);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Refrigerator refri = new Refrigerator(edit_text.getText().toString());
                        User.INSTANCE.addRefrigerator(refri);
                        setRefrigeratorList(getApplicationContext());
                        Toast.makeText(getApplicationContext(),edit_text.getText().toString() ,Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }



    private void setRefrigeratorList(Context context){
        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1);
        for(int i = 0; i<LIST.size(); i++){
           adapter.add(LIST.get(i).getName());
        }
        listview.setAdapter(adapter);
    }
}
