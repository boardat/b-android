package com.boredat.boredatdroid.models;

import com.boredat.boredatdroid.network.BoredatResponse;

/**
 * Created by Liz on 10/12/2015.
 */
public class ServerMessage extends BoredatResponse {
    private String status;
    private String message;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}