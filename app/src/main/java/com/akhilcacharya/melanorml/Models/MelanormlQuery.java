package com.akhilcacharya.melanorml.Models;


public class MelanormlQuery {

    private int classifier_id = 27707; //Base/Accurate set: 25516
    private String image_url;

    public MelanormlQuery(String image){
        this.image_url = "data:image/jpg;base64," + image;
    }

    public String getImage_url() {
        return image_url;
    }
}
