package com.example.listviewjava;

import android.view.View;

public class cars {
    String name;
    String company;
    int year;
    int image;
    cars(String name,String company,int year,int image){
        this.name = name;
        this.company = company;
        this .year = year;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public int getYear() {
        return year;
    }

    public int getImage() {
        return image;
    }
}
