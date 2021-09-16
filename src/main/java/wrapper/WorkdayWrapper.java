package wrapper;

import dao.WorkdayPOJO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkdayWrapper {
    private WorkdayPOJO workday;

    public WorkdayWrapper(WorkdayPOJO workday) {
        this.workday = workday;
    }

    public WorkdayPOJO getWorkday() {
        return workday;
    }

    public Date getDate() {
        return workday.getDate();
    }

    public int getTimeIn1() {
        return workday.getTimeIn1();
    }

    public int getTimeOut1() {
        return workday.getTimeOut1();
    }

    public int getTimeIn2() {
        return workday.getTimeIn2();
    }

    public int getTimeOut2() {
        return workday.getTimeOut2();
    }

    public int getOvertimeIn() {
        return workday.getOvertimeIn();
    }

    public int getOvertimeOut() {
        return workday.getOvertimeOut();
    }

    public String getDateString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(getDate());
    }

    public String getTimeIn1String() {
        if (getTimeIn1() == 0) {
            return "";
        } else {
            return String.format("%04d", getTimeIn1());
        }
    }

    public String getTimeOut1String() {
        if (getTimeOut1() == 0) {
            return "";
        } else {
            return String.format("%04d", getTimeOut1());
        }
    }

    public String getTimeIn2String() {
        if (getTimeIn2() == 0) {
            return "";
        } else {
            return String.format("%04d", getTimeIn2());
        }
    }

    public String getTimeOut2String() {
        if (getTimeOut2() == 0) {
            return "";
        } else {
            return String.format("%04d", getTimeOut2());
        }
    }

    public String getOvertimeInString() {
        if (getOvertimeIn() == 0) {
            return "";
        } else {
            return String.format("%04d", getOvertimeIn());
        }
    }

    public String getOvertimeOutString() {
        if (getOvertimeOut() == 0) {
            return "";
        } else {
            return String.format("%04d", getOvertimeOut());
        }
    }
}
