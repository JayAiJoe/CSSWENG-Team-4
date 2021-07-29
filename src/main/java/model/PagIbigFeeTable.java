package model;

import java.io.*;
import java.util.ArrayList;

// TODO: Add binary file for Pag-Ibig fee table

/**
 * This class is responsible for all processes involving the table
 * used in computing for the Pag-Ibig Fee. These include reading
 * the table from a file, writing the table to a file, and updating
 * the table.
 */
public class PagIbigFeeTable {
    /** The file of the binary file name used to store the Pag-Ibig fee table. */
    private static final String PAG_IBIG_FILE_NAME = "bin/Pag-Ibig.bin";
    /**
     * The fee table associated with the Pag-Ibig Fee. Each row in the table consists
     * of 3 Doubles, the lower_bound, the upper_bound, the rate. The range found in each row
     * is [lower_bound, upper_bound] and the formula for this range is rate * salary. The
     * exception to this is the last range wherein the third Double found is
     * the exact value of the fee to be paid in the salary range rather than the rate.
     */
    private ArrayList<ArrayList<Double>> formulas;

    /**
     * A constructor for the PagIbigFeeTable that reads the binary
     * file used to store the Pag-Ibig fee table.
     */
    public PagIbigFeeTable() {
        initialize();
    }

    /**
     * Returns the fee table associated with the Pag-Ibig Fee.
     * @return the fee table associated with the Pag-Ibig Fee
     */
    public ArrayList<ArrayList<Double>> getFormulas() {
        return formulas;
    }

    /**
     * Updates the fee table associated with the Pag-Ibig Fee.
     * @param formulas the updated fee table
     */
    public void setFormulas(ArrayList<ArrayList<Double>> formulas) {
        this.formulas = formulas;
    }

    /**
     * Reads the binary file where the Pag-Ibig fee table is stored
     * and stores the contents in the formulas attribute.
     */
    private void initialize() {
        formulas = new ArrayList<>();
        DataInputStream dataInputStream;

        // open the file for reading
        try {
            dataInputStream = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(PAG_IBIG_FILE_NAME)));
        } catch (FileNotFoundException e) {
            System.out.println("File " + PAG_IBIG_FILE_NAME + " not found!");
            return;
        }

        // read fee table from file
        while (true) {
            try {
                ArrayList<Double> range = new ArrayList<>();

                // read new range
                range.add(dataInputStream.readDouble());
                range.add(dataInputStream.readDouble());
                range.add(dataInputStream.readDouble());

                // add new range to formulas
                formulas.add(range);
            } catch (IOException e) {
                // end of file has been reached
                System.out.println(e.getMessage());
                break;
            }
        }

        // close the file
        try {
            dataInputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Overwrites the binary file where the Pag-Ibig fee table is stored
     * and writes the updated fee table to the file.
     */
    public void close() {
        DataOutputStream dataOutputStream;

        // open the file for writing
        try {
            dataOutputStream = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(PAG_IBIG_FILE_NAME, false)));
        } catch (FileNotFoundException e) {
            System.out.println("File " + PAG_IBIG_FILE_NAME + " not found!");
            return;
        }

        // write fee table to file
        for (ArrayList<Double> range: formulas) {
            for (double value: range) {
                try {
                    dataOutputStream.writeDouble(value);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        // close the file
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
