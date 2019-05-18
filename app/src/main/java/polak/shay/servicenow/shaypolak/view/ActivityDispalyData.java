package polak.shay.servicenow.shaypolak.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import polak.shay.servicenow.shaypolak.App;
import polak.shay.servicenow.shaypolak.R;
import polak.shay.servicenow.shaypolak.model.ErrorModel;
import polak.shay.servicenow.shaypolak.model.Joke;
import polak.shay.servicenow.shaypolak.view.adapters.JokeAdapter;

public class ActivityDispalyData extends AppCompatActivity implements ErrorModel.CallListener {

    private JokeAdapter mAdapter;
    private View mLoading;

    private boolean mNoWeb = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_display_data);
        init();
    }

    private void init() {
        mLoading = findViewById(R.id.loading);
        RecyclerView jokes = findViewById(R.id.joke_display);
        mAdapter = new JokeAdapter();
        jokes.setAdapter(mAdapter);
        jokes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 1 == totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if(mLoading.getVisibility() != View.VISIBLE) {
                        addNewJoke();
                    }
                }
            }
        });
        jokes.setLayoutManager(new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        App app = (App) getApplication();
        app.getLocalJokes().observe(this, new Observer<Joke[]>() {
            @Override
            public void onChanged(@Nullable Joke[] jokes) {
                mLoading.setVisibility(View.GONE);
                if (jokes != null) {
                    if (jokes.length == 0) {
                        addNewJoke();
                    } else {
                        mAdapter.addAllJoke(jokes);
                    }
                }
            }
        });
    }

    private void addNewJoke() {
        App app = (App) getApplication();
        if(isNetworkAvailable())
        {
            mNoWeb = false;
            mLoading.setVisibility(View.VISIBLE);
            app.getJoke().enqueue(new ErrorModel((App) getApplication(), mAdapter, this));
        }
        else
            {
                if(!mNoWeb)
                {
                    Toast.makeText(this, getText(R.string.no_web), Toast.LENGTH_LONG).show();
                }
                mNoWeb = true;
            }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onCallEnd() {
        mLoading.setVisibility(View.GONE);
    }
}
