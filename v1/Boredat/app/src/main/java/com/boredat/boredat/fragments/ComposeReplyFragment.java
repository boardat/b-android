package com.boredat.boredat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boredat.boredat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComposeReplyFragment extends Fragment {


    public ComposeReplyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose_reply, container, false);
    }

}
