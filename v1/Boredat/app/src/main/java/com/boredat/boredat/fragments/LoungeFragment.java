package com.boredat.boredat.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.boredat.boredat.BoredatApplication;
import com.boredat.boredat.R;
import com.boredat.boredat.activities.ComposeNewPostActivity;
import com.boredat.boredat.model.api.UserPreferences;
import com.boredat.boredat.presentation.Lounge.LoungeView;
import com.boredat.boredat.util.BoredatUtils;
import com.boredat.boredat.util.Constants;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoungeFragment extends Fragment implements LoungeView {
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private static final String KEY_FEED_ID = "feedId";
    private int mFeedId;

    public LoungeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    public static LoungeFragment newInstance(int feedId) {
        LoungeFragment fragment = new LoungeFragment();

        // supply arguments
        Bundle args = new Bundle();
        args.putInt(KEY_FEED_ID, feedId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFeedId = getArguments().getInt(KEY_FEED_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lounge, container, false);
        ButterKnife.bind(this, rootView);


        switch(mFeedId) {
            case Constants.FEED_ID_GLOBAL:
                BoredatApplication.get(getActivity()).getGlobalBoardComponent().inject(this);
                break;
            default:
                BoredatApplication.get(getActivity()).getLocalBoardComponent().inject(this);
                break;
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToComposeNewPostActivity();
            }
        });

        showBoard();
    }

    private void navigateToComposeNewPostActivity() {
        Intent intent = new Intent(getActivity(), ComposeNewPostActivity.class);
        intent.putExtra(ComposeNewPostActivity.KEY_FEED_ID, mFeedId);
        startActivity(intent);
    }
    @Override
    public void showBoard() {
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, FeedFragment.newInstance(mFeedId))
                .commit();
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
