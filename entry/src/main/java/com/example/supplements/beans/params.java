package com.example.supplements.beans;

public class params {
    private int id;
    private int idone;
    private String one;
    private int who;

    @Override
    public String toString() {
        return "params{" +
                "id=" + id +
                ", idone=" + idone +
                ", one='" + one + '\'' +
                ", who=" + who +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdone() {
        return idone;
    }

    public void setIdone(int idone) {
        this.idone = idone;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public params() {
    }

    public params(int id, int idone, String one, int who) {
        this.id = id;
        this.idone = idone;
        this.one = one;
        this.who = who;
    }
}
