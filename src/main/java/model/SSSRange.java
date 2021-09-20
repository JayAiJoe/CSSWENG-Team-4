package model;

import java.util.ArrayList;

/**
 * This class represents a single range in the SSS fee table that
 * contains the starting salary, ending salary, and the value of the range.
 */
public class SSSRange implements Comparable<SSSRange> {
    public static final String MAX = "1000000.00";

    private String start;
    private String end;
    private String compensation;
    private String value;

    /**
     * Constructor for instantiating an uninitialized
     * SSSRange object.
     */
    public SSSRange() {
        this("", "", "", "");
    }

    /**
     * Constructor for instantiating an initialized
     * PhilHealthRange object.
     * @param start the start of the range
     * @param end the end of the range
     * @param compensation the compensation of the range
     * @param value the value for the range
     */
    public SSSRange(String start, String end, String compensation, String value) {
        this.start = start;
        this.end = end;
        this.compensation = compensation;
        this.value = value;
        if (end.equals("PhP " + MAX)) {
            this.end = "MAX";
        }
    }

    /**
     * Compares this SSSRange object with another
     * SSSRange object. SSSRange objects are
     * sorted first by start then by end.
     * @param other the other SSSRange object this is
     *              being compared to
     * @return 1 if this should be sorted later and -1 otherwise
     */
    public int compareTo(SSSRange other) {
        ArrayList<Double> rangeA = this.convert();
        ArrayList<Double> rangeB = other.convert();

        double startA = rangeA.get(0);
        double startB = rangeB.get(0);
        double endA = rangeA.get(1);
        double endB = rangeB.get(1);

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
     * Returns the compensation for the range.
     * @return the compensation for the range
     */
    public String getCompensation() {
        return compensation;
    }

    /**
     * Returns the value for the range.
     * @return the value for the range
     */
    public String getValue() {
        return value;
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
     * Sets the compensation for the range.
     * @param compensation the compensation for the range
     */
    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    /**
     * Sets the value for the range.
     * @param value the value for the range
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns an ArrayList of doubles that is equivalent
     * to this range.
     * @return an ArrayList of doubles that is equivalent
     * to this range
     */
    public ArrayList<Double> convert() {
        String start, end, compensation, value;

        start = this.start.substring(4);
        if (this.end.equals("MAX")) {
            end = MAX;
        } else {
            end = this.end.substring(4);
        }
        compensation = this.compensation.substring(4);
        value = this.value.substring(4);

        ArrayList<Double> result = new ArrayList<>();
        result.add(Double.parseDouble(start));
        result.add(Double.parseDouble(end));
        result.add(Double.parseDouble(compensation));
        result.add(Double.parseDouble(value));

        return result;
    }
}