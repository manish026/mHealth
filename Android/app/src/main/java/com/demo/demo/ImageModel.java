package com.demo.demo;


import java.io.Serializable;

public class ImageModel implements Serializable{



    String url;


    public ImageModel(String url) {
        this.url = url;


    }


    public String getUrl() {
        return url;
    }




}