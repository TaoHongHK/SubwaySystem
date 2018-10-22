package com.homework;

public class DirectedEdge {
    private final int v;            //边的起点
    private final int w;            //边的终点
    private final double weight;    //边的权重

    public DirectedEdge(int v,int w,double weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }

    public int getFrom(){
        return v;
    }

    public int getTo(){
        return w;
    }

    @Override
    public String toString(){
        return String.format("%d-->%d %.2f",v,w,weight);
    }

}
