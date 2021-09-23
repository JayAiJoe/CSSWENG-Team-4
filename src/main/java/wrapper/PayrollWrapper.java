package wrapper;

import dao.PayrollPOJO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PayrollWrapper implements Comparable<PayrollWrapper> {
    private PayrollPOJO payroll;

    public PayrollWrapper(PayrollPOJO payroll) {
        this.payroll = payroll;
    }

    public Date getStartDate() {
        return payroll.getStartDate();
    }

    public Date getEndDate() {
        return payroll.getEndDate();
    }

    public String getFrequency() {
        return payroll.getFrequency();
    }

    public String getStartDateString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(getStartDate());
    }

    public String getEndDateString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(getEndDate());
    }

    @Override
    public int compareTo(PayrollWrapper other) {
        return other.getStartDate().compareTo(this.getStartDate());
    }
}
