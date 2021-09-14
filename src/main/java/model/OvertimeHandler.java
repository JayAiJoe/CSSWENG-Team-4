package model;

import dao.LogbookPOJO;
import dao.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class OvertimeHandler {
    private Date startDate, endDate;
    private ArrayList<LogbookPOJO> attendance;
    private ArrayList<OvertimeEntry> pendingEntries, acceptedEntries;

    public OvertimeHandler(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendance = new ArrayList<>();
        this.pendingEntries = new ArrayList<>();
        this.acceptedEntries = new ArrayList<>();

        initialize();
    }

    private void initialize() {
        attendance = Repository.getInstance().getPendingOT(startDate, endDate);

        for (LogbookPOJO logbook: attendance) {
            pendingEntries.add(new OvertimeEntry(logbook.getCompleteName(), logbook.getPendingOT(), logbook.getDate()));
        }
        for (LogbookPOJO logbook: Repository.getInstance().getAcceptedOT(startDate, endDate)) {
            acceptedEntries.add(new OvertimeEntry(logbook.getCompleteName(), logbook.getApprovedOT(), logbook.getDate()));
        }
        Collections.reverse(acceptedEntries);
    }

    public ArrayList<OvertimeEntry> getPendingEntries() {
        return pendingEntries;
    }

    public ArrayList<OvertimeEntry> getAcceptedEntries() {
        return acceptedEntries;
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

                    if (entry.getStatus()) {
                        boolean add = true;
                        for (OvertimeEntry accepted: acceptedEntries) {
                            if (accepted.getEmployeeName().equals(entry.getEmployeeName()) &&
                                accepted.getDateString().equals(entry.getDateString())) {
                                add = false;
                                accepted.setMinutes(entry.getMinutes());
                                break;
                            }
                        }
                        if (add) {
                            acceptedEntries.add(entry);
                        }
                    }
                    break;
                }
            }
        }

        attendance.removeAll(saveEntries);
        Repository.getInstance().updateLogbookOT(saveEntries);
    }
}
