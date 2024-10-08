package com.example.mvp_food_planner.Screens.FavScreen.View;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.mvp_food_planner.Model.Entity.Meal;
import com.example.mvp_food_planner.Model.Repo.MealLocalRepository;
import com.example.mvp_food_planner.R;
import com.example.mvp_food_planner.Screens.FavScreen.Presenter.FavPresenter;
import com.example.mvp_food_planner.Screens.HomeScreen.View.RandomMealAdapter;
import com.example.mvp_food_planner.Screens.MealDetailsScreen.View.MealDetailsFragment;

import java.util.List;


public class FavFragment extends Fragment implements FavoritesView, FavoriteListener {

    RecyclerView recyclerView;
    FavAdapter adapter;
    FavPresenter presenter;
    //CheckBox cbHeart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView = view.findViewById(R.id.fav_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //cbHeart = view.findViewById(R.id.cbHeart2);
        presenter = new FavPresenter((FavoritesView) this, new MealLocalRepository(getContext()));
        presenter.loadSavedMeals();

        return view;
    }

    @Override
    public void onRemoveFromSaved(Meal meal) {
        presenter.removeFromSaved(meal);
    }

    @Override
    public void onMealClicked(Meal meal) {
        if (meal != null && meal.idMeal != null) {
            Bundle bundle = new Bundle();
            bundle.putString("mealId", meal.idMeal);
            MealDetailsFragment detailsFragment = new MealDetailsFragment();
            detailsFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentNav, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), "Meal ID is missing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showFavoriteMeals(List<Meal> meals) {
        adapter = new FavAdapter(meals, getContext(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String error) {
        Log.e(TAG, "showError: " + error);
    }
}