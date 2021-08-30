package model;

import java.io.*;

/**
 * This class is responsible for all processes involving the Pag-Ibig Fee.
 * These include reading the formula from a file, writing the formula to
 * a file, and updating the formula.
 */
public class PagIbigFee {
    /** The file name of the binary file used to store the Pag-Ibig Fee formula. */
    private static final String PAG_IBIG_FILE_NAME = "bin/Pag-Ibig.bin";
    /** The total rate of the Pag-Ibig Fee. */
    private double totalRate;
    /** The contribution of the employer for the Pag-Ibig Fee. */
    private double employerContrib;

    /**
     * A constructor for the PagIbigFee that reads the binary
     * file used to store the Pag-Ibig Fee formula.
     */
    public PagIbigFee() {
        initialize();
    }

    /**
     * Returns the total rate of the Pag-Ibig Fee.
     * @return the total rate of the Pag-Ibig Fee
     */
    public double getTotalRate() {
        return totalRate;
    }

    /**
     * Updates the total rate of the Pag-Ibig Fee.
     * @param totalRate the updated total rate of the Pag-Ibig Fee
     */
    public void setTotalRate(double totalRate) {
        this.totalRate = totalRate;
    }

    /**
     * Returns the contribution of the employer for the Pag-Ibig Fee.
     * @return the contribution of the employer for the Pag-Ibig Fee
     */
    public double getEmployerContrib() {
        return employerContrib;
    }

    /**
     * Updates the contribution of the employer for the Pag-Ibig Fee.
     * @param employerContrib the contribution of the employer for the Pag-Ibig Fee
     */
    public void setEmployerContrib(double employerContrib) {
        this.employerContrib = employerContrib;
    }

    /**
     * Reads the binary file where the Pag-Ibig Fee formula is stored
     * and stores the total rate and the employee contribution in the
     * appropriate class variables.
     */
    private void initialize() {
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

        // read formula from file
        try {
            totalRate = dataInputStream.readDouble();
            employerContrib = dataInputStream.readDouble();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // close the file
        try {
            dataInputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Overwrites the binary file where the Pag-Ibig Fee formula is stored
     * and writes the updated total rate and employee contribution to the file.
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
        try {
            dataOutputStream.writeDouble(totalRate);
            dataOutputStream.writeDouble(employerContrib);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // close the file
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
