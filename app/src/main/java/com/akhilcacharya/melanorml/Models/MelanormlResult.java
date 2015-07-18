package com.akhilcacharya.melanorml.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MelanormlResult {
    private ArrayList<Prediction> predictions = new ArrayList<Prediction>();
    public ArrayList<Prediction> getPredictions(){
        return this.predictions;
    }
}
