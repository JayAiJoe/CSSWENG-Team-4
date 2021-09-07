package model;

import dao.Repository;
import dao.WorkdayPOJO;
import wrapper.WorkdayWrapper;

import java.util.ArrayList;
import java.util.Date;

public class WorkdayHandler {
    private Date startDate, endDate;
    private ArrayList<WorkdayWrapper> entries;

    public WorkdayHandler(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.entries = new ArrayList<>();

        initialize();
    }

    private void initialize() {
        ArrayList<WorkdayPOJO> workdays = Repository.getInstance().getWorkdays(startDate, endDate);
        for (WorkdayPOJO workday: workdays) {
            entries.add(new WorkdayWrapper(workday));
        }
    }

    public ArrayList<WorkdayWrapper> getEntries() {
        return entries;
    }

    public boolean addWorkday(Date date, int timeIn1, int timeOut1, int timeIn2, int timeOut2,
                           int overtimeIn, int overtimeOut) {
        WorkdayPOJO workday = new WorkdayPOJO(date, timeIn1, timeOut1, timeIn2,
                timeOut2, overtimeIn, overtimeOut);
        entries.add(0, new WorkdayWrapper(workday));

        return Repository.getInstance().addWorkday(workday);
    }
}
