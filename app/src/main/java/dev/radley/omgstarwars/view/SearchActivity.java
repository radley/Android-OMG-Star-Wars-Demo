package dev.radley.omgstarwars.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.listeners.RecyclerTouchListener;
import dev.radley.omgstarwars.utilities.Util;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.old.category.fragments.BaseCategoryFragment;
import dev.radley.omgstarwars.viewmodels.SearchViewModel;
import timber.log.Timber;

public class SearchActivity extends AppCompatActivity implements BaseCategoryFragment.OnHeadlineSelectedListener {

    protected Activity mActivity;
    

    protected SearchView searchView;
    protected Spinner spinner;
    protected TextView resultsText;
    protected Handler delayHandler = new Handler();

    private RecyclerView recyclerView;
    private RecyclerTouchListener mtouchListener;

    protected static SearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        setupToolbar();

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.init(getResources().getStringArray(R.array.category_ids),
                getResources().getStringArray(R.array.category_titles));
        
        if(intent.hasExtra(SearchExtras.QUERY)) {
            viewModel.setQuery(intent.getStringExtra(SearchExtras.QUERY));
        }

        if(intent.hasExtra(SearchExtras.CATEGORY)) {
            viewModel.setCategory(intent.getStringExtra(SearchExtras.CATEGORY));
        }

        initLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_query_hint, viewModel.getCategory()));
        searchView.setQuery(viewModel.getQuery(), false);
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {

                // remove spaces and symbols
                final String newQuery = Util.getTrimmedQuery(query);

                delayHandler.removeCallbacksAndMessages(null);

                resultsText.setText("");

                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(!viewModel.getQuery().equals(newQuery)) {

                            viewModel.setQuery(Util.getTrimmedQuery(query));
                            resultsText.setText(getString(R.string.search_delay_message));

                            //mCurrentFragment.getResultsFor(query);
                        }

                    }
                }, 500);

                return true;
            }
        });

        searchView.clearFocus();

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        mtouchListener = new RecyclerTouchListener(this) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {
                showDetailView(position);
            }
        };

        recyclerView.addOnItemTouchListener(mtouchListener);
    }

    private void showDetailView(int position) {
        startActivity(DetailExtras.getIntent(Objects.requireNonNull(this),
                viewModel.getItem(position)));
    }

    @Override
    public void onStop() {
        super.onStop();

        //mAdapter = null;
        recyclerView.removeOnItemTouchListener(mtouchListener);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onResultUpdate(int count) {

        if(viewModel.getQuery().length() < 2) {
            resultsText.setText("");
        } else {
            resultsText.setText(getResultsDescription(count));
        }
    }

    public String getResultsDescription(int count) {
        if (count >= 0)
            return getResources().getQuantityString(R.plurals.result_count, count, count, viewModel.getQuery());

        return getResources().getString(R.string.search_error);
    }

    protected void initLayout() {

        resultsText = findViewById(R.id.results_text);

        recyclerView = findViewById(R.id.grid);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, viewModel.getCategoryTitles());

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setSelection(viewModel.getCurrentCategoryPosition());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(!viewModel.getCategory().equals(viewModel.getCategory(position))) {

                    viewModel.setCategory(viewModel.getCategory(position));

                    searchView.clearFocus();
                    resultsText.setText("");

                    updateCategory();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        updateCategory();
    }


    protected void updateCategory() {

    }

    protected void hideKeyboard() {
        if (isKeybordShowing(this, this.getCurrentFocus())) {
            onBackPressed();
        }
    }

    public boolean isKeybordShowing(Context context, View view) {
        try {
            InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return keyboard.isActive();
        } catch (Exception ex) {
            Timber.e("cannot hide keyboard: " + ex);
            return false;
        }
    }

}
