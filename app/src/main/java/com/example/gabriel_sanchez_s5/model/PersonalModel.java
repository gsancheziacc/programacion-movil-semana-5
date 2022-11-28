package com.example.gabriel_sanchez_s5.model;

public class PersonalModel {
    private int id;
    private String name;
    private String chargeType;
    private byte[] imageData;

    public PersonalModel(String name, String chargeType, byte[] imageData) {
        this.name = name;
        this.chargeType = chargeType;
        this.imageData = imageData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
