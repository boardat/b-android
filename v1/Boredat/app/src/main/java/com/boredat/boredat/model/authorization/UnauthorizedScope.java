package com.boredat.boredat.model.authorization;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Liz on 2/11/2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UnauthorizedScope {
}
