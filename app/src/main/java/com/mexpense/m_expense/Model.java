package com.mexpense.m_expense;

public class Model {
    public String tripid;
    public String tripname;
    public String tripdest;

    public Model(String tripid, String tripname, String tripdest) {
        this.tripid = tripid;
        this.tripname = tripname;
        this.tripdest = tripdest;
    }

    public String getTripid() {
        return tripid;
    }

    public String getTripname() {
        return tripname;
    }

    public String getTripdest() {
        return tripdest;
    }
}
