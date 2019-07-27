package dev.radley.omgstarwars.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.viewmodels.SWImage
import java.util.ArrayList


class CategoryAdapter(private var items: ArrayList<SWModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val viewTypeItem = 1
    val viewTypeProgressBar = 0
    private var isFooterEnabled = false

    init {

        // prevent blinking items when notifyDataSetChanged is called
        // (common recyclerview issue)
        setHasStableIds(true);
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleText: TextView = view.findViewById(R.id.title)
        private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)

        fun bind(item: SWModel) {

            titleText.text = item.title

            // assign default & missing images
            val requestOptions = RequestOptions()
                    .placeholder(SWImage.getPlaceholderImage(item.categoryId))
                    .error(SWImage.getFallbackImage(item.categoryId))

            // add image to thumbnail with fade-in
            Glide.with(thumbnail)
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(item.imagePath))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.DATA) // resources are assets
                    .into(thumbnail)
        }
    }

    class ProgressViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var progressBar: ProgressBar

        init {
            progressBar = v.findViewById<View>(R.id.progress) as ProgressBar
        }
    }

    // required for adapter.setHasStableIds(true)
    // to prevent blinking images when notifyDataSetChanged is called
    override fun getItemId(position: Int): Long {

        // extra progress item
        if(position == items.size) return -1

        return items[position].id.toLong()
    }

    fun showFooterProgressBar(hasNextPage: Boolean) {
        isFooterEnabled = hasNextPage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val vh: RecyclerView.ViewHolder
        val view: View

        if(viewType== viewTypeItem) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.card_category, parent, false)
            vh = CardViewHolder(view)
        }else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            vh = ProgressViewHolder(view);
        }
        return vh
    }

    override fun getItemCount(): Int {
        return if (isFooterEnabled) items.size + 1
        else items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isFooterEnabled && position >= items.size) viewTypeProgressBar else viewTypeItem
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ProgressViewHolder) {
            holder.progressBar.isIndeterminate = true
        } else if (items.size > 0 && position < items.size) {
            (holder as CardViewHolder).bind(items[position])
        }
    }
}