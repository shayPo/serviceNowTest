package polak.shay.servicenow.shaypolak;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import polak.shay.servicenow.shaypolak.mangers.data_base.DataBaseManger;
import polak.shay.servicenow.shaypolak.mangers.network.NetworkManger;
import polak.shay.servicenow.shaypolak.mangers.network.model.JokeModel;
import polak.shay.servicenow.shaypolak.model.Joke;
import polak.shay.servicenow.shaypolak.model.User;
import retrofit2.Call;


public class App extends Application {
    public DataBaseManger mLocalData;
    public NetworkManger mNetworkManger;

    private User mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mLocalData = new DataBaseManger(this);
        mNetworkManger = new NetworkManger();
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }


    public LiveData<Joke[]> getLocalJokes() {
        return mLocalData.getJokes();
    }

    public Call<JokeModel> getJoke() {
        return mNetworkManger.getJoke(mUser.mFirstName, mUser.mLastName);
    }
}
