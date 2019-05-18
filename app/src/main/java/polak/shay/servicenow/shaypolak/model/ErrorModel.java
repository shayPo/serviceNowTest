package polak.shay.servicenow.shaypolak.model;

import java.lang.ref.WeakReference;

import polak.shay.servicenow.shaypolak.App;
import polak.shay.servicenow.shaypolak.mangers.network.model.JokeModel;
import polak.shay.servicenow.shaypolak.view.adapters.JokeAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ErrorModel implements Callback<JokeModel> {
    private WeakReference<App> mApp;
    private WeakReference<JokeAdapter> mAdapter;
    private int mNumberOfTimeout = 0;

    public ErrorModel(App app, JokeAdapter adapter)
    {
        mApp = new WeakReference<>(app);
        mAdapter = new WeakReference<>(adapter);
    }

    public void setNumberOfTimeout(int numberOfTimeout) {
        this.mNumberOfTimeout = numberOfTimeout;
    }

    @Override
    public void onResponse(Call<JokeModel> call, Response<JokeModel> response) {
        if (response.isSuccessful()) {
            Joke joke = response.body().mData;
            if(joke != null && mAdapter != null) {
                mAdapter.get().addJoke(joke);
            }
        }
    }

    @Override
    public void onFailure(Call<JokeModel> call, Throwable t) {
        mNumberOfTimeout++;
        if(mNumberOfTimeout >= 2) {
            ErrorModel error = new ErrorModel(mApp.get(), mAdapter.get());
            error.setNumberOfTimeout(mNumberOfTimeout);
            call.enqueue(error);
        }
    }
}
