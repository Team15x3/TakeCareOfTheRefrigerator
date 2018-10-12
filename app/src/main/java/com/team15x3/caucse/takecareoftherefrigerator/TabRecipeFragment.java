package com.team15x3.caucse.takecareoftherefrigerator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TabRecipeFragment extends Fragment{

    Button btnButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        btnButton = (Button)container.findViewById(R.id.btnButton);
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        return inflater.inflate(R.layout.tab_recipe_fragment, container,false);
    }
}
