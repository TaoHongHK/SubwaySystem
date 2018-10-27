package com.subwaysystem;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtReader {
    private static final String FILE_PATH =
            ".\\src\\com\\assets\\subway.txt";
    private static final String PATTERN_STRING_STATION =
            "(\\W+)---(\\W+)\t([0-9]*\\.[0-9]+)";
    private static final String PATTERN_STRING_LINE =
            "(([0-9]\\W*站点)|(阳逻线站点))间距站点名称\t间距（KM）";
    private static ArrayList<ArrayList<Station>> LINES  = new ArrayList<>();
    private static Map<String,Integer> allStations = new HashMap<>();
    private static ArrayList<Edge> EDGES = new ArrayList<>();
    private static int count = 0;

    public static void read(){
        File file = new File(FILE_PATH);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String tmp = null;
            String[] LINES_string;
            StringBuffer sb = new StringBuffer();
            while((tmp=bufferedReader.readLine())!=null){
                sb.append(tmp);
            }
            LINES_string = sb.toString().split(PATTERN_STRING_LINE);
            System.out.println("Successful");
            for (String line : LINES_string){
                extractStationsAndEdges(line);
            }
        }catch (FileNotFoundException e){
            System.out.println("the file can't be found");
        }catch (IOException ee){
            ee.printStackTrace();
        }
    }

    /**
     * tmp为全文中关于某一条线路的String,使用正则表达式提取出每字符
     * 单元中两个站的名字与两个站的间距，每个单元只生成一个站，一个
     * 站拥有一个代表值便于用来建图，最终读出一个线路表，和所有相连边
     * @param tmp
     * @return 一条地铁线路并添加到LINES中
     */
    public static void extractStationsAndEdges(String tmp) {
        if (tmp != null && !tmp.equals("")) {
            ArrayList<Station> oneLine = new ArrayList<>();
            Pattern pattern = Pattern.compile(PATTERN_STRING_STATION);
            Matcher matcher = pattern.matcher(tmp);
            while (matcher.find()) {
                String name1 = matcher.group(1);
                String name2 = matcher.group(2);
                double distance = Double.parseDouble(matcher.group(3));
                addToOneLine(name1,oneLine);
                addToOneLine(name2,oneLine);
                Edge oneEdge = new Edge(allStations.get(name1),allStations.get(name2),distance);
                if(!isInEdges(oneEdge)){
                    EDGES.add(oneEdge);
                }
            }
            LINES.add(oneLine);
        }
    }

    public static boolean isInEdges(Edge edge){
        for(Edge edge1 : EDGES){
            if (edge.either()==edge1.either()&&
                    edge.other(edge.either())==edge1.other(edge1.either())){
                return true;
            }
            else if (edge.either()==edge1.other(edge1.either())&&
                    edge.other(edge.either())==edge1.either()){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断所有站列表是否含有此站，没有的话则向所有站列表与线路
     * 列表中加入此站，并定义编号
     * 否则提取此站已有的编号，加入到线路列表中
     * @param name
     * @param oneLine
     */
    private static void addToOneLine(String name,ArrayList<Station> oneLine) {
        if (!allStations.containsKey(name)){
            allStations.put(name,count);
            oneLine.add(new Station(count, name));
            count++;
        }else{
            boolean contains = false;
            for (Station station : oneLine) {
                if (name.equals(station.getName()))
                    contains = true;
            }
            if (contains==false){
                oneLine.add(new Station(allStations.get(name),name));
            }
        }
    }

    public static ArrayList<Edge> getEdges(){
        return EDGES;
    }

    public static ArrayList<ArrayList<Station>> getLINES(){
        return LINES;
    }

}