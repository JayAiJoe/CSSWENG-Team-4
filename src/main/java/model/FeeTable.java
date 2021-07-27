package model;

import java.util.ArrayList;

/**
 * This class is responsible for all processes involving the table
 * used in computing for a government fee. These include reading
 * the table from a file, writing the table to a file, and updating
 * the table.
 */
public class FeeTable {
    /** The file name of the binary file where the fee table is stored. */
    private String fileName;
    /**
     * The fee table associated with the government fee. Each row in the table consists
     * of 4 Doubles, the lower_bound, the upper_bound, the rate, and the constant. The
     * range found in each row is [lower_bound, upper_bound) and the formula for this range
     * is rate * salary + constant.
     */
    private ArrayList<ArrayList<Double>> formulas;

    /** The file name of the binary file used to store the PhilHealth fee table. */
    public static String PHILHEALTH_FILE_NAME = "PhilHealth.bin";
    /** The file of the binary file name used to store the Pag-Ibig fee table. */
    public static String PAG_IBIG_FILE_NAME = "Pag-Ibig.bin";
    /** The file of the binary file name used to store the SSS fee table. */
    public static String SSS_FILE_NAME = "SSS.bin";

    /**
     * A constructor for a FeeTable that accepts the file name
     * of the binary file where the fee table is stored as its parameter.
     * @param fileName the file name of the binary file used to store
     *                 the fee table.
     */
    public FeeTable(String fileName) {
        this.fileName = fileName;
        initialize();
    }

    /**
     * Returns the fee table associated with the government fee.
     * @return the fee table associated with the government fee
     */
    public ArrayList<ArrayList<Double>> getFormulas() {
        return formulas;
    }

    /**
     * Updates the fee table associated with the government fee.
     * @param formulas the updated fee table
     */
    public void setFormulas(ArrayList<ArrayList<Double>> formulas) {
        this.formulas = formulas;
    }

    // TODO: Finish implementing the following methods.
    private void initialize() {

    }

    public void close() {

    }
}
