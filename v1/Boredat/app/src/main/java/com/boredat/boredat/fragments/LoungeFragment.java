package com.boredat.boredat.fragments;


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
public class LoungeFragment extends Fragment implements LoungeView, AdapterView.OnItemSelectedListener{
    @Bind(R.id.spinner_nav)
    Spinner spinner;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Inject
    UserPreferences mUserPrefs;

    private ArrayAdapter<String> mAdapter;

    public LoungeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    public static LoungeFragment newInstance() {
        return new LoungeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoredatApplication.get(getActivity()).getSessionComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lounge, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String local = getString(R.string.spinner_lounge_local);
        String global = getString(R.string.spinner_lounge_global);

        // TODO: FIX GET LOCAL BOARD NAME FROM USER PREFS
        if (mUserPrefs != null) {
            if (mUserPrefs.hasUserDetails() && mUserPrefs.getNetworkShortname() != null) {
                local = BoredatUtils.createLocalTitle(mUserPrefs.getNetworkShortname());
            }
        }

        String[] options = {
                local,
                global
        };

        // Create an ArrayAdapter using the string array and a default spinner layout
        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, options);

        // Specify the layout to use when the list of choices appears
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(mAdapter);
        // Specify the interface implementation
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // An item was selected. Retrieve the selected item using
        // parent.getItemAtPosition(pos)

        if (position == 1) {
            showGlobalBoard();
        } else {
            showLocalBoard();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

    }

    @Override
    public void showLocalBoard() {
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, FeedFragment.newInstance(Constants.FEED_ID_LOCAL))
                .commit();
    }

    @Override
    public void showGlobalBoard() {
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, FeedFragment.newInstance(Constants.FEED_ID_GLOBAL))
                .commit();
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
