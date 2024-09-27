package com.example.mvp_food_planner.Model.Repo;

import com.example.mvp_food_planner.Model.Entity.Meal;
import com.example.mvp_food_planner.Model.POJO.CategoryFilter;
import com.example.mvp_food_planner.Model.POJO.CountryFilter;
import com.example.mvp_food_planner.Network.Client;
import com.example.mvp_food_planner.Network.NetworkCallback;

import java.util.ArrayList;
import java.util.List;

public class Repo {
    private final Client client;

    public Repo() {         //  need to add some parameters
        client = Client.getInstance();
    }

    public void fetchRandomMeals(int count, NetworkCallback<Meal> callback) {
        List<Meal> meals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            client.getRandomMeal(new NetworkCallback<Meal>() {
                @Override
                public void onSuccess(List<Meal> mealList) {
                    meals.addAll(mealList);
                    if (meals.size() == count) {
                        callback.onSuccess(meals);
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    callback.onFailure(errorMessage);
                }
            });
        }
    }

    public void fetchCategories(NetworkCallback<CategoryFilter> callback) {
        client.getCategoriesList(callback);
    }

    public void fetchCountries(NetworkCallback<CountryFilter> callback) {
        client.getCountriesList(callback);
    }
}
