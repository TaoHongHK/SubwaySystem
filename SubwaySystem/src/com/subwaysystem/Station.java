package com.subwaysystem;

public class Station {
    private int number;
    private String name;

    public Station(int number,String name){
        this.number = number;
        this.name = name;
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
}
