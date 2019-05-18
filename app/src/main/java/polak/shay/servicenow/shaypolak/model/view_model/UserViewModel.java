package polak.shay.servicenow.shaypolak.model.view_model;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;


import polak.shay.servicenow.shaypolak.App;
import polak.shay.servicenow.shaypolak.model.User;

public class UserViewModel extends ViewModel {
    public User mUser;

    public void saveData(Context context) {
        App app = (App) context.getApplicationContext();
        app.setUser(mUser);
        app.mLocalData.setUserData(mUser);
    }
}
