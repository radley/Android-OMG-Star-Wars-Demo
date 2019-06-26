package dev.radley.omgstarwars.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.viewmodels.CategoryViewModel;
import dev.radley.omgstarwars.listeners.RecyclerTouchListener;
import dev.radley.omgstarwars.adapters.CategoryAdapter;

public class CategoryFragment extends Fragment {


    private CategoryAdapter adapter;
    private CategoryViewModel viewModel;
    private ProgressBar progressBar;
    private ProgressBar launchProgressBar;
    private RecyclerView recyclerView;
    private RecyclerTouchListener touchListener;


    /**
     * Create instance of category fragment
     *
     * @param category
     * @return CategoryFragent
     */
    public static CategoryFragment newInstance(String category) {
        CategoryFragment myFragment = new CategoryFragment();

        Bundle args = new Bundle();
        args.putString(SearchExtras.CATEGORY, category);
        myFragment.setArguments(args);
        return myFragment;
    }

    /**
     * - setup layout in view
     * - setup viewModel
     * - get category id from bundle and pass it to the viewModel
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return view View
     */
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        assert getArguments() != null;
        String category = getArguments().getString(SearchExtras.CATEGORY);

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        progressBar = view.findViewById(R.id.progress);
        launchProgressBar = view.findViewById(R.id.launch_progress);
        recyclerView = view.findViewById(R.id.grid);

        assert category != null;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount(category)));

        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        observeViewModel(category);
        return view;
    }

    /**
     * Add viewModel observers for
     *  - list updates
     *  - loading state
     *  - error state
     *
     * @param category String
     */
    private void observeViewModel(String category) {
        viewModel.getList(category).observe(this, swModel -> {

            if(adapter != null) {
                adapter.notifyDataSetChanged();
            } else {
                adapter = new CategoryAdapter(swModel);
                adapter.setOnBottomReachedListener(position -> viewModel.getNextPage());

                recyclerView.setAdapter(adapter);
            }
            recyclerView.setVisibility(View.VISIBLE);
            launchProgressBar.setVisibility(View.GONE);

        });

        viewModel.getLoadError().observe(this, (Boolean error) -> {

            if(error) {
                Toast.makeText(getActivity(), getString(R.string.error_message, viewModel.getId()), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                launchProgressBar.setVisibility(View.GONE);
            }
        });

        viewModel.getLoading().observe(this, (Boolean isLoading) -> {

            if(launchProgressBar.getVisibility() == View.GONE)
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }

    /**
     * Start listeners
     */
    @Override
    public void onStart() {
        super.onStart();

        touchListener = new RecyclerTouchListener(getContext()) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {

                startActivity(DetailExtras.getIntent(Objects.requireNonNull(getActivity()),
                        viewModel.getItem(position)));
            }
        };

        recyclerView.addOnItemTouchListener(touchListener);
    }

    /**
     * Stop listeners & remove adapter
     */
    @Override
    public void onStop() {
        super.onStop();

        adapter = null;
        recyclerView.removeOnItemTouchListener(touchListener);
    }

    /**
     * Clear viewModel on exit
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.dispose();
    }

    /**
     * Get recyclerView span count based on model type
     *
     * @param id
     * @return spac count resource
     */
    private int getSpanCount(String id) {

        if(id.equals("starships") ||
                id.equals("vehicles") ) {
            return getResources().getInteger(R.integer.grid_span_count_wide);
        } else {
            return getResources().getInteger(R.integer.grid_span_count_tall);
        }
    }

    /**
     * Called from activity when tab is double-tapped
     * Enables recyclerView to scroll back to top
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}
