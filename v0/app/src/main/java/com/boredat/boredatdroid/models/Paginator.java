package com.boredat.boredatdroid.models;

/**
 * Created by Liz on 8/3/2015.
 */
public class Paginator {
    private int currentPage;

    public Paginator() {
        this.currentPage = 1;
    }

    public Paginator(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setNextPage() {
        this.currentPage++;
    }

    public void setPrevPage() {
        this.currentPage--;
    }

    @Override
    public String toString() {
        return "Paginator{" +
                "currentPage=" + currentPage +
                '}';
    }
}
