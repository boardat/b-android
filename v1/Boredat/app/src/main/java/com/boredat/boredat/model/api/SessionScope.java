package com.boredat.boredat.model.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Liz on 2/7/2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionScope {
}
