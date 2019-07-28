package dev.radley.omgstarwars.view.detail.hero


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.viewmodels.SWImage
import kotlinx.android.synthetic.main.view_detail_hero_wide.view.*

/**
 * Layout for Film details text
 */
class WideHeroLayout : HeroLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, model: SWModel) : super(context) {

        this.model = model

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_detail_hero_wide, this, true)

        populateContent()
        updateHeroImage(heroImage, model.imagePath, SWImage.getFallbackImage(model.categoryId))
    }

    /**
     * Add content to layout
     */
    private fun populateContent() {

        title = heroTitle
        subtitle = heroSubtitle


        heroTitle.text = model.title
        heroTitle.visibility = View.VISIBLE

        // some vehicles have the same name & model, so don't show twice
        if(model.title.toLowerCase() != model.subtitle.toLowerCase()) {
            heroSubtitle.text = model.subtitle
            heroSubtitle.visibility = View.VISIBLE
        }

        updateHeroImage(heroImage, model.imagePath, SWImage.getFallbackImage(model.categoryId))
    }
}
