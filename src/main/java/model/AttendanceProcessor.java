package model;

import dao.LogbookPOJO;
import dao.Repository;
import dao.WorkdayPOJO;

import java.util.ArrayList;
import java.util.Date;

public class AttendanceProcessor {
    public AttendanceProcessor(ArrayList<LogbookPOJO> logbooks) {
        // set start and end date
        Date startDate = logbooks.get(0).getDate();
        Date endDate = startDate;
        for (LogbookPOJO logbook: logbooks) {
            if (startDate.compareTo(logbook.getDate()) > 0) {
                startDate = logbook.getDate();
            }
            if (endDate.compareTo(logbook.getDate()) < 0) {
                endDate = logbook.getDate();
            }
        }
        startDate.setHours(0);
        startDate.setMinutes(0);
        endDate.setHours(23);
        endDate.setMinutes(59);

        ArrayList<WorkdayPOJO> workdays = Repository.getInstance().getWorkdays(startDate, endDate);

        /* assumptions:
           1. logbooks is arranged by employee, then by date
           2. workdays is arranged by date
           3. the number of logbooks per employee is equal to the number of workdays
         */
        int logbookCtr = 0;
        while (logbookCtr < logbooks.size()) {
            for (int i = 0; i < workdays.size() && logbookCtr < logbooks.size(); i++) {
                LogbookPOJO logbook = logbooks.get(logbookCtr);
                WorkdayPOJO workday = workdays.get(i);

                processLogbook(logbook, workday);

                logbookCtr++;
            }
        }
        Repository.getInstance().addLogBook(logbooks);
    }

    private void processLogbook(LogbookPOJO logbook, WorkdayPOJO workday) {
        if (logbook.getTimeIn1() != 0 && logbook.getTimeOut1() != 0 && // present whole day
            logbook.getTimeIn2() != 0 && logbook.getTimeOut2() != 0) {
            int timeIn1 = Math.max(workday.getTimeIn1(), logbook.getTimeIn1());
            int timeOut1 = logbook.getTimeOut1();
            int timeIn2 = logbook.getTimeIn2();
            int timeOut2 = Math.min(workday.getTimeOut2(), logbook.getTimeOut2());
            timeIn1 = timeIn1 / 100 * 60 + timeIn1 % 100;
            timeOut1 = timeOut1 / 100 * 60 + timeOut1 % 100;
            timeIn2 = timeIn2 / 100 * 60 + timeIn2 % 100;
            timeOut2 = timeOut2 / 100 * 60 + timeOut2 % 100;

            // set minsLate
            int worktime = timeOut1 - timeIn1 + timeOut2 - timeIn2;
            logbook.setMinsLate(Math.max(0, 480 - worktime));

            // set overtime
            int approvedOT = 0, pendingOT = 0;
            if (workday.getOvertimeIn() != 0) {
                int overtimeIn = Math.max(workday.getOvertimeIn(), logbook.getTimeIn1());
                timeIn1 = workday.getTimeIn1();
                overtimeIn = overtimeIn / 100 * 60 + overtimeIn % 100;
                timeIn1 = timeIn1 / 100 * 60 + timeIn1 % 100;

                approvedOT += Math.max(0, timeIn1 - overtimeIn);
            }

            int overtimeOut;
            if (workday.getOvertimeOut() != 0) {
                overtimeOut = Math.min(workday.getOvertimeOut(), logbook.getTimeOut2());
                timeOut2 = workday.getTimeOut2();
                overtimeOut = overtimeOut / 100 * 60 + overtimeOut % 100;
                timeOut2 = timeOut2 / 100 * 60 + timeOut2 % 100;

                approvedOT += Math.max(0, overtimeOut - timeOut2);

                timeOut2 = logbook.getTimeOut2();
                overtimeOut = workday.getOvertimeOut();
                timeOut2 = timeOut2 / 100 * 60 + timeOut2 % 100;
                overtimeOut = overtimeOut / 100 * 60 + overtimeOut % 100;
            } else {
                overtimeOut = workday.getTimeOut2();
                timeOut2 = logbook.getTimeOut2();
                overtimeOut = overtimeOut / 100 * 60 + overtimeOut % 100;
                timeOut2 = timeOut2 / 100 * 60 + timeOut2 % 100;
            }
            pendingOT += Math.max(0, timeOut2 - overtimeOut);

            logbook.setApprovedOT(approvedOT);
            logbook.setPendingOT(pendingOT);
        } else if (logbook.getTimeIn1() != 0 && logbook.getTimeOut1() != 0 && // present first shift
            logbook.getTimeIn2() == 0 && logbook.getTimeOut2() == 0) {
            int timeIn1 = Math.max(workday.getTimeIn1(), logbook.getTimeIn1());
            int timeOut1 = Math.min(workday.getTimeOut1(), logbook.getTimeOut1());
            timeIn1 = timeIn1 / 100 * 60 + timeIn1 % 100;
            timeOut1 = timeOut1 / 100 * 60 + timeOut1 % 100;

            // set minsLate
            int worktime = timeOut1 - timeIn1;
            logbook.setMinsLate(Math.max(0, 240 - worktime));

            // set overtime
            if (workday.getOvertimeIn() != 0) {
                int overtimeIn = Math.max(workday.getOvertimeIn(), logbook.getTimeIn1());
                timeIn1 = workday.getTimeIn1();
                overtimeIn = overtimeIn / 100 * 60 + overtimeIn % 100;
                timeIn1 = timeIn1 / 100 * 60 + timeIn1 % 100;

                logbook.setApprovedOT(Math.max(0, timeIn1 - overtimeIn));
            }
            int overtimeOut = workday.getTimeOut1();
            timeOut1 = logbook.getTimeOut1();
            overtimeOut = overtimeOut / 100 * 60 + overtimeOut % 100;
            timeOut1 = timeOut1 / 100 * 60 + timeOut1 % 100;

            logbook.setPendingOT(Math.max(0, timeOut1 - overtimeOut));
        } else if (logbook.getTimeIn1() == 0 && logbook.getTimeOut1() == 0 && // present second shift
            logbook.getTimeIn2() != 0 && logbook.getTimeOut2() != 0) {
            int timeIn2 = Math.max(workday.getTimeIn2(), logbook.getTimeIn2());
            int timeOut2 = Math.min(workday.getTimeOut2(), logbook.getTimeOut2());
            timeIn2 = timeIn2 / 100 * 60 + timeIn2 % 100;
            timeOut2 = timeOut2 / 100 * 60 + timeOut2 % 100;

            // set minsLate
            int worktime = timeOut2 - timeIn2;
            logbook.setMinsLate(Math.max(0, 240 - worktime));

            // set overtime
            int overtimeOut;
            if (workday.getOvertimeOut() != 0) {
                overtimeOut = Math.min(workday.getOvertimeOut(), logbook.getTimeOut2());
                timeOut2 = workday.getTimeOut2();
                overtimeOut = overtimeOut / 100 * 60 + overtimeOut % 100;
                timeOut2 = timeOut2 / 100 * 60 + timeOut2 % 100;

                logbook.setApprovedOT(Math.max(0, overtimeOut - timeOut2));

                timeOut2 = logbook.getTimeOut2();
                overtimeOut = workday.getOvertimeOut();
                timeOut2 = timeOut2 / 100 * 60 + timeOut2 % 100;
                overtimeOut = overtimeOut / 100 * 60 + overtimeOut % 100;
            } else {
                overtimeOut = workday.getTimeOut2();
                timeOut2 = logbook.getTimeOut2();
                overtimeOut = overtimeOut / 100 * 60 + overtimeOut % 100;
                timeOut2 = timeOut2 / 100 * 60 + timeOut2 % 100;
            }
            logbook.setPendingOT(Math.max(0, timeOut2 - overtimeOut));
        }
    }
}
