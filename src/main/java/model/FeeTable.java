package model;

import java.io.*;
import java.util.ArrayList;

/**
 * This class is responsible for all processes involving the table
 * used in computing for government fees. These include reading
 * the table from a file, writing the table to a file, and updating
 * the table.
 */
public class FeeTable {
    /** The file name of the binary file used to store the fee table. */
    private String fileName;
    /**
     * The fee table associated with a government fee. Each row in the table consists
     * of 3 Doubles, the lower_bound, the upper_bound, the rate/value. The range found in each
     * row is [lower_bound, upper_bound] and the formula for this range is rate * salary or value.
     */
    private ArrayList<ArrayList<Double>> formulas;

    /** The file name of the binary file used to store the PhilHealth fee table. */
    public static final String PHILHEALTH_FILE_NAME = "bin/PhilHealth.bin";
    /** The file name of the binary file used to store the SSS fee table. */
    public static final String SSS_FILE_NAME = "bin/SSS.bin";

    /**
     * A constructor for the FeeTable that reads the binary
     * file used to store a fee table.
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

    /**
     * Reads the binary file where the government fee table is stored
     * and stores the contents in the formulas attribute.
     */
    private void initialize() {
        formulas = new ArrayList<>();
        DataInputStream dataInputStream;

        // open the file for reading
        try {
            dataInputStream = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found!");
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
                System.out.println("End of file has been reached.");
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
     * Overwrites the binary file where the government fee table is stored
     * and writes the updated fee table to the file.
     */
    public void close() {
        DataOutputStream dataOutputStream;

        // open the file for writing
        try {
            dataOutputStream = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(fileName, false)));
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found!");
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
