package model;

import dao.PerformancePOJO;
import dao.Repository;
import wrapper.PerformanceWrapper;

import java.util.ArrayList;
import java.util.Date;

public class AttendanceStatistics {
    ArrayList<PerformanceWrapper> statistics = new ArrayList<>();

    public AttendanceStatistics() {
        initialize();
    }

    /**
     * Initializes the entries using the performance information from the database
     */
    private void initialize() {
        ArrayList<PerformancePOJO> performances = Repository.getInstance().getAllPerformance();
        for (PerformancePOJO performance: performances) {
            performance.setDatePaid(new Date(performance.getDatePaid().getTime() - 8 * 3600000L));
            statistics.add(new PerformanceWrapper(performance));
        }
    }

    public ArrayList<PerformanceWrapper> getStatistics() {
        return statistics;
    }
}
