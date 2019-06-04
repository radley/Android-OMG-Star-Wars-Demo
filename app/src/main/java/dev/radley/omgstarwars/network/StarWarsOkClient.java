package dev.radley.omgstarwars.network;

import android.os.Build;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.client.OkClient;
import retrofit.client.Request;


public class StarWarsOkClient extends OkClient {

    public StarWarsOkClient() {
        super();
    }

    @Override
    protected HttpURLConnection openConnection(Request request) throws IOException {

        HttpURLConnection connection = super.openConnection(request);
        connection.setRequestProperty("User-Agent", "swapi-android-" + Build.VERSION.RELEASE);

        return connection;
    }
}