package com.akhilcacharya.melanorml.Models;


import java.util.Comparator;

public class Prediction implements Comparator<Prediction>, Comparable <Prediction>{

    private String class_id;
    private String class_name;
    private float prob;

    public String getClassId() {
        return class_id;
    }

    public String getClassName() {
        return class_name;
    }

    public float getProb() {
        return (prob);
    }

    @Override
    public int compareTo(Prediction another) {
        return (int) (this.prob - another.getProb());
    }

    @Override
    public int compare(Prediction lhs, Prediction rhs) {
        return (int) (lhs.getProb() - rhs.getProb());
    }
}
