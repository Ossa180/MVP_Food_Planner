package com.example.mvp_food_planner.Screens.FilterScreen.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvp_food_planner.R;


public class ByIngredientFragment extends Fragment implements Searchable {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_by_ingredient, container, false);
    }

    @Override
    public void onSearchQuery(String query) {

    }
}