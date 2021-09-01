package model;

import dao.PerformancePOJO;
import dao.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AttendanceStatistics {
    ArrayList<PerformancePOJO> performance = new ArrayList<>();

    public AttendanceStatistics(){
        try{
            initialize();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Initializes the entries using the performance information from the database
     */
    public void initialize(){
         performance = Repository.getInstance().getAllPerformance();
    }

    public ArrayList<PerformancePOJO> getStatistics(){
        return performance;
    }
}
