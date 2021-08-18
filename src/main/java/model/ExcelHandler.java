package model;

import java.io.*;
import java.util.ArrayList;


public class ExcelHandler {


    public ArrayList<ArrayList<Double>> readSSSTable(String filePath) {
        ArrayList<ArrayList<Double>> table = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = "";
            String splitBy = ",";
            br.readLine();
            while((line = br.readLine()) != null){
                ArrayList<Double> range = new ArrayList<>();
                String[] values = line.split(splitBy);
                for (String value: values){
                    range.add(Double.parseDouble(value));
                }
                table.add(range);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }
}
