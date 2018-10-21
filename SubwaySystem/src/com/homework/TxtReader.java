package com.homework;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtReader {
    private static final String FILE_PATH = "\\src\\com.homework\\subway.txt";
    private static final String PATTERN_STRING = "([a-zA-Z]+?)---([a-zA-z]+?])";

    public static void read(){
        File file = new File(FILE_PATH);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            Pattern pattern = Pattern.compile(PATTERN_STRING);
            String tmp = null,from = null,to = null;
            StringBuffer sb = new StringBuffer();
            while((tmp=bufferedReader.readLine())!=null){
                Matcher matcher = pattern.matcher(tmp);
                if (matcher.find()){
                    
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("the file can be found");
        }catch (IOException ee){
            ee.printStackTrace();
        }
    }

}
