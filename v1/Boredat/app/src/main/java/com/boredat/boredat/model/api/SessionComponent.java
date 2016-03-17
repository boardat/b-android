package com.boredat.boredat.model.api;

import com.boredat.boredat.activities.SessionMainActivity;
import com.boredat.boredat.fragments.ComposeNewPostFragment;
import com.boredat.boredat.fragments.DetailPostFragment;
import com.boredat.boredat.fragments.FeedFragment;
import com.boredat.boredat.fragments.LoungeFragment;

import dagger.Subcomponent;

/**
 * Created by Liz on 2/7/2016.
 */
@SessionScope
@Subcomponent(
        modules={
                SessionModule.class
        }
)
public interface SessionComponent {
    LoungeFragment inject(LoungeFragment loungeFragment);
    SessionMainActivity inject(SessionMainActivity sessionMainActivity);
    FeedFragment inject(FeedFragment feedFragment);
    DetailPostFragment inject(DetailPostFragment detailPostFragment);
    ComposeNewPostFragment inject(ComposeNewPostFragment composeNewPostFragment);
}
