package com.boredat.boredatdroid.network;

//import com.boredat.boredatdroid.models.Error;

/**
 * Created by Liz on 7/7/2015.
 */
public class BoredatResponse  {
    public Error error;

    public boolean isError() {
        return error != null;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error response) {
        this.error = response;
    }
    public static class Error {
        public String message;
        public String errorNo;
        public String moreInfo;

        public int status;

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setErrorNo(String errorNo) {
            this.errorNo = errorNo;
        }

        public String getMoreInfo() {
            return moreInfo;
        }

        public void setMoreInfo(String moreInfo) {
            this.moreInfo = moreInfo;
        }

        public String getMessage() {
            return message;
        }

        public int getErrorNo() {
            return Integer.valueOf(errorNo);
        }
    }
}
