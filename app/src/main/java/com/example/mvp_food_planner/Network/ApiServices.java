package com.example.mvp_food_planner.Network;

import com.example.mvp_food_planner.Model.POJO.CategoryFilter;
import com.example.mvp_food_planner.Model.POJO.CountryFilter;
import com.example.mvp_food_planner.Model.Entity.Meal;
import com.example.mvp_food_planner.Model.GenericeResponse;
import com.example.mvp_food_planner.Model.POJO.IngredientFilter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    // Meal API
    // Generic  for any type
    @GET("categories.php")
    Call<GenericeResponse<CategoryFilter>> getCategories();

    @GET("list.php?i=list")
    Call<GenericeResponse<IngredientFilter>> getIngredients();

    @GET("list.php?a=list")
    Call<GenericeResponse<CountryFilter>> getCountries();

    @GET("random.php?count=5") // get 5 rand elements
    Call<GenericeResponse<Meal>> getMeal();

    // meal by id
    @GET("lookup.php")
    Call<GenericeResponse<Meal>> getMealById(@Query("i") String mealId);

    // filter meals of this Category // www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("filter.php")
    Call<GenericeResponse<Meal>> getMealsByCategory(@Query("c") String category);

    // filter meal by this area
    @GET("filter.php")
    Call<GenericeResponse<Meal>> getMealsByArea(@Query("a") String area);

    // filter meal by this ingredient
    @GET("filter.php")
    Call<GenericeResponse<Meal>> getMealsByIngredient(@Query("i") String ingredient);


}
