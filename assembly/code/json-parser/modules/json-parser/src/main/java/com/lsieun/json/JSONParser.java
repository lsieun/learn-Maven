package com.lsieun.json;

import java.io.File;
import java.io.FileReader;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

public class JSONParser {
    public static void main(String[] args) {
        FileReader fileReader;
        JSONObject json;
//        JSONParser parser = new JSONParser();
        org.json.simple.parser.JSONParser parser;
        parser = new org.json.simple.parser.JSONParser();

        try {
            if (args == null || args.length == 0 || args[0] == null || !new File(args[0]).exists()) {
                System.out.println("No valid JSON file provided");
            }
            else {
                fileReader = new FileReader(new File(args[0]));
                json = (JSONObject) parser.parse(fileReader);
                if (json != null) {
                    System.out.println(json.toString());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
