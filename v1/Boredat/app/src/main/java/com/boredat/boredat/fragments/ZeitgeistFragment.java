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
public class ZeitgeistFragment extends Fragment {


    public ZeitgeistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ZeitgeistFragment.
     */
    public static ZeitgeistFragment newInstance() {
        return new ZeitgeistFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zeitgeist, container, false);
    }

}
