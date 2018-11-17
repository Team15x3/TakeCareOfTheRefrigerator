package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageFrigerActivity extends AppCompatActivity {

    Button btnAddRefrigerator;
    ListView listview;
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
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }


    private void setRefrigeratorList(Context context){
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1);
        for(int i = 0; i<LIST.size(); i++){
           adapter.add(LIST.get(i).getName());
        }
        listview.setAdapter(adapter);
    }
}
