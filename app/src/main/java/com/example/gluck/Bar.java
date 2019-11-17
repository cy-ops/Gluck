package com.example.gluck;

public class Bar {
    private String about;
    private String brand;
    private String location;
    private String offer;
    private String image;

    public Bar() {
    }

    public Bar(String about, String brand, String location, String offer, String image) {
        this.about = about;
        this.brand = brand;
        this.location = location;
        this.offer = offer;
        this.image = image;
    }

    public String getAbout() {
        return about;
    }

    public String getBrand() {
        return brand;
    }

    public String getLocation() {
        return location;
    }

    public String getOffer() {
        return offer;
    }

    public String getImage(){
        return image;
    }
}
