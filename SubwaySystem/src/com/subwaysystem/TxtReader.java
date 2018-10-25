package com.subwaysystem;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtReader {
    private static final String FILE_PATH =
            ".\\src\\com\\assets\\subway.txt";
    private static final String PATTERN_STRING_STATION =
            "(\\W+)---(\\W+)\t([0-9]*\\.[0-9]+)";
    private static final String PATTERN_STRING_LINE =
            "(([0-9]\\W*站点)|(阳逻线站点))间距站点名称\t间距（KM）";
    private static ArrayList<Station> allStations = new ArrayList<>();
    private static final ArrayList<ArrayList<Station>> LINES  = new ArrayList<>();

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
                extractStations(line);
            }
            setAllStations();
        }catch (FileNotFoundException e){
            System.out.println("the file can't be found");
        }catch (IOException ee){
            ee.printStackTrace();
        }
    }

    /**
     *tmp为全文中关于某一条线路的String,使用正则表达式提取出每字符
     * 单元中两个站的名字与两个站的间距，每个单元只生成一个站，然后
     * 添加最后一个站，最后一个站距下一个站的距离为零
     * @param tmp
     * @return 一条地铁线路并添加到LINES中
     */
    public static void extractStations(String tmp) {
        if (tmp != null && !tmp.equals("")) {
            String name1 = null;
            String name2 = null;
            double distance = 0.0;
            ArrayList<Station> oneLine = new ArrayList<>();
            ArrayList<String> stationNames = new ArrayList<>();
            Pattern pattern = Pattern.compile(PATTERN_STRING_STATION);
            Matcher matcher = pattern.matcher(tmp);
            while (matcher.find()) {
                name1 = matcher.group(1);
                name2 = matcher.group(2);
                distance = Double.parseDouble(matcher.group(3));
                stationNames.add(name1);
                oneLine.add(new Station(0,name1,distance));

            }
            oneLine.add(new Station(0,name2,0));
            LINES.add(oneLine);
        }
    }

    /**
     * 将所有站提取出来，并编号
     * @return 添加到allStations中
     */
    public static void setAllStations(){
        int count = 0;
        for (ArrayList<Station> oneLine:LINES){
            for (Station s:oneLine){
                if (allStations.isEmpty()){
                    s.setNumber(count++);
                    allStations.add(s);
                }
                else {
                    boolean isIn = false;
                    for (Station s1 : allStations) {
                        if (!s.getName().equals(s1.getName()))
                            continue;
                        else isIn = true;
                    }
                    if (isIn==false) {
                        s.setNumber(count++);
                        allStations.add(s);
                    }
                }
            }
        }
        for (ArrayList<Station> oneLine:LINES){
            for(Station s : oneLine){
                for (Station s1: allStations){
                    if (s.getName().equals(s1.getName())){
                        s.setNumber(s1.getNumber());
                    }
                }
            }
        }
    }

    public static ArrayList<Station> getAllStations(){
        return allStations;
    }

    public static ArrayList<ArrayList<Station>> getLINES(){
        return LINES;
    }

}