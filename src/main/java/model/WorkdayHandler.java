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

    public boolean addWorkday(Date date, int timeIn1, int timeOut1, int timeIn2, int timeOut2,
                           int overtimeIn, int overtimeOut) {
        WorkdayPOJO workday = new WorkdayPOJO(date, timeIn1, timeOut1, timeIn2,
                timeOut2, overtimeIn, overtimeOut);
        entries.add(0, workday);

        return Repository.getInstance().addWorkday(workday);
    }
}
