package com.subwaysystem;

import java.util.ArrayList;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private ArrayList<ArrayList<Edge>> adj;

    public EdgeWeightedGraph(ArrayList<Edge> Edges){
        int maxCount = 0;
        for(Edge edge : Edges){
            maxCount = edge.either()>maxCount?edge.either():maxCount;
            maxCount = edge.other(edge.either())>maxCount?edge.other(edge.either()):maxCount;
        }
        this.V = maxCount;
        this.E = 0;
        adj = new ArrayList<>(V+1);
        for (int i = 0;i<=V;i++){
            adj.add(new ArrayList<Edge>(V+1));
        }
        for (Edge edge : Edges){
            addEdge(edge);
        }
    }

    public void addEdge(Edge e){
        int v = e.either();
        int w = e.other(v);
        adj.get(v).add(e);
        adj.get(w).add(e);
        E++;
    }

    public ArrayList<Edge> adj(int v){
        return adj.get(v);
    }

    public Iterable<Edge> edges() {
        ArrayList<Edge> list = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // add only one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    public int getV(){
        return V;
    }
}
