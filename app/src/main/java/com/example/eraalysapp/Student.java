package com.example.eraalysapp;

public class Student {
    String name;
    String group;
    int image;

    public Student(String name, String group, int image) {
        this.name = name;
        this.group = group;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
