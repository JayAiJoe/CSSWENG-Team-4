package dao;

import java.util.Date;

public class PayrollPOJO {
    private Date startDate;
    private Date endDate;
    private String frequency;

    public PayrollPOJO(Date startDate, Date endDate, String frequency) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
    }

    public PayrollPOJO() {}

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
