package com.team15x3.caucse.takecareoftherefrigerator;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TabScrapeFragment extends Fragment {

    private final int SHOW_RECIPE_INFORMATION_REQUEST = 3333;
    private ListView lvScrapeList;
    private RecipeAdapter listAdapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_scrape_fragment, container, false);

        lvScrapeList = (ListView)view.findViewById(R.id.lvScrape);
        ArrayList<Recipe>  scrapeList = User.INSTANCE.getScrapeList();
        listAdapter = new RecipeAdapter(view.getContext(), R.layout.recipe_list,scrapeList);
        lvScrapeList.setAdapter(listAdapter);
        lvScrapeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showRecipeInformation(position);
            }
        });

        return view;
    }

    private void showRecipeInformation(int idx ){
        Intent intent = new Intent(view.getContext(),RecipeInfoActivity.class);
        intent.putExtra("list_number",idx);
        startActivityForResult(intent, SHOW_RECIPE_INFORMATION_REQUEST);
    }

}
