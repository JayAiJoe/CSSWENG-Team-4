package model;

import java.util.ArrayList;

// TODO: Implement initialize() method

/**
 * This class represents a payroll on a given payday
 * consisting of payroll entries. This class is responsible
 * for adding entries and making method calls that perform
 * different calculations.
 */
public class Payroll {
    private ArrayList<PayrollEntry> entries;

    public Payroll() {
        initialize();
    }

    public ArrayList<PayrollEntry> getEntries() {
        return entries;
    }

    private void initialize() {
        entries = new ArrayList<>();
        entries.add(new PayrollEntry("a", "b", 10, 5,
                    1, 1,1,1,1,1,1,1,1));
        entries.add(new PayrollEntry("z", "b", 10, 5,
                1, 1,4,2,1,1,1,1,1));
    }
}
