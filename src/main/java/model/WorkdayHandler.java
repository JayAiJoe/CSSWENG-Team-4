package model;

import dao.Repository;
import dao.WorkdayPOJO;

import java.util.ArrayList;
import java.util.Date;

public class WorkdayHandler {
    private Date startDate, endDate;
    private ArrayList<WorkdayPOJO> entries;

    public WorkdayHandler(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.entries = new ArrayList<>();

        initialize();
    }

    private void initialize() {
        entries = Repository.getInstance().getWorkdays(startDate, endDate);
    }

    public ArrayList<WorkdayPOJO> getEntries() {
        return entries;
    }
}
