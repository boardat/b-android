package com.boredat.boredat.model.api;

import com.boredat.boredat.activities.SessionMainActivity;
import com.boredat.boredat.fragments.ComposeNewPostFragment;
import com.boredat.boredat.fragments.DetailPostFragment;
import com.boredat.boredat.fragments.FeedFragment;
import com.boredat.boredat.fragments.LoungeFragment;

import dagger.Subcomponent;

/**
 * Created by Liz on 3/19/2016.
 */
@LocalBoardScope
@Subcomponent (
        modules={
            LocalBoardModule.class
        }
)
public interface LocalBoardComponent {
    LoungeFragment inject(LoungeFragment loungeFragment);
    SessionMainActivity inject(SessionMainActivity sessionMainActivity);
    FeedFragment inject(FeedFragment feedFragment);
    DetailPostFragment inject(DetailPostFragment detailPostFragment);
    ComposeNewPostFragment inject(ComposeNewPostFragment composeNewPostFragment);
}
