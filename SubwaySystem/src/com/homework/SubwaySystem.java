package com.homework;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class SubwaySystem {
    private  final ArrayList<ArrayList<Station>> LINES;

    public SubwaySystem(){
        TxtReader.read();
        this.LINES = TxtReader.getLINES();
    }

    public ArrayList<ArrayList<Station>> getLINES() {
        return LINES;
    }

    /**
     * 输入站名，返回含有此站的所有线路
     * @param station
     * @return 含有此站的所有线路名称
     * @throws NotFoundException
     */
    public String searchForLine(String station) throws NotFoundException{
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
        }else throw new NotFoundException("not Found");
        return  tmp;
    }

    /**
     * 输入线路和终点站名（以表方向），按顺序返回该路线的所有站名
     * @param line
     * @param direction
     * @return 按顺序该路线上的所有站名
     * @throws NotFoundException
     */
    public String searchForStations(String line,String direction) throws NotFoundException{
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
            default:throw new NotFoundException("not Found");
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
            }else throw new NotFoundException("not Found");
        }
        return result;
    }

    /**
     * 根据给定的起点和终点，返回所有经过站点的集合
     * @param start
     * @param stop
     * @return 最短路径上经过的所有站的集合
     * @throws NotFoundException
     */
    public List<Station> getShortestPath(Station start,Station stop) throws NotFoundException{
        return null;
    }

    /**
     * 用直观的方式打印出给定路径所对应的乘车方式
     * @param path
     */
    public void printPath(List<Station> path){
        StringBuffer result = new StringBuffer();
        int[] n = new int[path.size()];
        int j = 0;
        ArrayList<String> changeStation = new ArrayList<>();
        try {
            changeStation.add(searchForLine(path.get(0).getName()));
            for (int i = 1;i<path.size()-1;i++) {
                if (!ifInOneLine(path.get(i - 1), path.get(i + 1))) {
                    n[i] = 1;
                    String[] s1 = searchForLine(path.get(i - 1).getName()).split("、");
                    String[] s2 = searchForLine(path.get(i).getName()).split("、");
                    String[] s3 = searchForLine(path.get(i + 1).getName()).split("、");
                    for (String line2 : s2) {
                        for (String line1 : s1) {
                            if (line1.equals(line2)) {
                                if (!changeStation.contains(line1))
                                    changeStation.add(line1);
                            }
                        }
                        for (String line3 : s3) {
                            if (line3.equals(line2)) {
                                if (!changeStation.contains(line3))
                                    changeStation.add(line3);
                            }
                        }
                    }
                }
            }
        }catch (NotFoundException e){
            System.out.println("not Found");
        }
        result.append("请乘坐"+changeStation.get(j++)+"从"+path.get(0).getName()+",");
        for (int i = 1;i<n.length - 1;i++){
            if (n[i]==1) {
                result.append("到" + path.get(i).getName() + ",转"+changeStation.get(j++));
                if (!path.get(i+1).getName().equals(path.get(path.size()-1))){
                    result.append("从"+path.get(i+1).getName());
                }
            }
        }
        result.append("，至终点站"+path.get(path.size()-1).getName());
        System.out.println(result.toString());
    }

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
        }catch (NotFoundException e){
            System.out.println("not Found,caught");
        }
        return result;
    }

    /**
     *给定路径，计算普通票价的消费
     * @param path
     * @return 普通票价花费
     */
    public double normalCost(List<Station> path){
        double result = 0.0;
        double distance = 0.0;
        int count = 0;
        for (Station station : path){
            distance+=station.getDisToNext();
        }
        for (int i = 1;i<path.size()-1;i++) {
            if (!ifInOneLine(path.get(i - 1), path.get(i + 1))) {
                count++;
            }
        }
        result += (count+1);
        return result;
    }

    public static void main(String[] args) {
        SubwaySystem subwaySystem = new SubwaySystem();
        ArrayList<Station> test = new ArrayList<>();
        test.add(subwaySystem.getLINES().get(1).get(0));
        test.add(subwaySystem.getLINES().get(1).get(1));
        test.add(subwaySystem.getLINES().get(1).get(2));
        test.add(subwaySystem.getLINES().get(1).get(3));
        test.add(subwaySystem.getLINES().get(1).get(4));
        test.add(subwaySystem.getLINES().get(1).get(5));
        test.add(subwaySystem.getLINES().get(6).get(2));
        test.add(subwaySystem.getLINES().get(6).get(3));
        test.add(subwaySystem.getLINES().get(6).get(4));
        test.add(subwaySystem.getLINES().get(6).get(5));
        test.add(subwaySystem.getLINES().get(2).get(16));
        test.add(subwaySystem.getLINES().get(2).get(15));
        try {
            System.out.println(subwaySystem.searchForLine("范湖"));
            System.out.println(subwaySystem.searchForStations("二号线", "天河机场"));
            subwaySystem.printPath(test);
        }catch (NotFoundException e){
            System.out.println("this station or line is not existed,caught!");
        }
    }

}

class NotFoundException extends Exception{
    public NotFoundException(String message){
        super(message);
    }
}
