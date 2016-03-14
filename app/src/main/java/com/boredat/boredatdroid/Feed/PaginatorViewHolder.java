package com.boredat.boredatdroid.Feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.boredat.boredatdroid.R;
import com.boredat.boredatdroid.models.Paginator;

/**
 * Created by Liz on 8/3/2015.
 */
public class PaginatorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private final Button mFirstButton;
    private final Button mPrevButton;
    private final Button mNextButton;

    private final TextView mPrevPageNum;
    private final TextView mCurrentPageNum;
    private final TextView mNextPageNum;

    private PaginatorViewHolderClicks mListener;
    private final Context mCtx;

    public static final float HIGHLIGHTED_PAGE_FONT_SIZE = (float) 20.0;
    public static final float DEFAULT_PAGE_FONT_SIZE = (float) 14.0;

    public PaginatorViewHolder(View itemView, PaginatorViewHolderClicks listener) {
        super(itemView);
        this.mListener = listener;
        this.mCtx = itemView.getContext();

        this.mFirstButton = (Button) itemView.findViewById(R.id.paginator_action_first);
        this.mPrevButton = (Button) itemView.findViewById(R.id.paginator_action_prev);
        this.mNextButton = (Button) itemView.findViewById(R.id.paginator_action_next);

        this.mPrevPageNum = (TextView) itemView.findViewById(R.id.paginator_text_prev_page_num);
        this.mCurrentPageNum = (TextView) itemView.findViewById(R.id.paginator_text_current_page_num);
        this.mNextPageNum = (TextView) itemView.findViewById(R.id.paginator_text_next_page_num);

        /*onClickListeners*/
        this.mFirstButton.setOnClickListener(this);
        this.mPrevButton.setOnClickListener(this);
        this.mNextButton.setOnClickListener(this);
    }

    public void bindPaginator(Paginator p) {
        Log.d("", "bindPaginator currentPage=" + p.getCurrentPage());
        switch (p.getCurrentPage()) {
            case 1:
                mFirstButton.setVisibility(View.GONE);
                mPrevButton.setVisibility(View.GONE);

                mPrevPageNum.setText(String.valueOf(p.getCurrentPage()));
                mCurrentPageNum.setText(String.valueOf(p.getCurrentPage() + 1));
                mNextPageNum.setText(String.valueOf(p.getCurrentPage() + 2));

                mPrevPageNum.setTextSize(HIGHLIGHTED_PAGE_FONT_SIZE);
                mCurrentPageNum.setTextSize(DEFAULT_PAGE_FONT_SIZE);
                break;
            default:
                mFirstButton.setVisibility(View.VISIBLE);
                mPrevButton.setVisibility(View.VISIBLE);

                mPrevPageNum.setText(String.valueOf(p.getCurrentPage() - 1));
                mCurrentPageNum.setText(String.valueOf(p.getCurrentPage()));
                mNextPageNum.setText(String.valueOf(p.getCurrentPage() + 1));

                mPrevPageNum.setTextSize(DEFAULT_PAGE_FONT_SIZE);
                mCurrentPageNum.setTextSize(HIGHLIGHTED_PAGE_FONT_SIZE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.paginator_action_first:
                mListener.onClickedFirstPage(v);
                break;
            case R.id.paginator_action_prev:
                mListener.onClickedPrevPage(v);
                break;
            case R.id.paginator_action_next:
                mListener.onClickedNextPage(v);
                break;
            default:
                break;
        }
    }

    public static interface PaginatorViewHolderClicks {
        public void onClickedFirstPage(View caller);
        public void onClickedPrevPage(View caller);
        public void onClickedNextPage(View caller);
    }
}
