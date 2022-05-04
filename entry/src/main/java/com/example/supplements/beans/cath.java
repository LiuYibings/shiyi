package com.example.supplements.beans;

import java.util.List;

public class cath {
    private int id;
    private int id1;
    private int id2;
    private int idone;
    private int thing_id;
    private String cathdate;
    private List<params> params;

    @Override
    public String toString() {
        return "cath{" +
                "id=" + id +
                ", id1=" + id1 +
                ", id2=" + id2 +
                ", idone=" + idone +
                ", thing_id=" + thing_id +
                ", cathdate='" + cathdate + '\'' +
                ", params=" + params +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public int getIdone() {
        return idone;
    }

    public void setIdone(int idone) {
        this.idone = idone;
    }

    public int getThing_id() {
        return thing_id;
    }

    public void setThing_id(int thing_id) {
        this.thing_id = thing_id;
    }

    public String getCathdate() {
        return cathdate;
    }

    public void setCathdate(String cathdate) {
        this.cathdate = cathdate;
    }

    public List<com.example.supplements.beans.params> getParams() {
        return params;
    }

    public void setParams(List<com.example.supplements.beans.params> params) {
        this.params = params;
    }

    public cath(int id, int id1, int id2, int idone, int thing_id, String cathdate, List<com.example.supplements.beans.params> params) {
        this.id = id;
        this.id1 = id1;
        this.id2 = id2;
        this.idone = idone;
        this.thing_id = thing_id;
        this.cathdate = cathdate;
        this.params = params;
    }

    public cath() {
    }
}
