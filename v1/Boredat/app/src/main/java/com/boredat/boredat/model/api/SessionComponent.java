package com.boredat.boredat.model.api;

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
    LocalBoardComponent plus(LocalBoardModule localBoardModule);
    GlobalBoardComponent plus(GlobalBoardModule globalBoardModule);
}
