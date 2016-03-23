package com.boredat.boredat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.boredat.boredat.BoredatApplication;
import com.boredat.boredat.adapters.ComposeSpinnerAdapter;
import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.UserPreferences;
import com.boredat.boredat.presentation.Compose.ComposePresenter;
import com.boredat.boredat.presentation.Compose.ComposeView;
import com.boredat.boredat.presentation.Compose.IComposePresenter;
import com.boredat.boredat.util.Constants;
import com.boredat.boredat.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComposeNewPostActivity extends AppCompatActivity implements ComposeView, AdapterView.OnItemSelectedListener{
    // Constants
    public static final String KEY_FEED_ID = "feedId";

    @Bind(R.id.compose_new_post_et)
    EditText mComposeEditText;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_spinner)
    AppCompatSpinner mSpinner;

    @Bind(R.id.toolbar_post_button)
    Button mPostButton;

    @Bind(R.id.progressBarHolder)
    FrameLayout progressBarHolder;

    private boolean mIsPostAnonymous;
    private ComposeSpinnerAdapter mAdapter;
    private IComposePresenter mPresenter;
    private int mFeedId;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;


    @Inject
    BoredatService mService;

    @Inject
    UserPreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ButterKnife.bind(this);

        mFeedId = getIntent().getIntExtra(KEY_FEED_ID, Constants.FEED_ID_LOCAL);

        // Handle Post button clicks
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onPost(getEnteredText(), isPostAnonymous());
            }
        });

        setupToolbar();
        getPresenter().loadViewPrefs();
    }

    @Override
    public void showProgress() {
        mPostButton.setEnabled(false);
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
        mPostButton.setEnabled(true);
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(ComposeNewPostActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPreferredView(String personalityName, boolean preferAnon) {
        mIsPostAnonymous = preferAnon;

        ArrayList<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.anonymous));
        list.add(personalityName);

        setupSpinner(list);

        if (preferAnon) {
            mSpinner.setSelection(0);
        } else {
            mSpinner.setSelection(1);
        }
    }

    @Override
    public void showPreferredView() {
        mSpinner.setEnabled(false);
        mIsPostAnonymous = true;

        ArrayList<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.anonymous));
        setupSpinner(list);
    }

    private void setupToolbar() {

    }

    private void setupSpinner(List<String> list) {
//        mAdapter = new ArrayAdapter<String>(this, R.layout.personality_line_item_inverted, list);
//        mAdapter.setDropDownViewResource(R.layout.personality_line_item);
        mAdapter = new ComposeSpinnerAdapter(this, R.layout.personality_line_item_inverted, list);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public String getEnteredText() {
        return mComposeEditText.getText().toString();
    }

    @Override
    public boolean isPostAnonymous() {
        return mIsPostAnonymous;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        String anon = getResources().getString(R.string.anonymous);

        if (item.equals(anon)) {
            mIsPostAnonymous = true;
            getPresenter().onAnonSelected();
        } else {
            mIsPostAnonymous = false;
            getPresenter().onPersonalitySelected();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private IComposePresenter getPresenter() {
        if (mService == null || mPrefs == null) {
            switch(mFeedId) {
                case Constants.FEED_ID_GLOBAL:
                    BoredatApplication.get(this).getGlobalBoardComponent().inject(this);
                    break;
                default:
                    BoredatApplication.get(this).getLocalBoardComponent().inject(this);
                    break;
            }
        }

        if (mPresenter == null) {
            mPresenter = new ComposePresenter(this, mService, mPrefs);
        }

        return  mPresenter;
    }
}
