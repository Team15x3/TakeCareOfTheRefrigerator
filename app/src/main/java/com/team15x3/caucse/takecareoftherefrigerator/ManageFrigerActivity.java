package com.team15x3.caucse.takecareoftherefrigerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageFrigerActivity extends AppCompatActivity {

    Button btnAddFriger;
    ListView listview;
    private final ArrayList<Refrigerator> LIST = User.INSTANCE.getRefrigeratorList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_friger);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.activity_list_item, LIST);

        btnAddFriger = (Button)findViewById(R.id.btnAddFriger);
        listview = (ListView)findViewById(R.id.lvRefrigeratorList);
        listview.setAdapter(adapter);

        btnAddFriger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : add new refrigerator / name, search group, invite person
            }
        });


    }
}
