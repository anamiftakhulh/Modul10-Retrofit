package com.retrofit2.retrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.retrofit2.retrofit2.Adapters.MoviesAdapter;
import com.retrofit2.retrofit2.Models.Movie;
import com.retrofit2.retrofit2.Models.MovieResponse;
import com.retrofit2.retrofit2.Rest.ApiClient;
import com.retrofit2.retrofit2.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "ae7cfd51e03e186c17de1dcf3b24b46d";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }
        final RecyclerView recyclerView = (RecyclerView)
                findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>()
        {

            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response)
            {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }
            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t)
            {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
