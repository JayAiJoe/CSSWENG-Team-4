package model;

import java.util.ArrayList;

/**
 * This class represents a single range in the PhilHealth fee table that
 * contains the starting salary, ending salary, and the value of the range.
 */
public class PhilHealthRange implements Comparable<PhilHealthRange> {
    public static final String MAX = "1000000.00";

    private String start;
    private String end;
    private String value;
    private boolean initialized;

    /**
     * Constructor for instantiating an uninitialized
     * PhilHealthRange object.
     */
    public PhilHealthRange() {
        this("", "", "");
        initialized = false;
    }

    /**
     * Constructor for instantiating an initialized
     * PhilHealthRange object.
     * @param start the start of the range
     * @param end the end of the range
     * @param value the value for the range
     */
    public PhilHealthRange(String start, String end, String value) {
        this.start = start;
        this.end = end;
        this.value = value;
        initialized = true;
    }

    /**
     * Compares this PhilHealthRange object with another
     * PhilHealthRange object. PhilHealthRange objects are
     * sorted first by start then by end.
     * @param other the other PhilHealthRange object this is
     *              being compared to
     * @return 1 if this should be sorted later and -1 otherwise
     */
    public int compareTo(PhilHealthRange other) {
        if (this.end.equals("MAX")) {
            this.end = MAX;
        }
        if (other.end.equals("MAX")) {
            other.end = MAX;
        }

        double startA = Double.parseDouble(this.start);
        double startB = Double.parseDouble(other.start);
        double endA = Double.parseDouble(this.end);
        double endB = Double.parseDouble(other.end);

        if (startA == startB) {
            if (endA > endB) {
                return 1;
            } else {
                return -1;
            }
        } else if (startA > startB) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Returns the start of the range.
     * @return the start of the range
     */
    public String getStart() {
        return start;
    }

    /**
     * Returns the end of the range.
     * @return the end of the range
     */
    public String getEnd() {
        if (end.equals(MAX)) {
            return "MAX";
        }
        return end;
    }

    /**
     * Returns the value for the range.
     * @return the value for the range
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns whether the range is initialized or not.
     * @return whether the range is initialized or not
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Sets the start of the range.
     * @param start the start of the range
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Sets the end of the range.
     * @param end the end of the range
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Sets the value for the range.
     * @param value the value for the range
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets whether the range is initialized or not
     * @param initialized whether the range is initialized or not
     */
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    /**
     * Returns an ArrayList of doubles that is equivalent
     * to this range.
     * @return an ArrayList of doubles that is equivalent
     * to this range
     */
    public ArrayList<Double> convert() {
        if (end.equals("MAX")) {
            end = MAX;
        }

        ArrayList<Double> result = new ArrayList<>();
        result.add(Double.parseDouble(start));
        result.add(Double.parseDouble(end));
        result.add(Double.parseDouble(value));

        return result;
    }
}