package com.homework;

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
    public String getName(){
        return name;
    }
}
