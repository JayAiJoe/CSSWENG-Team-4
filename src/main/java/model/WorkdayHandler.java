package model;

import dao.Repository;
import dao.WorkdayPOJO;
import wrapper.WorkdayWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkdayHandler {
    private static final WorkdayPOJO defaultWorkday = new WorkdayPOJO(null, 800, 1200,
            1300, 1700, 0, 0);
    private WorkdayWrapper currentWorkday = null, nextWorkday = null;

    public WorkdayHandler(Date currentDate, Date nextDate) {
        String currentDateString = new SimpleDateFormat("MM/dd/yyyy").format(currentDate);
        String nextDateString = new SimpleDateFormat("MM/dd/yyyy").format(nextDate);

        Date startDate = new Date(currentDate.getTime() - 86400000L);
        Date endDate = new Date(nextDate.getTime() + 86400000L);

        ArrayList<WorkdayPOJO> workdays = Repository.getInstance().getWorkdays(startDate, endDate);
        for (WorkdayPOJO workday: workdays) {
            WorkdayWrapper temp = new WorkdayWrapper(workday);
            if (temp.getDateString().equals(currentDateString)) {
                currentWorkday = temp;
            } else if (temp.getDateString().equals(nextDateString)) {
                nextWorkday = temp;
            }
        }

        if (currentWorkday == null) {
            currentWorkday = new WorkdayWrapper(defaultWorkday);
            currentWorkday.getWorkday().setDate(currentDate);
        }
        if (nextWorkday == null) {
            nextWorkday = new WorkdayWrapper(defaultWorkday);
            nextWorkday.getWorkday().setDate(nextDate);
        }
    }

    public WorkdayWrapper getCurrentWorkday() {
        return currentWorkday;
    }

    public WorkdayWrapper getNextWorkday() {
        return nextWorkday;
    }

    public void updateCurrentWorkday(WorkdayPOJO workday) {
        currentWorkday = new WorkdayWrapper(workday);
        Repository.getInstance().updateWorkday(workday);
    }

    public void updateNextWorkday(WorkdayPOJO workday) {
        nextWorkday = new WorkdayWrapper(workday);
        Repository.getInstance().updateWorkday(workday);
    }
}
