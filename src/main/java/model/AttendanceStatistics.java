package model;

import dao.PerformancePOJO;
import dao.Repository;

import java.util.ArrayList;

public class AttendanceStatistics {
    ArrayList<PerformancePOJO> performance = new ArrayList<>();

    public AttendanceStatistics() {
        initialize();
    }

    /**
     * Initializes the entries using the performance information from the database
     */
    public void initialize() {
        performance = Repository.getInstance().getAllPerformance();
    }

    public ArrayList<PerformancePOJO> getStatistics() {
        return performance;
    }
}
