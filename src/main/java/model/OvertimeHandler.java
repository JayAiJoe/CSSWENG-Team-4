package model;

import dao.LogbookPOJO;
import dao.Repository;

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
        attendance = Repository.getInstance().getPendingOT(startDate, endDate);

        for (LogbookPOJO logbook: attendance) {
            entries.add(new OvertimeEntry(logbook.getCompleteName(), logbook.getPendingOT(), logbook.getDate()));
        }
    }

    public ArrayList<OvertimeEntry> getEntries() {
        return entries;
    }

    public void save(ArrayList<OvertimeEntry> finalEntries) {
        ArrayList<LogbookPOJO> saveEntries = new ArrayList<>();
        for (LogbookPOJO logbook: attendance) {
            String name = logbook.getCompleteName();
            Date date = logbook.getDate();
            int minutes = logbook.getApprovedOT();

            // find entry corresponding to logbook
            for (OvertimeEntry entry: finalEntries) {
                if (entry.getEmployeeName().equals(name) && entry.getDate().equals(date)) {
                    if (entry.getStatus()) { // pendingOT is accepted
                        logbook.setApprovedOT(minutes + entry.getMinutes());
                    }
                    logbook.setPendingOT(0);
                    saveEntries.add(logbook);
                    break;
                }
            }
        }

        attendance.removeAll(saveEntries);
        Repository.getInstance().updateLogbookOT(saveEntries);
    }
}
