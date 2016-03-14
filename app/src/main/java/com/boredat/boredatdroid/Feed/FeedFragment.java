package com.boredat.boredatdroid.Feed;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.boredat.boredatdroid.R;
import com.boredat.boredatdroid.models.Paginator;
import com.boredat.boredatdroid.models.Post;
import com.boredat.boredatdroid.network.UserSessionManager;

import java.util.List;

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment implements FeedView {
    UserSessionManager sessionManager;
    private FeedPresenter mPresenter;

    private TextView mEmptyView;
    private RecyclerView mRecyclerView;
    private FeedAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog mDialog;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment
     * @return A new instance of fragment FeedFragment.
     */
    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    // Required empty public constructor
    public FeedFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new UserSessionManager(this.getActivity());

        mPresenter = new FeedPresenterImpl(this.getActivity(), this);

        mPresenter.onLoadPage(sessionManager.getCurrentPage());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local_board, container, false);

        // Set up RecyclerView
        mEmptyView = (TextView)view.findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.card_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        // TODO: refactor (see MainActivity)
        // Set up ProgressDialog
        mDialog = new ProgressDialog(this.getActivity());
        mDialog.setTitle(getString(R.string.app_name));
        mDialog.setMessage(getString(R.string.dialog_loading_posts));
        mDialog.setIndeterminate(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.show();


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
        }
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
    public void showFeedPage(List<Post> posts, Paginator paginator) {
        Log.d(""," == showFeedPage in FeedFragment == \n\n \tpage: " + paginator.getCurrentPage()+"\n");
        mAdapter = new FeedAdapter();
        mAdapter.setPresenter(mPresenter);
        mAdapter.setHeader(paginator);
        mAdapter.setFooter(paginator);
        mAdapter.setItems(posts);

        switch(paginator.getCurrentPage()) {
            case 1:
                mAdapter.hideHeader();
                break;
            default:
                mAdapter.showHeader();
                break;
        }

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showMessage(String message) {
        Log.d("", "showMessage => " + message);

        Toast.makeText(getActivity(),
                message,
                Toast.LENGTH_LONG).show();
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

}
