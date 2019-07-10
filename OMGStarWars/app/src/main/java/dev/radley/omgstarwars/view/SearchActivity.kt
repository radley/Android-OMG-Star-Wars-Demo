package dev.radley.omgstarwars.view

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.adapters.SearchAdapter
import dev.radley.omgstarwars.bundle.DetailExtras
import dev.radley.omgstarwars.bundle.SearchExtras
import dev.radley.omgstarwars.listeners.RecyclerTouchListener
import dev.radley.omgstarwars.utilities.FormatUtils
import dev.radley.omgstarwars.viewmodels.SearchViewModel

class SearchActivity : AppCompatActivity() {


    private lateinit var progressBar: ProgressBar
    private lateinit var mtouchListener: RecyclerTouchListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var spinner: Spinner
    private lateinit var resultsText: TextView
    private lateinit var viewModel: SearchViewModel

    private val queryDelay = Handler()
    private var adapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        setupToolbar()

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        if (intent.hasExtra(SearchExtras.CATEGORY)) {
            viewModel.category = intent.getStringExtra(SearchExtras.CATEGORY)
        }

        if (intent.hasExtra(SearchExtras.QUERY)) {
            viewModel.query = intent.getStringExtra(SearchExtras.QUERY)
        }

        setupLayout()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)

        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = getString(R.string.search_query_hint, viewModel.category)
        searchView.setQuery(viewModel.query, false)
        searchView.isIconified = false
        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {

                // remove spaces and symbols
                val newQuery = FormatUtils.getTrimmedQuery(query)

                queryDelay.removeCallbacksAndMessages(null)

                resultsText.text = ""

                queryDelay.postDelayed({

                    if (viewModel.query != newQuery) {

                        viewModel.query = FormatUtils.getTrimmedQuery(query)
                        adapter?.setQuery(viewModel.query)
                    }
                }, 500)

                return true
            }
        })

        return true
    }

    override fun onStart() {
        super.onStart()

        mtouchListener = object : RecyclerTouchListener(this) {

            override fun onItemSelected(holder: RecyclerView.ViewHolder, position: Int) {

                startActivity(DetailExtras.getIntent(holder.itemView.context, viewModel.getItem(position)))
            }
        }

        recyclerView.addOnItemTouchListener(mtouchListener)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {

                if (viewModel.category != viewModel.getCategoryByPosition(position)) {

                    viewModel.category = viewModel.getCategoryByPosition(position)

                    searchView.clearFocus()
                    resultsText.text = ""
                    searchView.queryHint = getString(R.string.search_query_hint, viewModel.category)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }
    }

    override fun onStop() {
        super.onStop()

        adapter = null
        recyclerView.removeOnItemTouchListener(mtouchListener)
        queryDelay.removeCallbacksAndMessages(null)
        spinner.onItemSelectedListener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupLayout() {

        resultsText = findViewById(R.id.results_text)
        progressBar = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.grid)

        spinner = findViewById(R.id.spinner)
        val spinnerAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, viewModel.categoryTitles)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.setSelection(viewModel.categoryPosition)
    }

    private fun observeViewModel() {

        viewModel.list.observe(this, Observer { list ->

            adapter?.notifyDataSetChanged() ?: run {
                adapter = SearchAdapter(this, list)
                adapter?.setQuery(viewModel.query)
                recyclerView.adapter = adapter
            }

            resultsText.text = resources.getQuantityString(R.plurals.result_count,
                    list.size, list.size, viewModel.query)
            recyclerView.visibility = View.VISIBLE
        })

        viewModel.error.observe(this, Observer { error ->
            if (error!!) {
                resultsText.text = getString(R.string.error_message)
                Toast.makeText(this, getString(R.string.search_error_message, viewModel.query), Toast.LENGTH_SHORT).show()
                recyclerView.visibility = View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading!!) {
                resultsText.text = getString(R.string.search_delay_message)
                recyclerView.visibility = View.GONE
            }
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }
}
