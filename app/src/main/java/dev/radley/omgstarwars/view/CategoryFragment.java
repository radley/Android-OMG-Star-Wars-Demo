package dev.radley.omgstarwars.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

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
import dev.radley.omgstarwars.viewmodels.CategoryViewModelFactory;
import dev.radley.omgstarwars.viewmodels.CategoryViewModel;
import dev.radley.omgstarwars.listeners.RecyclerTouchListener;
import dev.radley.omgstarwars.adapters.CategoryAdapter;

public class CategoryFragment extends Fragment {

    private Button retryButton;
    private CategoryAdapter adapter;
    private CategoryViewModel viewModel;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerTouchListener touchListener;


    public static CategoryFragment newInstance(String category) {
        CategoryFragment myFragment = new CategoryFragment();

        Bundle args = new Bundle();
        args.putString(SearchExtras.CATEGORY, category);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        assert getArguments() != null;
        String category = getArguments().getString(SearchExtras.CATEGORY);

        View mView = inflater.inflate(R.layout.fragment_category, container, false);

        retryButton = mView.findViewById(R.id.retry_button);
        progressBar = mView.findViewById(R.id.progress);

        recyclerView = mView.findViewById(R.id.grid);

        assert category != null;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount(category)));

        viewModel = ViewModelProviders.of(this,
                new CategoryViewModelFactory(Objects.requireNonNull(getActivity()).getApplication(), category))
                .get(CategoryViewModel.class);

        observeViewModel(category);
        return mView;
    }

    private void observeViewModel(String category) {
        viewModel.getList(category).observe(this, swModel -> {

            if(swModel != null) {

                if(adapter != null) {
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new CategoryAdapter(getActivity(), swModel);
                    adapter.setOnBottomReachedListener(position -> viewModel.getNextPage());

                    recyclerView.setAdapter(adapter);
                }

                retryButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);


                //list.setVisibility(View.VISIBLE);
                //adapter.notifyDataSetChanged();
            } else {
                //list.setVisibility(View.GONE);
            }
        });
    }

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

    @Override
    public void onStop() {
        super.onStop();

        adapter = null;
        recyclerView.removeOnItemTouchListener(touchListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.clear();
    }

    private int getSpanCount(String id) {

        if(id.equals(getResources().getString(R.string.category_id_starships)) ||
                id.equals(getResources().getString(R.string.category_id_vehicles)) ) {
            return getResources().getInteger(R.integer.grid_span_count_wide);
        } else {
            return getResources().getInteger(R.integer.grid_span_count_tall);
        }
    }

    // called from activity to scroll back to top
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}
