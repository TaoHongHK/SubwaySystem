package com.subwaysystem;

public class Station {
    private int number;
    private String name;
    private double disToNext;

    public Station(int number,String name,double disToNext){
        this.number = number;
        this.name = name;
        this.disToNext = disToNext;
    }

    public int getNumber(){
        return number;
    }
    public void setNumber(int number){
        this.number = number;
    }
    public String getName(){
        return name;
    }
    public double getDisToNext(){
        return disToNext;
    }
}
