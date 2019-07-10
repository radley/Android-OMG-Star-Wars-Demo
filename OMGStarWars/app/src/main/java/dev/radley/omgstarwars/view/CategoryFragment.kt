package dev.radley.omgstarwars.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.adapters.CategoryAdapter
import dev.radley.omgstarwars.bundle.DetailExtras
import dev.radley.omgstarwars.bundle.SearchExtras
import dev.radley.omgstarwars.listeners.RecyclerTouchListener
import dev.radley.omgstarwars.models.CategoryOld
import dev.radley.omgstarwars.viewmodels.CategoryViewModel
import timber.log.Timber
import java.util.*

class CategoryFragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    private var adapter: CategoryAdapter? = null
    private lateinit var viewModel: CategoryViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var launchProgressBar: ProgressBar
    private lateinit var touchListener: RecyclerTouchListener


    companion object {

        fun newInstance(category: String): CategoryFragment {

            val myFragment = CategoryFragment()

            val args = Bundle()
            args.putString(SearchExtras.CATEGORY, category)
            myFragment.arguments = args
            return myFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_category, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        launchProgressBar = view.findViewById(R.id.launch_progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view)

        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        val category = arguments!!.getString(SearchExtras.CATEGORY)
        category?.let {
            recyclerView.layoutManager = GridLayoutManager(context, getSpanCount(category))
            observeViewModel(category)
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        touchListener = object : RecyclerTouchListener(context) {

            override fun onItemSelected(holder: RecyclerView.ViewHolder, position: Int) {

                startActivity(DetailExtras.getIntent(Objects.requireNonNull(activity),
                        viewModel.getItem(position)))
            }
        }

        recyclerView.addOnItemTouchListener(touchListener)
    }

    override fun onStop() {
        super.onStop()

        adapter = null
        recyclerView.removeOnItemTouchListener(touchListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.dispose()
    }


    private fun observeViewModel(category: String) {
        viewModel.getList(category).observe(this, Observer { swModel ->

            adapter?.notifyDataSetChanged() ?: run {
                adapter = CategoryAdapter(swModel)
                recyclerView.adapter = adapter

                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                        super.onScrolled(recyclerView, dx, dy)
                        if( !recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN))
                            viewModel.getNextPage()
                    }
                })
            }

            recyclerView.visibility = View.VISIBLE
            launchProgressBar.visibility = View.GONE
        })

        viewModel.loadError.observe(this, Observer { error ->

            if(error) {
                Toast.makeText(activity, getString(R.string.error_message, viewModel.id), Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                launchProgressBar.visibility = View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->

            if (launchProgressBar.visibility == View.GONE) {
                progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
            }
        })
    }


    private fun getSpanCount(id: String): Int {

        return if (id == CategoryOld.STARSHIPS || id == CategoryOld.VEHICLES) {
            resources.getInteger(R.integer.grid_span_count_wide)
        } else {
            resources.getInteger(R.integer.grid_span_count_tall)
        }
    }
}