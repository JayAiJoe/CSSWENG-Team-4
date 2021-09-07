package wrapper;

import dao.PerformancePOJO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PerformanceWrapper {
    private PerformancePOJO performance;

    public PerformanceWrapper(PerformancePOJO performance) {
        this.performance = performance;
    }

    public int getEmployeeID() {
        return performance.getEmployeeID();
    }

    public String getCompleteName() {
        return performance.getCompleteName();
    }

    public Date getDateStart() {
        return performance.getDateStart();
    }

    public Date getDatePaid() {
        return performance.getDatePaid();
    }

    public String getDateStartString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(getDateStart());
    }

    public String getDatePaidString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(getDatePaid());
    }

    public double getDaysPresent() {
        return performance.getDaysPresent();
    }

    public double getDaysAbsent() {
        return performance.getDaysAbsent();
    }

    public int getMinsOvertime() {
        return performance.getMinsOvertime();
    }

    public int getMinsLate() {
        return performance.getMinsLate();
    }
}
