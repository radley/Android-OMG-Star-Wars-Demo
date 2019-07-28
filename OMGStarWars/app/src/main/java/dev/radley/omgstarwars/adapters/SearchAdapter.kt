package dev.radley.omgstarwars.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.models.Starship
import dev.radley.omgstarwars.utilities.FormatUtils
import dev.radley.omgstarwars.viewmodels.SWImage
import java.util.ArrayList

class SearchAdapter(var context: Context, private var modelList: ArrayList<SWModel>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var query: String = ""

    fun setQuery(query: String) {
        this.query = query
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleText: TextView = view.findViewById(R.id.title)
        private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        private val subtitleText: TextView = view.findViewById(R.id.subtitle)

        fun bind(item: SWModel) {

            if (item is Starship && item.model.toLowerCase() != item.title.toLowerCase()) {
                subtitleText.text = FormatUtils.getEmphasizedText(item.model, query)
                subtitleText.visibility = View.VISIBLE
            } else {
                subtitleText.text = ""
                subtitleText.visibility = View.GONE
            }

            titleText.text = FormatUtils.getEmphasizedText(item.title, query)

            // assign default & missing images
            val requestOptions = RequestOptions()
                    .placeholder(SWImage.getPlaceholderImage(item.categoryId))
                    .error(SWImage.getFallbackImage(item.categoryId))

            // add image to thumbnail with fade-in
            Glide.with(thumbnail.context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(item.imagePath))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_result, parent, false)
    )

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(modelList[position])
    }

}

