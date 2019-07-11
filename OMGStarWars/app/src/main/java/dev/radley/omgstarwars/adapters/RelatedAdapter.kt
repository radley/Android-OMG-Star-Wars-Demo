package dev.radley.omgstarwars.adapters

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
import dev.radley.omgstarwars.viewmodels.SWImage
import java.util.ArrayList

class RelatedAdapter(private var modelList: ArrayList<SWModel>, var clickListener: (SWModel) -> Unit) : RecyclerView.Adapter<RelatedAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleText: TextView = view.findViewById(R.id.title)
        private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)

        fun bind(item: SWModel) {

            titleText.text = item.title

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

            thumbnail.setOnClickListener { clickListener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardLayout: Int = if (modelList[0].categoryId == "starships" || modelList[0].categoryId == "vehicles") {
            R.layout.card_row_wide
        } else
            R.layout.card_row

        // use wider layout for Starships and Vehicles

        val v = LayoutInflater.from(parent.context).inflate(cardLayout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(modelList[position])
    }
}