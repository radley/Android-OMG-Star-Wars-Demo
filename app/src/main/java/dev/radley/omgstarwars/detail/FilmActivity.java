package dev.radley.omgstarwars.detail;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.swapi.models.Film;
import com.swapi.sw.StarWarsApi;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FilmActivity extends DetailActivity{


    protected Film mFilm;

    @Override
    protected void loadResource(Serializable resource) {

        mFilm = (Film)resource;
    }

    @Override
    protected void updateTitle() {
        mActionBar.setTitle(mFilm.title);

    }

}
