package com.boredat.boredat.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.boredat.boredat.R;

/**
 * Created by Liz on 2/2/2016.
 */
public class NewsworthyButton extends ImageButton {
    private static final int[] STATE_HAS_VOTED_NEWSWORTHY = {R.attr.state_has_voted_newsworthy};
    private boolean mHasVotedNewsworthy = false;


    public NewsworthyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHasVotedNewsworthy(boolean hasVotedNewsworthy) {
        if (this.mHasVotedNewsworthy != hasVotedNewsworthy) {
            this.mHasVotedNewsworthy = hasVotedNewsworthy;
            refreshDrawableState();
        }
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (mHasVotedNewsworthy) {
            mergeDrawableStates(drawableState, STATE_HAS_VOTED_NEWSWORTHY);
        }
        return drawableState;
    }
}
