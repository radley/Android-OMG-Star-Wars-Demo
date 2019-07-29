package dev.radley.omgstarwars.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.adapters.CategoryAdapter
import dev.radley.omgstarwars.bundle.DetailExtras
import dev.radley.omgstarwars.bundle.SearchExtras
import dev.radley.omgstarwars.listeners.RecyclerTouchListener
import dev.radley.omgstarwars.models.Category
import dev.radley.omgstarwars.viewmodels.CategoryViewModel
import timber.log.Timber


class CategoryFragment : Fragment() {

    var isLoading = false
    lateinit var recyclerView: RecyclerView

    private var adapter: CategoryAdapter? = null
    private lateinit var viewModel: CategoryViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var touchListener: RecyclerTouchListener
    private lateinit var layoutManager: GridLayoutManager


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
        recyclerView = view.findViewById(R.id.recycler_view)

        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        val category = arguments!!.getString(SearchExtras.CATEGORY)
        category?.let {

            layoutManager = GridLayoutManager(context, getSpanCount(category))

            // use alternate span size for progress bar at the end
            layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {

                    adapter?.let {
                        when (it.getItemViewType(position)) {
                            it.viewTypeItem -> return 1
                            it.viewTypeProgressBar -> return getProgressWidth() //number of columns of the grid
                            else -> return -1
                        }
                    }
                    // default
                    return 1
                }
            })

            recyclerView.layoutManager = layoutManager

            observeViewModel(category)
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        touchListener = object : RecyclerTouchListener(context) {

            override fun onItemSelected(holder: RecyclerView.ViewHolder?, position: Int) {

                startActivity(context?.let {
                    DetailExtras.getIntent(it, viewModel.getItem(position))
                })
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

        viewModel.getLoadError().observe(this, Observer { error ->

            if (error) {
                Toast.makeText(activity, getString(R.string.error_message, viewModel.getId()), Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        })

        viewModel.getLoading().observe(this, Observer { loading ->

            isLoading = loading

            if (progressBar.visibility == View.VISIBLE) {
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        })


        viewModel.getList(category).observe(this, Observer { swModel ->

            adapter?.showFooterProgressBar(viewModel.hasNextPage())

            // save recyclerview position
            val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()

            adapter?.notifyDataSetChanged() ?: run {

                // initialize adapter if it doesn't exist
                adapter = CategoryAdapter(swModel)
                adapter?.showFooterProgressBar(viewModel.hasNextPage())

                // option to show rise animation every time we load
//                val controller = AnimationUtils.loadLayoutAnimation(activity, R.anim.category_layout_rise_animation)
//                recyclerView.layoutAnimation = controller


                recyclerView.adapter = adapter

                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                        recyclerView.layoutManager?.let {

                            val linearLayoutManager = it as LinearLayoutManager
                            val totalItemCount = linearLayoutManager.itemCount
                            val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                            if (!isLoading && totalItemCount <= (lastVisibleItem + 1)) {
                                viewModel.loadNextPage()
                            }
                        }

                        super.onScrolled(recyclerView, dx, dy)
                    }
                })
            }

            // restore recyclerview position (notifyDataSetChanged may causes it to change)
            recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)

            recyclerView.visibility = View.VISIBLE
        })
    }

    private fun getProgressWidth(): Int {

        Timber.d("count = %s", viewModel.getCount())
        Timber.d("SpanCount = %s", getSpanCount(viewModel.getId()))

        if ((viewModel.getCount() % getSpanCount(viewModel.getId())) > 0) {
            return 1
        }

        return getSpanCount(viewModel.getId())
    }


    private fun getSpanCount(id: String): Int {

        return if (id == Category.STARSHIPS || id == Category.VEHICLES) {
            resources.getInteger(R.integer.grid_span_count_wide)
        } else {
            resources.getInteger(R.integer.grid_span_count_tall)
        }
    }
}