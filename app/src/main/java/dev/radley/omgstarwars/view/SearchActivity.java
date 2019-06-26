package dev.radley.omgstarwars.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.adapters.SearchAdapter;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.listeners.RecyclerTouchListener;
import dev.radley.omgstarwars.utilities.FormatUtils;
import dev.radley.omgstarwars.viewmodels.SearchViewModel;

import static java.util.Objects.requireNonNull;

public class SearchActivity extends AppCompatActivity {


    private Handler queryDelay = new Handler();
    private ProgressBar progressBar;
    private RecyclerTouchListener mtouchListener;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private SearchView searchView;
    private Spinner spinner;
    private TextView resultsText;
    private WeakReference<Context> context;

    private static SearchViewModel viewModel;

    /**
     * Activity is opened from search query in CategoriesActivity which will always pass
     * a bundle with SearchExtras.CATEGORY and SearchExtras.QUERY values
     *
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        setupToolbar();

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.init(getResources().getStringArray(R.array.category_ids),
                getResources().getStringArray(R.array.category_titles));

        if(getIntent().hasExtra(SearchExtras.CATEGORY)) {
            viewModel.setCategory(getIntent().getStringExtra(SearchExtras.CATEGORY));
        }

        if(getIntent().hasExtra(SearchExtras.QUERY)) {
            viewModel.setQuery(getIntent().getStringExtra(SearchExtras.QUERY));
        }

        setupLayout();
        observeViewModel();
    }

    /**
     * Instantiate searchView with default value and keep it open
     *
     * @param menu
     * @return option menu created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_query_hint, viewModel.getCategory()));
        searchView.setQuery(viewModel.getQuery(), false);
        searchView.setIconified(false);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {

                // remove spaces and symbols
                final String newQuery = FormatUtils.getTrimmedQuery(query);

                queryDelay.removeCallbacksAndMessages(null);

                resultsText.setText("");

                queryDelay.postDelayed(() -> {

                    if (!viewModel.getQuery().equals(newQuery)) {

                        viewModel.setQuery(FormatUtils.getTrimmedQuery(query));
                        adapter.setQuery(viewModel.getQuery());
                    }
                }, 500);

                return true;
            }
        });

        return true;
    }

    /**
     * Add listeners
     *
     */
    @Override
    public void onStart() {
        super.onStart();

        mtouchListener = new RecyclerTouchListener(this) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {

                startActivity(DetailExtras.getIntent(SearchActivity.this,
                        viewModel.getItem(position)));
            }
        };

        recyclerView.addOnItemTouchListener(mtouchListener);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(!viewModel.getCategory().equals(viewModel.getCategoryByPosition(position))) {

                    viewModel.setCategory(viewModel.getCategoryByPosition(position));

                    searchView.clearFocus();
                    resultsText.setText("");
                    searchView.setQueryHint(getString(R.string.search_query_hint, viewModel.getCategory()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    /**
     *  Remove adapter & listeners
     */
    @Override
    public void onStop() {
        super.onStop();

        adapter = null;
        recyclerView.removeOnItemTouchListener(mtouchListener);
        queryDelay.removeCallbacksAndMessages(null);
        spinner.setOnItemSelectedListener(null);
    }

    /**
     *  Hide toolbar title and use back arrow to go back
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    /**
     * Setup view
     */
    protected void setupLayout() {

        resultsText = findViewById(R.id.results_text);
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.grid);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, viewModel.getCategoryTitles());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(viewModel.getCategoryPosition());
    }

    /**
     * Add viewModel observers
     */
    private void observeViewModel() {

        viewModel.getList().observe(this, list -> {

            if(adapter != null) {
                adapter.notifyDataSetChanged();
            } else {
                adapter = new SearchAdapter(this, list);
                adapter.setQuery(viewModel.getQuery());
                recyclerView.setAdapter(adapter);
            }

            resultsText.setText(getResources().getQuantityString(R.plurals.result_count,
                    list.size(), list.size(), viewModel.getQuery()));
            recyclerView.setVisibility(View.VISIBLE);
        });

        viewModel.getError().observe(this, (Boolean error) -> {

            if(error) {
                resultsText.setText(getString(R.string.error_message));
                Toast.makeText(this, getString(R.string.search_error_message, viewModel.getQuery()), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
            }
        });

        viewModel.getLoading().observe(this, (Boolean isLoading) -> {

            if(isLoading) {
                resultsText.setText(getString(R.string.search_delay_message));
                recyclerView.setVisibility(View.GONE);
            }
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);

        });
    }
}
