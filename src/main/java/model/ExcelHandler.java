package model;

import org.apache.commons.math3.analysis.function.Log;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;


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

    public ArrayList<LogbookPOJO> readLogbook(String filePath) throws IOException, ParseException {
        ArrayList<LogbookPOJO> logbook = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Workbook workbook = WorkbookFactory.create(new File(filePath));
        DataFormatter dataFormatter = new DataFormatter();
        int sheetIndex = 4;
        String name;
        String date;
        String month;
        int  ID;
        int[] add = {2,3,2,1};
        Sheet sheet = null;
        //System.out.println(workbook.getSheetIndex(sheet));
        while (sheetIndex < workbook.getNumberOfSheets()) {
            sheet = workbook.getSheetAt(sheetIndex);
            int columnIndex = 0;
            int nameColumnIndex = 9;
            int dateColumnIndex = 1;
            int rowNumber = 11;
            while ((sheet.getRow(2).getCell(columnIndex) != null) && (!Objects.equals(sheet.getRow(2).getCell(columnIndex + 1).getStringCellValue(), ""))) {
                Row row = sheet.getRow(2);
                Cell cell = row.getCell(nameColumnIndex);
                name = cell.getStringCellValue();
                //System.out.println(name);
                row = sheet.getRow(3);
                cell = row.getCell(dateColumnIndex);
                date = dataFormatter.formatCellValue(cell);
                month = date.substring(5, 7);
                ID = Integer.parseInt(row.getCell(nameColumnIndex).getStringCellValue());
                Date value = formatter.parse(date);
                Date prevValue = value;
                while (!Objects.equals(row.getCell(columnIndex + 1).getStringCellValue(), "")) {
                    row = sheet.getRow(rowNumber);
                    String time;
                    int[] times = {0, 0, 0, 0};
                    int j = 0;
                    if (Objects.equals(row.getCell(columnIndex).getStringCellValue(), "")) {
                        columnIndex += 15;
                        nameColumnIndex += 15;
                        dateColumnIndex += 15;
                        rowNumber = 11;
                        break;
                    } else {
                        String day = row.getCell(columnIndex).getStringCellValue().substring(0, 2);
                        prevValue.setDate(Integer.parseInt(day));
                        if (prevValue.before(value)) {
                            value.setMonth(Integer.parseInt(month) + 1);
                        }
                        value.setDate(Integer.parseInt(day));
                        cell = row.getCell(columnIndex + 1);
                        if (!cell.getStringCellValue().equals("Absent")) {
                            for (int i = columnIndex + 1; i < columnIndex + 9;) {
                                cell = row.getCell(i);
                                if (Objects.equals(cell.getStringCellValue(), "")) {
                                    if ((i == columnIndex + 4) & (times[2] != 0)) {
                                        time = "1700";
                                    } else {
                                        time = "-1";
                                    }
                                } else {
                                    time = cell.getStringCellValue().substring(0, 2) + cell.getStringCellValue().substring(3, 5);
                                }
                                times[j] = Integer.parseInt(time);
                                i += add[j];
                                j++;
                            }
                            if ((times[0] != -1) | (times[2] != -1)) {
                                for (int k = 0; k < 4; k++) {
                                    if (times[k] == -1) {
                                        times[k] = 0;
                                    }
                                }
                                LogbookPOJO entry = new LogbookPOJO(ID, name, value, 30.0, times[0], times[1], times[2], times[3]);
                                logbook.add(entry);
                            }
                        } else {
                            LogbookPOJO entry = new LogbookPOJO(ID, name, value, 30.0, times[0], times[1], times[2], times[3]);
                            logbook.add(entry);
                        }
                        rowNumber++;
                    }
                }

            }
            sheetIndex++;
        }
        return logbook;
    }

    public void readWorkdays(String filePath) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));


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
