package com.boredat.boredatdroid.models;

import com.boredat.boredatdroid.network.BoredatResponse;

/**
 * Created by Liz on 7/9/2015.
 */
public class Personality extends BoredatResponse{
    public String networkName;
    public String personalityId;
    public String personalityName;
    public int postCount;
    public String image;
    public int profileViews;
}
