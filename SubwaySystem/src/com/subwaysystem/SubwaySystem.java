package com.subwaysystem;

import java.util.*;

public class SubwaySystem {
    private final ArrayList<ArrayList<Station>> LINES;
    private final ArrayList<Edge> EDGES;
    private static final String TYPE_WUHANTONG = "武汉通";
    private static final String TYPE_RIPIAO = "日票";

    public SubwaySystem(){
        TxtReader.read();
        this.LINES = TxtReader.getLINES();
        this.EDGES = TxtReader.getEdges();
    }

    /**
     * 输入站名，返回含有此站的所有线路
     * @param station
     * @return 含有此站的所有线路名称
     * @throws SubwayException
     **/
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
     **/
    public String searchForStations(String line,String direction) throws SubwayException{
        ArrayList<Station> onLine;
        String result = null;
        onLine = getOneLine(line);
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



    public String[] getFinalStations(String line) throws SubwayException{
        ArrayList<Station> oneLine;
        oneLine = getOneLine(line);
        String[] finals = new String[2];
        finals[1] = oneLine.get(oneLine.size()-1).getName();
        finals[2] = oneLine.get(0).getName();
        return finals;
    }

    public ArrayList<Station> getOneLine(String name) throws SubwayException{
        ArrayList<Station> oneLine;
        switch(name) {
            case "一号线":
                oneLine = LINES.get(0);
                break;
            case "二号线":
                oneLine = LINES.get(1);
                break;
            case "三号线":
                oneLine = LINES.get(2);
                break;
            case "四号线":
                oneLine = LINES.get(3);
                break;
            case "六号线":
                oneLine = LINES.get(4);
                break;
            case "七号线":
                oneLine = LINES.get(5);
                break;
            case "八号线":
                oneLine = LINES.get(6);
                break;
            case "阳逻线":
                oneLine = LINES.get(7);
                break;
            case "十一号线":
                oneLine = LINES.get(8);
                break;
            default:
                throw new SubwayException("not Found");
        }
        return oneLine;
    }

    /**
     * 根据给定的起点和终点，返回所有经过站点的集合
     * 用已有的表来建立图，使用迪杰特斯拉算法找到两个站之间的最短路径
     * @param start
     * @param stop
     * @return 最短路径上经过的所有站的集合
     * @throws SubwayException
     **/
    public List<Station> getShortestPath(String start,String stop) throws SubwayException{
        Station start_station = findStationByName(start);
        Station stop_station = findStationByName(stop);
        ArrayList<Station> result = new ArrayList<>();
        EdgeWeightedGraph graph = new EdgeWeightedGraph(EDGES);
        DijkstraUndirectedSP SP = new DijkstraUndirectedSP(graph,start_station.getNumber());
        if (SP.hasPathTo(stop_station)){
            for (int i: SP.pathTo(stop_station)){
                result.add(findStationByNumber(i));
            }
            return result;
        }
        else throw new SubwayException("can't find the way");
    }

    /**
     * 用直观的方式打印出给定路径所对应的乘车方式
     * 判断间隔站是否在同一条线上，如果不是，那么中间站就是换乘站
     * 再获取换乘站前后站所在的线路
     * @param path
     **/
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
                    result.append("从"+path.get(i).getName());
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
     **/
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
     **/
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
     **/
    public Station findStationByName(String name) throws SubwayException{
        for (ArrayList<Station> oneLine : LINES){
            for (Station s : oneLine){
                if (s.getName().equals(name)){
                    return s;
                }
            }
        }
        throw new SubwayException("not found");
    }

    /**
     * 输入编号，返回对应的站
     * @param number
     * @return 对应站
     * @throws SubwayException
     **/
    public Station findStationByNumber(int number) throws SubwayException{
        for (ArrayList<Station> oneLine : LINES){
            for (Station s : oneLine){
                if (s.getNumber() == number){
                    return s;
                }
            }
        }
        throw new SubwayException("not found");
    }

    /**
     *给定路径，计算普通票价的消费
     * @param path
     * @return 普通票价花费
     **/
    public int normalCost(List<Station> path){
        int result = 0;
        double distance = 0.0;
        for (Edge edge : EDGES){
            for (int i = 0;i<path.size()-1;i++){

                if (path.get(i).getNumber()==edge.either()
                        &&path.get(i+1).getNumber()==edge.other(edge.either())){
                    distance+=edge.getWeight();
                }
                else if(path.get(i+1).getNumber()==edge.either()
                        &&path.get(i).getNumber()==edge.other(edge.either())){
                    distance+=edge.getWeight();
                }
            }
        }
        if (distance>0&&distance<=9){
            result = 2;
        } else result = getMoney(distance-9,5);
        return result;
    }

    public int getMoney(double distance,int n){
        if(distance-n<=0){
            return (n+1)/2;
        }else return getMoney(distance-n,n+2);
    }

    /**
     * 计算使用武汉通和日票乘客的票价
     * @param
     * @return 计算使用武汉通和日票乘客的票价
     **/
   public double specialCost(List<Station> path,String type){
        double result = 0.0;
        if (type.equals(TYPE_RIPIAO)){
            result = 0.0;
        }else if(type.equals(TYPE_WUHANTONG)){
            result = normalCost(path)*0.9;
        }
        return result;
    }

    public static void main(String[] args) {
        SubwaySystem subwaySystem = new SubwaySystem();
        try {
            System.out.println(subwaySystem.searchForLine("范湖"));
            System.out.println(subwaySystem.searchForStations("二号线", "天河机场"));
            List<Station> test = subwaySystem.getShortestPath("汉口火车站","光谷广场");
            System.out.println(subwaySystem.printPath(test));
            System.out.println(subwaySystem.normalCost(test));
            System.out.println(String.format("%.2f",subwaySystem.specialCost(test,"武汉通")));
            System.out.println(subwaySystem.specialCost(test,"日票"));
        }catch (SubwayException e){
            System.out.println("this station or line is not existed,caught!");
        }
    }

}
