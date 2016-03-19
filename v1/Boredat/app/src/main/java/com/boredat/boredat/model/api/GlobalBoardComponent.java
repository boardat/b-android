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
@GlobalBoardScope
@Subcomponent (
        modules={
            GlobalBoardModule.class
        }
)
public interface GlobalBoardComponent {
    LoungeFragment inject(LoungeFragment loungeFragment);
    SessionMainActivity inject(SessionMainActivity sessionMainActivity);
    FeedFragment inject(FeedFragment feedFragment);
    DetailPostFragment inject(DetailPostFragment detailPostFragment);
    ComposeNewPostFragment inject(ComposeNewPostFragment composeNewPostFragment);
}
