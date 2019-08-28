package com.company;

public class Car {
    private String Name, ABS, MT, AT;
    private String DisplayName;
    private String Description;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getABS() {
        return ABS;
    }

    public void setABS(String ABS) {
        this.ABS = ABS;
    }

    public String getMT() {
        return MT;
    }

    public void setMT(String MT) {
        this.MT = MT;
    }

    public String getAT() {
        return AT;
    }

    public void setAT(String AT) {
        this.AT = AT;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
