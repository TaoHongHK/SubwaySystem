package com.homework;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtReader {
    private static final String FILE_PATH =
            ".\\src\\com\\homework\\subway.txt";
    private static final String PATTERN_STRING_STATION =
            "(\\W+)---(\\W+)\t([0-9]*\\.[0-9]+)";
    private static final String PATTERN_STRING_LINE =
            "(([0-9]\\W*站点)|(阳逻线站点))间距站点名称\t间距（KM）";
    private static int count = 0;
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
        }catch (FileNotFoundException e){
            System.out.println("the file can't be found");
        }catch (IOException ee){
            ee.printStackTrace();
        }
    }

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
                oneLine.add(new Station(++count,name1,distance));

            }
            oneLine.add(new Station(++count,name2,0));
            LINES.add(oneLine);
        }
    }

    public static ArrayList<ArrayList<Station>> getLINES(){
        return LINES;
    }

}