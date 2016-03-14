package com.boredat.boredatdroid.CreateNewPost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.boredat.boredatdroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateNewPostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateNewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewPostFragment extends Fragment implements CreateNewPostView{
    private OnFragmentInteractionListener mListener;
    private CreateNewPostPresenter mPresenter;

    private ProgressDialog mDialog;
    private EditText mEditText;
    private Switch mPersonalitySwitch;
    private Switch mLocationSwitch;
    private Button mPostButton;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateNewPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateNewPostFragment newInstance() {
        return new CreateNewPostFragment();
    }

    public CreateNewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CreateNewPostPresenterImpl(this.getActivity(),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_post, container, false);

        mEditText = (EditText) view.findViewById(R.id.action_create_new_post_text);
        mPersonalitySwitch = (Switch) view.findViewById(R.id.action_create_new_post_switch_personality);
        mLocationSwitch = (Switch) view.findViewById(R.id.action_create_new_post_switch_location);
        mPostButton = (Button) view.findViewById(R.id.action_create_new_post_button_post);

        // Handle Post button clicks
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mLocation = 0;
                if (mLocationSwitch.isChecked()) {
                    // TODO get the location id
                }
                mPresenter.onPost(mEditText.getText().toString(),!mPersonalitySwitch.isChecked(),mLocation);
            }
        });

        // Set up ProgressDialog
        mDialog = new ProgressDialog(this.getActivity());
        mDialog.setTitle(getString(R.string.app_name));
        mDialog.setMessage(getString(R.string.dialog_loading_posts));
        mDialog.setIndeterminate(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showMessage(String message) {
        //Log.d("", "showMessage => " + message);

        Toast.makeText(getActivity(),
                message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mDialog != null) {
            mDialog.hide();
        }
    }

    @Override
    public void onSentPost() {
        if (mListener != null) {
            mListener.onFragmentInteraction(null);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
        }
    }
}
