package com.example.gluck;

public class Restaurant {
    private String About;
    private String Brand;
    private String Location;
    private String Offer;

    public Restaurant() {
    }

    public Restaurant(String about, String brand, String location, String offer) {
        About = about;
        Brand = brand;
        Location = location;
        Offer = offer;
    }

    public String getAbout() {
        return About;
    }

    public String getBrand() {
        return Brand;
    }

    public String getLocation() {
        return Location;
    }

    public String getOffer() {
        return Offer;
    }
}
