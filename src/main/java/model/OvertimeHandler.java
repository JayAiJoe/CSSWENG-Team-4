package model;

import dao.LogbookPOJO;

import java.util.ArrayList;
import java.util.Date;

public class OvertimeHandler {
    private Date startDate, endDate;
    private ArrayList<LogbookPOJO> attendance;
    private ArrayList<OvertimeEntry> entries;

    public OvertimeHandler(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendance = new ArrayList<>();
        this.entries = new ArrayList<>();

        initialize();
    }

    private void initialize() {
        // TODO: get pending overtime

        // TODO: set information in entries
    }

    public ArrayList<OvertimeEntry> getEntries() {
        return entries;
    }

    public void save() {
        // TODO: update logbooks
    }
}
