package com.subwaysystem;

import java.util.*;

public class SubwaySystem {
    private final ArrayList<ArrayList<Station>> LINES;
    private final ArrayList<Station> allStations;
    private static final String TYPE_WUHANTONG = "武汉通";
    private static final String TYPE_RIPIAO = "日票";

    public SubwaySystem(){
        TxtReader.read();
        this.LINES = TxtReader.getLINES();
        this.allStations = TxtReader.getAllStations();
    }

    public ArrayList<ArrayList<Station>> getLINES() {
        return LINES;
    }

    /**
     * 输入站名，返回含有此站的所有线路
     * @param station
     * @return 含有此站的所有线路名称
     * @throws SubwayException
     */
    public String searchForLine(String station) throws SubwayException{
        StringBuffer result = new StringBuffer();
        if (station!=null){
            for(int i = 0;i<LINES.size();i++){
                for (Station s : LINES.get(i)){
                    if (s.getName().trim().equals(station)){
                        switch (i){
                            case 0: result.append("一号线、");
                                break;
                            case 1: result.append("二号线、");
                                break;
                            case 2: result.append("三号线、");
                                break;
                            case 3: result.append("四号线、");
                                break;
                            case 4: result.append("六号线、");
                                break;
                            case 5: result.append("七号线、");
                                break;
                            case 6: result.append("八号线、");
                                break;
                            case 7: result.append("阳逻线、");
                                break;
                            case 8: result.append("十一号线、");
                                break;
                            default: result.append("-1、");
                                break;
                        }
                    }
                }
            }
        }
        String tmp = result.toString();
        if(!tmp.equals("") && tmp!=null){
            tmp = tmp.substring(0,tmp.length()-1);
        }else throw new SubwayException("not Found");
        return  tmp;
    }

    /**
     * 输入线路和终点站名（以表方向），按顺序返回该路线的所有站名
     * @param line
     * @param direction
     * @return 按顺序该路线上的所有站名
     * @throws SubwayException
     */
    public String searchForStations(String line,String direction) throws SubwayException{
        ArrayList<Station> onLine;
        String result = null;
        switch(line){
            case "一号线": onLine = LINES.get(0);
                break;
            case "二号线": onLine = LINES.get(1);
                break;
            case "三号线": onLine = LINES.get(2);
                break;
            case "四号线": onLine = LINES.get(3);
                break;
            case "六号线": onLine = LINES.get(4);
                break;
            case "七号线": onLine = LINES.get(5);
                break;
            case "八号线": onLine = LINES.get(6);
                break;
            case "阳逻线": onLine = LINES.get(7);
                break;
            case "十一号线": onLine = LINES.get(8);
                break;
            default:throw new SubwayException("not Found");
        }
        if (onLine!=null){
            StringBuffer sb = new StringBuffer();
            if(direction.equals(onLine.get(onLine.size()-1).getName())){
                for (Station s : onLine){
                    sb.append(s.getName()+"、");
                }
                result = sb.toString();
                result = result.substring(0,result.length()-1);
            }else if(direction.equals(onLine.get(0).getName())){
                for(int i = onLine.size()-1;i>=0;i--){
                    sb.append(onLine.get(i).getName()+"、");
                }
                result = sb.toString();
                result = result.substring(0,result.length()-1);
            }else throw new SubwayException("not Found");
        }
        return result;
    }

    /**
     * 根据给定的起点和终点，返回所有经过站点的集合
     * 用已有的表来建立图，使用迪杰特斯拉算法找到两个站之间的最短路径
     * @param start
     * @param stop
     * @return 最短路径上经过的所有站的集合
     * @throws SubwayException
     */
    public List<Station> getShortestPath(String start,String stop) throws SubwayException{
        Station start_station = findStation(start);
        Station stop_station = findStation(stop);
        ArrayList<Station> result;
        SubwaySystem.EdgeWeightedGraph graph = new SubwaySystem.EdgeWeightedGraph();
        SubwaySystem.DijkstraUndirectedSP SP =
                new SubwaySystem.DijkstraUndirectedSP(graph,start_station);
        if (SP.hasPathTo(stop_station)){
            result = SP.pathTo(stop_station);
            return result;
        }
        else throw new SubwayException("can't find the way");
    }

    /**
     * 用直观的方式打印出给定路径所对应的乘车方式
     * 判断间隔站是否在同一条线上，如果不是，那么中间站就是换乘站
     * 再获取换乘站前后站所在的线路
     * @param path
     */
    public String printPath(List<Station> path){
        StringBuffer result = new StringBuffer();
        int[] n = new int[path.size()];
        int j = 0;
        ArrayList<String> changeStation = new ArrayList<>();
        try {
            String[] s1 = null;
            String[] s2 = null;
            String[] s3 = null;
            for (int i = 1;i<path.size()-1;i++) {
                if (!ifInOneLine(path.get(i - 1), path.get(i + 1))) {
                    n[i] = 1;
                    for (String name1 : oneLineName(path.get(i-1),path.get(i))){
                        if (changeStation.isEmpty()){
                            changeStation.add(name1);
                        }
                        else  if(!changeStation.get(changeStation.size()-1).equals(name1))
                            changeStation.add(name1);
                        else continue;
                    }
                    for (String name2 : oneLineName(path.get(i),path.get(i+1))){
                        if (changeStation.isEmpty()){
                            changeStation.add(name2);
                        }
                        else  if(!changeStation.get(changeStation.size()-1).equals(name2))
                            changeStation.add(name2);
                        else continue;
                    }
                }
            }
            if (changeStation.isEmpty()) {
                for (String name3 : oneLineName(path.get(0),path.get(1))){
                    changeStation.add(name3);
                }
            }
        }catch (SubwayException e){
            System.out.println("not Found");
        }
        result.append("请乘坐"+changeStation.get(j++)+"从"+path.get(0).getName()+",");
        for (int i = 1;i<n.length - 1;i++){
            if (n[i]==1) {
                result.append("到" + path.get(i).getName() + ",再转"+changeStation.get(j++));
                if (!path.get(i+1).getName().equals(path.get(path.size()-1))){
                    result.append("从"+path.get(i+1).getName());
                }
            }
        }
        result.append("至终点站"+path.get(path.size()-1).getName());
        return result.toString();
    }

    /**
     * 输入两个站名，判断是否在同一条线上
     * @param station1
     * @param station2
     * @return boolean是否在同一条线上
     */
    public boolean ifInOneLine(Station station1,Station station2){
        boolean result = false;
        try {
            String[] lines1 = searchForLine(station1.getName()).split("、");
            String[] lines2 = searchForLine(station2.getName()).split("、");
            for (String s : lines1){
                for (String ss : lines2){
                    if (s.equals(ss))
                        result = true;
                }
            }
        }catch (SubwayException e){
            System.out.println("not Found,caught");
        }
        return result;
    }

    /**
     * 输入两个站名，判断是否在同一条线上
     * @param station1
     * @param station2
     * @return String[]是否在同一条线上
     */
    public String[] oneLineName(Station station1,Station station2)throws SubwayException{
        ArrayList<String> result = new ArrayList<>();
        try {
            if (ifInOneLine(station1,station2)) {
                String[] lines1 = searchForLine(station1.getName()).split("、");
                String[] lines2 = searchForLine(station2.getName()).split("、");
                for (String s : lines1) {
                    for (String ss : lines2) {
                        if (s.equals(ss))
                            result.add(s);
                    }
                }
            }
            else throw new SubwayException("isn't in one line");
        }catch (SubwayException e){
            System.out.println("not Found,caught");
        }
        return result.toArray(new String[0]);
    }

    /**
     * 输入站名，返回对应的站
     * @param name
     * @return 对应站
     * @throws SubwayException
     */
    public Station findStation(String name) throws SubwayException{
        for (Station station:allStations){
            if(station.getName().equals(name)){
                return station;
            }
        }
        throw new SubwayException("not found");
    }

    /**
     *给定路径，计算普通票价的消费
     * @param path
     * @return 普通票价花费
     */
    public int normalCost(List<Station> path){
        int result = 0;
        double distance = 0.0;
        for (Station station : path){
            distance+=station.getDisToNext();
        }
        if (distance>0&&distance<=9){
            result = 2;
        }else if (distance>9&&distance<=14){
            result = 3;
        }else if (distance>14){
            int more = (int)(distance-14)/2;
            if ((distance-14)/2-more==0){
                result = 3+more;
            }else {
                result = 3+more+1;
            }
        }

        return result;
    }

    /**
     * 计算使用武汉通和日票乘客的票价
     * @param path
     * @return 计算使用武汉通和日票乘客的票价
     */
    public double specialCost(List<Station> path,String type){
        double result = 0.0;
        if (type.equals(TYPE_RIPIAO)){
            result = 0.0;
        }else if(type.equals(TYPE_WUHANTONG)){
            result = normalCost(path)*0.9;
        }
        return result;
    }

    private class Edge implements Comparable<Edge>{
        private final int v;
        private final Station stationV;
        private final int w;
        private final Station stationW;
        private final double weight;

        public Edge(Station stationV,Station stationW,double weight){
            if (stationV==null) throw new IllegalArgumentException("vertex station can't be null");
            if(stationW==null) throw new IllegalArgumentException("vertex station can't be null");
            if(Double.isNaN(weight)) throw new IllegalArgumentException("weight is nan");
            this.stationV = stationV;
            this.stationW = stationW;
            this.v = stationV.getNumber();
            this.w = stationW.getNumber();
            this.weight = weight;
        }

        public double getWeight(){
            return weight;
        }

        public int either(){
            return v;
        }
        public Station eitherStation(){
            return stationV;
        }
        public Station otherStatioin(Station S){
            Station result=null;
           if (S.getName().equals(stationV.getName()))
               result = stationW;
           else if(S.getName().equals(stationW.getName()))
               result = stationV;
           return result;
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

    private class EdgeWeightedGraph{
        private final int V;
        private int E;
        private ArrayList<ArrayList<Edge>> adj;

        public EdgeWeightedGraph(){
            this.V = allStations.size();
            adj = new ArrayList<>(V+1);
            for (int i = 0;i<=V;i++){
                adj.add(new ArrayList<>(V+1));
            }
            for (ArrayList<Station> oneLine : LINES){
                for (int i = 0;i<oneLine.size()-1;i++) {
                    double distance = oneLine.get(i).getDisToNext();
                    Edge edge = new Edge(oneLine.get(i),oneLine.get(i+1), distance);
                    addEdge(edge);
                }
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

    private class DijkstraUndirectedSP{
        private double[] distTo;
        private Edge[] edgeTo;
        private IndexMinPQ<Double> pq;
        private Station source;

        public DijkstraUndirectedSP(EdgeWeightedGraph graph,Station source){
            this.source = source;
            int s = source.getNumber();
            distTo = new double[graph.getV()];
            edgeTo = new Edge[graph.getV()];
            for(int v = 0;v<graph.V;v++){
                distTo[v] = Double.POSITIVE_INFINITY;
            }
            distTo[s] = 0.0;
            pq = new IndexMinPQ<>(graph.getV());
            pq.insert(s,distTo[s]);
            while(!pq.isEmpty()){
                int v = pq.delMin();
                for (Edge e : graph.adj.get(v)){
                    relax(e,v);
                }
            }
            assert check(graph,s);
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

        public ArrayList<Station> pathTo(Station stop) {
            int v = stop.getNumber();
            if (!hasPathTo(stop)) return null;
            ArrayList<Edge> Edge_path = new ArrayList<>();
            ArrayList<Station> path = new ArrayList<>();
            int x = v;
            for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
                Edge_path.add(e);
                x = e.other(x);
            }
            for (int i = Edge_path.size()-1;i>=0;i--){
                   if (path.isEmpty()){
                       Station station1 = Edge_path.get(i).eitherStation();
                       Station station2 = Edge_path.get(i).otherStatioin(Edge_path.get(i).eitherStation());
                       if (station1.getName().equals(source.getName())){
                           path.add(station1);
                           path.add(station2);
                       }
                       else if(station2.getName().equals(source.getName())){
                           path.add(station2);
                           path.add(station1);
                       }
                   }
                   else path.add(Edge_path.get(i).otherStatioin(path.get(path.size()-1)));
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

    public static void main(String[] args) {
        SubwaySystem subwaySystem = new SubwaySystem();
        try {
            System.out.println(subwaySystem.searchForLine("范湖"));
            System.out.println(subwaySystem.searchForStations("二号线", "天河机场"));
            List<Station> test = subwaySystem.getShortestPath("黄金口","香港路");
            System.out.println(subwaySystem.printPath(test));
            System.out.println(subwaySystem.normalCost(test));
            System.out.println(subwaySystem.specialCost(test,"武汉通"));
            System.out.println(subwaySystem.specialCost(test,"日票"));
        }catch (SubwayException e){
            System.out.println("this station or line is not existed,caught!");
        }
    }

}
