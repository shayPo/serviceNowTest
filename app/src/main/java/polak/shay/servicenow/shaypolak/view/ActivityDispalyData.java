package polak.shay.servicenow.shaypolak.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import polak.shay.servicenow.shaypolak.App;
import polak.shay.servicenow.shaypolak.R;
import polak.shay.servicenow.shaypolak.model.ErrorModel;
import polak.shay.servicenow.shaypolak.model.Joke;
import polak.shay.servicenow.shaypolak.view.adapters.JokeAdapter;

public class ActivityDispalyData extends AppCompatActivity implements JokeAdapter.OnLastItemVisable {

    private JokeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_display_data);
        init();
    }

    private void init() {
        RecyclerView jokes = findViewById(R.id.joke_display);
        mAdapter = new JokeAdapter(this);
        jokes.setAdapter(mAdapter);
        jokes.setLayoutManager(new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        App app = (App) getApplication();
        app.getLocalJokes().observe(this, new Observer<Joke[]>() {
            @Override
            public void onChanged(@Nullable Joke[] jokes) {
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

    @Override
    public void LastItemVisible() {
        addNewJoke();
    }

    private void addNewJoke() {
        App app = (App) getApplication();
        app.getJoke().enqueue(new ErrorModel((App) getApplication(), mAdapter));
  }

}
