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
    private WeakReference<CallListener> mListener;
    private int mNumberOfTimeout = 0;

    public ErrorModel(App app, JokeAdapter adapter, CallListener listener)
    {
        mApp = new WeakReference<>(app);
        mAdapter = new WeakReference<>(adapter);
        mListener = new WeakReference<>(listener);
    }

    @Override
    public void onResponse(Call<JokeModel> call, Response<JokeModel> response) {
        if (response.isSuccessful()) {
            Joke joke = response.body().mData;
            if(joke != null && mAdapter != null) {
                mAdapter.get().addJoke(joke);
            }
        }
        mListener.get().onCallEnd();
    }

    @Override
    public void onFailure(Call<JokeModel> call, Throwable t) {
        mNumberOfTimeout++;
        if(mNumberOfTimeout < 3) {
            call.enqueue(this);
        }
        mListener.get().onCallEnd();
    }

    public interface CallListener
    {
        void onCallEnd();
    }
}
