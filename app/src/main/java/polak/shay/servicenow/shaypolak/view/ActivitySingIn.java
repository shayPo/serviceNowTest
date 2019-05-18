package polak.shay.servicenow.shaypolak.view;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import polak.shay.servicenow.shaypolak.App;
import polak.shay.servicenow.shaypolak.R;
import polak.shay.servicenow.shaypolak.model.User;
import polak.shay.servicenow.shaypolak.model.view_model.UserViewModel;
import polak.shay.servicenow.shaypolak.view.customLayouts.InputView;

public class ActivitySingIn extends AppCompatActivity implements View.OnClickListener, InputView.OnTextChangeListener {

    private Button mSubmit = null;
    private InputView mFirstName;
    private InputView mLastName;

    private int mContainingAllData = 0;
    private UserViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_sing_in);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mModel.mUser.mFirstName = mFirstName.getText();
        mModel.mUser.mLastName = mLastName.getText();
        mModel.saveData(this);
    }


    private void init() {
        mFirstName = findViewById(R.id.first_name);
        mLastName = findViewById(R.id.last_name);
        mSubmit = findViewById(R.id.submit);

        mFirstName.setOnDataStateChangeListener(this);
        mLastName.setOnDataStateChangeListener(this);

        mModel = ViewModelProviders.of(this).get(UserViewModel.class);
        App app = (App) getApplication();
        mModel.mUser = app.getUser();
        if (mModel.mUser == null) {
            mModel.mUser = new User();
        }
        mSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ActivityDispalyData.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void OnTextChange(boolean containingData, int type) {
        mContainingAllData ^= type;
        if (!containingData) {
            mSubmit.setEnabled(false);
        } else {
            if (mContainingAllData == InputView.ALL_DATA) {
                mSubmit.setEnabled(true);
            }
        }
    }
}
