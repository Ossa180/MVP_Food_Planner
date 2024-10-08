package com.example.mvp_food_planner.Screens.FilterScreen.ByIngredients.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mvp_food_planner.Model.POJO.IngredientFilter;
import com.example.mvp_food_planner.Model.Repo.Repo;
import com.example.mvp_food_planner.Network.Client;
import com.example.mvp_food_planner.R;
import com.example.mvp_food_planner.Screens.FilterScreen.ByIngredients.Presenter.ByIngredientPresenter;
import com.example.mvp_food_planner.Screens.FilterScreen.FilteredItems.View.FilteredItemFragment;
import com.example.mvp_food_planner.Screens.FilterScreen.View.Searchable;

import java.util.ArrayList;
import java.util.List;


public class ByIngredientFragment extends Fragment implements IngredientView {

    private ByIngredientPresenter presenter;
    private ByIngredientAdapter ingredientAdapter;
    private List<IngredientFilter> ingredients = new ArrayList<>();
    private List<IngredientFilter> filteredIngredients = new ArrayList<>();
    private SearchView searchView;

    public ByIngredientFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_by_ingredient, container, false);

        // Setup RecyclerView with GridLayoutManager
        RecyclerView recyclerView = view.findViewById(R.id.recyclerIngredient);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));  // 3 columns in grid
        ingredientAdapter = new ByIngredientAdapter(getContext(), filteredIngredients, this::onIngredientClicked); // Pass the click listener
        recyclerView.setAdapter(ingredientAdapter);

        // Initialize SearchView
        searchView = view.findViewById(R.id.searchBar); // Ensure this matches the search bar ID
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterIngredients(newText); // Filter ingredients as user types
                return true;
            }
        });

        // Initialize Repo with Client.getInstance()
        Repo repo = new Repo(Client.getInstance());
        presenter = new ByIngredientPresenter(this, repo);  // Initialize presenter
        presenter.getIngredients();  // Fetch ingredients

        return view;
    }

    @Override
    public void showIngredients(List<IngredientFilter> ingredientList) {
        ingredients.clear();
        ingredients.addAll(ingredientList);
        filteredIngredients.clear();
        filteredIngredients.addAll(ingredientList); // Initially show all ingredients
        ingredientAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    // Method to filter ingredients based on search query
    private void filterIngredients(String query) {
        filteredIngredients.clear();
        if (query.isEmpty()) {
            filteredIngredients.addAll(ingredients); // If no search query, show all ingredients
        } else {
            for (IngredientFilter ingredient : ingredients) {
                if (ingredient.getStrIngredient().toLowerCase().contains(query.toLowerCase())) {
                    filteredIngredients.add(ingredient);
                }
            }
        }
        ingredientAdapter.notifyDataSetChanged(); // Refresh the adapter
    }

    // Method to handle ingredient click
    private void onIngredientClicked(IngredientFilter ingredient) {
        FilteredItemFragment filteredItemFragment = new FilteredItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selectedIngredient", ingredient.getStrIngredient());

        // Retrieve any existing selections to append
        // You can retrieve these from your currently selected filters in the previous fragment
        // For example, assuming these are passed as arguments to this fragment:
        if (getArguments() != null) {
            String selectedCategory = getArguments().getString("selectedCategory");
            String selectedArea = getArguments().getString("selectedArea");

            if (selectedCategory != null) {
                bundle.putString("selectedCategory", selectedCategory);
            }
            if (selectedArea != null) {
                bundle.putString("selectedArea", selectedArea);
            }
        }

        filteredItemFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentNav, filteredItemFragment)
                .addToBackStack(null)
                .commit();
    }
}


