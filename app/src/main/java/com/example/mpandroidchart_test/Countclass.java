package com.example.mpandroidchart_test;

public class Countclass {
    private int posCount;
    private int neuCount;
    private int negCount;

    public Countclass(int posCount, int neuCount, int negCount) {
        this.posCount = posCount;
        this.neuCount = neuCount;
        this.negCount = negCount;
    }

    public Countclass() {
    }

    public int getPosCount() {
        return posCount;
    }

    public void setPosCount(int posCount) {
        this.posCount = posCount;
    }

    public int getNeuCount() {
        return neuCount;
    }

    public void setNeuCount(int neuCount) {
        this.neuCount = neuCount;
    }

    public int getNegCount() {
        return negCount;
    }

    public void setNegCount(int negCount) {
        this.negCount = negCount;
    }
}
