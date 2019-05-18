package polak.shay.servicenow.shaypolak.mangers.data_base;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;


import polak.shay.servicenow.shaypolak.R;
import polak.shay.servicenow.shaypolak.model.Joke;
import polak.shay.servicenow.shaypolak.model.User;


public class DataBaseManger {

    private LocaleDataBase mLocaleDataBase;
    private Handler mHandler;

    public DataBaseManger(Context context) {
        init(context);
        HandlerThread thread = new HandlerThread(context.getString(R.string.data_base));
        thread.start();
        mHandler = new Handler(thread.getLooper());
    }

    private void init(Context context) {
        mLocaleDataBase = Room.databaseBuilder(context,
                LocaleDataBase.class, "locale-base")
                .fallbackToDestructiveMigration()
                .build();
    }

    public void setUserData(final User data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    mLocaleDataBase.LocaleDataDao().insertUser(data);
                }
                catch (Exception e)
                {
                mLocaleDataBase.LocaleDataDao().updateUser(data);
                }
            }
        });
    }

    public LiveData<User[]> getUserData() {
        return mLocaleDataBase.LocaleDataDao().getUser();
    }

    public LiveData<Joke[]> getJokes() {
        return mLocaleDataBase.LocaleDataDao().getJokes();
    }

    public void addJoke(final Joke joke) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mLocaleDataBase.LocaleDataDao().addJoke(joke);
            }
        });
    }
}

