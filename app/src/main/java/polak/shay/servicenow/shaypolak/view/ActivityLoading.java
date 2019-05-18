package polak.shay.servicenow.shaypolak.view;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import polak.shay.servicenow.shaypolak.App;
import polak.shay.servicenow.shaypolak.R;
import polak.shay.servicenow.shaypolak.model.User;

public class ActivityLoading extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_loading);
        init();

    }

    private void init() {
        App app = (App) getApplication();
        app.mLocalData.getUserData().observe(this, new Observer<User[]>() {
            @Override
            public void onChanged(@Nullable User[] users) {
                User user = null;
                if (users != null && users.length > 0) {
                    App app = (App) getApplication();
                    user = users[0];
                    app.setUser(user);
                }
                nextScreen(user);
            }
        });
    }

    private void nextScreen(User user) {
        Intent intent;
        if (user != null) {
            if (!user.mAfterApprove) {
                intent = new Intent(this, ActivitySingIn.class);
            } else {
                intent = new Intent(this, ActivityDispalyData.class);
            }
        } else {
            intent = new Intent(this, ActivitySingIn.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
