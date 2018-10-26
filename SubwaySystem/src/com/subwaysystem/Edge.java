package com.subwaysystem;

public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v,int w,double weight){
        if (v<0) throw new IllegalArgumentException("vertex station can't be null");
        if(w<0) throw new IllegalArgumentException("vertex station can't be null");
        if(Double.isNaN(weight)) throw new IllegalArgumentException("weight is nan");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }

    public int either(){
        return v;
    }
    public int other(int vertex){
        if(vertex==v) return w;
        else if(vertex==w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    @Override
    public int compareTo(Edge edge){
        return 0;
    }
}
