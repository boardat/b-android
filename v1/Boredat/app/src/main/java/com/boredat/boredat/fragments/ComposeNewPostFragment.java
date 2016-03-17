package com.boredat.boredat.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

import com.boredat.boredat.R;
import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.presentation.Compose.IComposePresenter;
import com.devspark.robototextview.widget.RobotoCheckBox;
import com.devspark.robototextview.widget.RobotoEditText;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComposeNewPostFragment extends Fragment implements OnItemSelectedListener {
    // Constants
    public static final String KEY_REPLY_TO_POST_ID = "replyToPostId";
    public static final long NEW_POST = -420;

    // Member Variables
    private boolean isReply = true;
    private long mReplyToPostId;
    private IComposePresenter mPresenter;
    private ArrayAdapter<String> mArrayAdapter;


    @Inject
    BoredatService mService;

    @Bind(R.id.compose_post_personality_spinner)
    Spinner mPersonalitySpinner;
    @Bind(R.id.compose_post_anonymous_only_label)
    RobotoTextView mAnonymousOnlyLabel;
    @Bind(R.id.compose_post_location_checkbox)
    RobotoCheckBox mLocationCheckbox;
    @Bind(R.id.compose_post_text_input_layout)
    TextInputLayout mComposeTextInputLayout;
    @Bind(R.id.compose_post_edit_text)
    RobotoEditText mComposeEditText;

    public ComposeNewPostFragment() {
        // Required empty public constructor
    }

    public static ComposeNewPostFragment newInstance(long postId) {
        ComposeNewPostFragment fragment = new ComposeNewPostFragment();

        // supply arguments
        Bundle args = new Bundle();
        args.putLong(KEY_REPLY_TO_POST_ID, postId);
        fragment.setArguments(args);

        return fragment;

    }

    // Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mReplyToPostId = getArguments().getLong(KEY_REPLY_TO_POST_ID);
        }

        if (mReplyToPostId == NEW_POST) isReply = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_compose_post, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO implement user prefs
        boolean hasPersonality = true;
        String personalityName = "malloc";
        String anonName = "anonymous";
        boolean showPersonalityDefault = true;
        boolean showLocationDefault = true;

        if (hasPersonality) {
            // Set click listener
            mPersonalitySpinner.setOnItemSelectedListener(this);

            // Set drop down elements
            List<String> options = new ArrayList<String>();

            if (showPersonalityDefault) {
                options.add(personalityName);
                options.add(anonName);
            } else {
                options.add(anonName);
                options.add(personalityName);
            }

            // Create adapter
            mArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, options);

            // Drop down layout style -- list view with radio button
            mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mPersonalitySpinner.setAdapter(mArrayAdapter);

        } else {
            mPersonalitySpinner.setVisibility(View.GONE);
            mAnonymousOnlyLabel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
