package com.subwaysystem;

import java.util.ArrayList;

public class DijkstraUndirectedSP {
    private double[] distTo;
    private Edge[] edgeTo;
    private IndexMinPQ<Double> pq;
    private int source;

    public DijkstraUndirectedSP(EdgeWeightedGraph graph,int source){
        this.source = source;
        distTo = new double[graph.getV()];
        edgeTo = new Edge[graph.getV()];
        for(int v = 0;v<graph.getV();v++){
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[source] = 0.0;
        pq = new IndexMinPQ<>(graph.getV());
        pq.insert(source,distTo[source]);
        while(!pq.isEmpty()){
            int v = pq.delMin();
            for (Edge e : graph.adj(v)){
                relax(e,v);
            }
        }
        assert check(graph,source);
    }

    public void relax(Edge e,int v){
        int w = e.other(v);
        if (distTo[w] > distTo[v] + e.getWeight()) {
            distTo[w] = distTo[v] + e.getWeight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(Station stop) {
        return distTo[stop.getNumber()] < Double.POSITIVE_INFINITY;
    }

    public ArrayList<Integer> pathTo(Station stop) {
        int v = stop.getNumber();
        if (!hasPathTo(stop)) return null;
        ArrayList<Edge> Edge_path = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            Edge_path.add(e);
            x = e.other(x);
        }
        for (int i = Edge_path.size()-1;i>=0;i--){
            if (path.isEmpty()){
               int v1 = Edge_path.get(i).either();
               int w1 = Edge_path.get(i).other(v1);
                if (v1==source){
                    path.add(v1);
                    path.add(w1);
                }
                else if(w1==source){
                    path.add(w1);
                    path.add(v1);
                }
            }
            else path.add(Edge_path.get(i).other(path.get(path.size()-1)));
        }
        return path;
    }

    private boolean check(EdgeWeightedGraph G, int s) {

        // check that edge weights are nonnegative
        for (Edge e : G.edges()) {
            if (e.getWeight()< 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.getV(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.getV(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (distTo[v] + e.getWeight()< distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.getV(); w++) {
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            if (w != e.either() && w != e.other(e.either())) return false;
            int v = e.other(w);
            if (distTo[v] + e.getWeight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

}
