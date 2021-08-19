package model;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.ArrayList;


public class ExcelHandler {


    public ArrayList<ArrayList<Double>> readSSSTable(String filePath) {
        ArrayList<ArrayList<Double>> table = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = "";
            String splitBy = ",";
            br.readLine();
            while((line = br.readLine()) != null){
                ArrayList<Double> range = new ArrayList<>();
                String[] values = line.split(splitBy);
                for (String value: values){
                    range.add(Double.parseDouble(value));
                }
                table.add(range);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    public ArrayList<ArrayList<Double>> readSSSTable2(String filePath) throws IOException{
        ArrayList<ArrayList<Double>> SSSvalues = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(new File(filePath));
        Sheet sheet = workbook.getSheetAt(0);
        int i =0;
        for (Row row: sheet){
            if (i==0){i++;continue;}
            ArrayList<Double> rowValues = new ArrayList<>();
            for (Cell cell: row) {
                rowValues.add(cell.getNumericCellValue());
            }
            SSSvalues.add(rowValues);
        }

        return SSSvalues;
    }

    public void writeSSS(String filePath) throws IOException {

        Workbook workbook = new HSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        String[] columns = {"Lower","Upper","Compensation","Fee"};
        int[] values = {1,2,5,6};

        Sheet sheet = workbook.createSheet("Ranges");

        Row headers = sheet.createRow(0);
        for (int i=0; i<4; i++){
            Cell cell = headers.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowNum = 1;
        for (int i = 0;i<3;i++)
        {
            Row row = sheet.createRow(rowNum++);
            for(int j = 0;j<4;j++ ){
                row.createCell(j).setCellValue(i+values[j]);
            }
        }

        for (int i =0; i<4;i++){
            sheet.autoSizeColumn(i);
        }
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:B2"));

        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
}
