package com.example.niraj.mapplication;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.niraj.mapplication.Trailer.Result;
import com.example.niraj.mapplication.Trailer.TrailerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    List<ResultActivity> resultList=new ArrayList<>();
    List<Result> resultList1=new ArrayList<>();

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "534ba520f1a0491c82219461bdd10cc9";
    SwipeRefreshLayout swipeRefreshLayout;
    MovieAdapterActivity adapter;
    ListView gridView;
    RelativeLayout ll_no_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle("Popular Movies");

            adapter = new MovieAdapterActivity(this, resultList);
            final TrailerAdapter adapter1 = new TrailerAdapter(this, resultList1);

            gridView = (ListView) findViewById(R.id.gridView);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
            ll_no_data=(RelativeLayout) findViewById(R.id.ll_no_data);

            if (gridView != null) {
                gridView.setAdapter(adapter);
            }

            if (API_KEY.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org",
                        Toast.LENGTH_LONG).show();
                return;
            }

            swipeRefreshLayout.setOnRefreshListener(this);

            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(true);
                                            fetchMovies();
                                            gridView.setVisibility(View.VISIBLE);
                                        }
                                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        fetchMovies();
        gridView.setVisibility(View.GONE);
    }

    void fetchMovies(){
        Utilities.showProgressDialog(this);
        if (Utilities.checkNetworkConnection(this)) {
            ApiInterfaceActivity apiService =
                    ApiClientActivity.getClient().create(ApiInterfaceActivity.class);
            Call<Ret> call = apiService.getPopularMovies(API_KEY);
            call.enqueue(new Callback<Ret>() {
                @Override
                public void onResponse(Call<Ret> call, Response<Ret> response) {
                    Ret ret = response.body();
                    //Log.e(TAG, "response   " + new Gson().toJson(response));
                    List<ResultActivity> results = ret.getResults();
                    Log.d(TAG, "Number of movies received: " + results.size());
                    resultList.clear();
                    resultList.addAll(results);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    gridView.setVisibility(View.VISIBLE);
                    Utilities.dismissProgressDialog();
                    ll_no_data.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Ret> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        } else {
            Common.showDialog(MainActivity.this, "No Internet Connection");
            swipeRefreshLayout.setRefreshing(false);
            gridView.setVisibility(View.GONE);
            Utilities.dismissProgressDialog();
           // ll_no_data.setVisibility(View.VISIBLE);

        }
    }
}

