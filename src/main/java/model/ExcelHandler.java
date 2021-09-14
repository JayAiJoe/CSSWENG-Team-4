package model;

import dao.EmployeePOJO;
import dao.LogbookPOJO;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public ArrayList<ArrayList<Double>> readSSSTable2(File file) throws IOException, IllegalStateException{
        ArrayList<ArrayList<Double>> SSSvalues = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        int i =0;
        for (Row row: sheet){
            if (i==0){i++;continue;}
            ArrayList<Double> rowValues = new ArrayList<>();
            for (Cell cell: row) {
                if (cell.getNumericCellValue() < 0) {
                    throw new IllegalStateException();
                }
                rowValues.add(cell.getNumericCellValue());
            }
            SSSvalues.add(rowValues);
        }

        return SSSvalues;
    }

    public ArrayList<EmployeePOJO> readEmployees(File file) throws IOException, ParseException {
        ArrayList<EmployeePOJO> employees = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        int i=0;

        /*
            Format of the Excel file should follow the employeePOJO constructor of employeeID, employeeName, company, wage, mode of payment, frequency of payment,
            debt amount, date joined and date left.
         */
        for (Row row: sheet){
            if (i==0){i++; continue;}
            ArrayList<String> rowValues = new ArrayList<>();
            for(Cell cell: row){
                rowValues.add(cell.getStringCellValue());
            }
            EmployeePOJO newEmployee = new EmployeePOJO(Integer.parseInt(rowValues.get(0)),rowValues.get(1),rowValues.get(2),Double.parseDouble(rowValues.get(3)),
                                                        rowValues.get(4),rowValues.get(5),Double.parseDouble(rowValues.get(6)),formatter.parse(rowValues.get(7)),
                                                        formatter.parse(rowValues.get(8)));
            employees.add(newEmployee);
        }

        return employees;
    }

    public ArrayList<LogbookPOJO> readLogbook(File file) throws IOException, ParseException {
        ArrayList<LogbookPOJO> logbook = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Workbook workbook = WorkbookFactory.create(file);
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
                                LogbookPOJO entry = new LogbookPOJO(ID, name, new Date(value.getTime() + 8 * 3600000L), 30.0, times[0], times[1], times[2], times[3]);
                                logbook.add(entry);
                            }
                        } else {
                            LogbookPOJO entry = new LogbookPOJO(ID, name, new Date(value.getTime() + 8 * 3600000L), 30.0, times[0], times[1], times[2], times[3]);
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

    public void printMD(String filePath, ObservableList<PayrollEntry> payrollEntries, String dateStart, String dateEnd, String company) throws IOException {

        Workbook workbook = new HSSFWorkbook();

        String[] columns = {"NAME OF EMPLOYEE", "DAYS OF WORK", "RATE", "TOTAL REGULAR WAGE", "OVERTIME","", "COLA", "TOTAL AMOUNT","DEDUCTIONS","","","","","NET AMOUNT PAID"};
        Font headersFont = workbook.createFont();
        headersFont.setBold(true);
        CellStyle headerCellStyle = workbook.createCellStyle();
        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setFont(headersFont);
        headerCellStyle.setFont(headersFont);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerCellStyle.setBorderTop(BorderStyle.THICK);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.DASH_DOT);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Sheet sheet = workbook.createSheet(company+" Payroll");
        Row row = sheet.createRow(0);
        for(int i = 0; i<14;i++){
            Cell cell = row.createCell(i);
            switch (i){
                case 6:
                    cell.setCellValue("For the period of");
                    break;
                case 8:
                    cell.setCellValue(dateStart);
                    cell.setCellStyle(boldStyle);
                    break;
                case 10:
                    if (!dateStart.equals("13th month")) {
                        cell.setCellValue("to");
                    }
                    break;
                case 12:
                    if (!dateStart.equals("13th month")) {
                        cell.setCellValue(dateEnd);
                        cell.setCellStyle(boldStyle);
                    }
                    break;
            }
        }
        sheet.addMergedRegion(CellRangeAddress.valueOf("G1:H1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("I1:J1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("M1:N1"));
        Row row2 = sheet.createRow(1);
        for(int i = 0; i<14;i++){
            Cell cell = row2.createCell(i);
            switch (i){
                case 0:
                    cell.setCellValue("WE HEREBY ACKNOWLEDGE to have received from");
                    break;
                case 3:
                    if (company.equals("CRAYOLA")) {
                        cell.setCellValue("CRAYOLA ATBP.");
                        cell.setCellStyle(boldStyle);
                    }
                    else {
                        cell.setCellValue("IX-XI HARDWARE");
                        cell.setCellStyle(boldStyle);
                    }
                    break;
                case 7:
                    cell.setCellValue("located at");
                    break;
                case 8:
                    if (company.equals("CRAYOLA")) {
                        cell.setCellValue("UNIT 2-3, U&I BLDG., F. TAÑEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
                        cell.setCellStyle(boldStyle);
                    }
                    else {
                        cell.setCellValue("UNIT 5, U&I BLDG., F. TAÑEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
                        cell.setCellStyle(boldStyle);
                    }
                    break;
            }
        }
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:C2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("D2:F2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("I2:N2"));
        Row row3 = sheet.createRow(2);
        Cell cellValue = row3.createCell(0);
        cellValue.setCellValue("the sum specified opposite our respective name, as full compensation for our service rendered.");
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:M3"));

        Row headers = sheet.createRow(3);
        for (int i=0; i<14; i++){
            Cell cell = headers.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        Row headers2 = sheet.createRow(4);
        for (int i=0; i<14; i++){
            Cell cell = headers2.createCell(i);
            if (i == 4){
                cell.setCellValue("REGULAR/HOLIDAY");
            }
            cell.setCellStyle(headerCellStyle);
        }
        Row headers3 = sheet.createRow(5);
        for (int i=0; i<14; i++){
            Cell cell = headers3.createCell(i);
            switch (i){
                case 4:
                    cell.setCellValue("HRS. minutes");
                    break;
                case 5:
                    cell.setCellValue("AMOUNT");
                    break;
                case 8:
                    cell.setCellValue("S.S.S");
                    break;
                case 9:
                    cell.setCellValue("PHILHEALTH");
                    break;
                case 10:
                    cell.setCellValue("PAG-IBIG FUND");
                    break;
                case 11:
                    cell.setCellValue("TAX W/HELD");
                    break;
                case 12:
                    cell.setCellValue("LATE/UT");
                    break;
            }
            cell.setCellStyle(headerCellStyle);
        }
        sheet.addMergedRegion(CellRangeAddress.valueOf("A4:A6"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("B4:B6"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("C4:C6"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("D4:D6"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("E4:F4"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("E5:F5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("G4:G6"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("H4:H6"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("I4:M5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("N4:N6"));

        int rowNum = 6;
        for (PayrollEntry employee: payrollEntries)
        {
            Row contents = sheet.createRow(rowNum++);
            for(int j = 0;j<14;j++ ){
                Cell cell = contents.createCell(j);
                switch (j){
                    case 0:
                        cell.setCellValue(employee.getEmployeeName());
                        break;
                    case 1:
                        cell.setCellValue(employee.getWorkdays());
                        break;
                    case 2:
                        cell.setCellValue(employee.getRate());
                        break;
                    case 3:
                        cell.setCellValue(employee.getSalary());
                        break;
                    case 4:
                        cell.setCellValue(employee.getTime());
                        break;
                    case 5:
                        cell.setCellValue(employee.getAmount());
                        break;
                    case 6:
                        cell.setCellValue(employee.getCola());
                        break;
                    case 7:
                        cell.setCellValue(employee.getTotal());
                        break;
                    case 8:
                        cell.setCellValue(employee.getSss());
                        break;
                    case 9:
                        cell.setCellValue(employee.getPhilhealth());
                        break;
                    case 10:
                        cell.setCellValue(employee.getPagibig());
                        break;
                    case 11:
                        cell.setCellValue("");
                        break;
                    case 12:
                        cell.setCellValue(employee.getLate());
                        break;
                    case 13:
                        cell.setCellValue(employee.getNet());
                        break;
                }
                cell.setCellStyle(cellStyle);
            }
        }

        for (int i =0; i<4;i++){
            sheet.autoSizeColumn(i,true);
        }

        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    public void printVoucher(String filePath , ObservableList<PayrollEntry> entries, String dateStart, String dateEnd) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        DecimalFormat df = new DecimalFormat("0.00");
        Sheet sheet = workbook.createSheet("Vouchers");
        int rowNum = 2;

        Font headersFont = workbook.createFont();
        headersFont.setBold(true);
        CellStyle bold = workbook.createCellStyle();
        bold.setFont(headersFont);


        for(PayrollEntry employee: entries){
            Row row = sheet.createRow(rowNum);
            Cell cell = row.createCell(0);
            cell.setCellValue(employee.getEmployeeName().toUpperCase());
            cell.setCellStyle(bold);

            rowNum+=3;

            Row row2 = sheet.createRow(rowNum++);
            row2.createCell(0).setCellValue("DATE COVERED: ");
            Cell cell2 = row2.createCell(1);
            if (!dateStart.equals("13th month")) {
                cell2.setCellValue(dateStart + " - " + dateEnd);
            }
            else {
                cell2.setCellValue(dateStart);
            }
            cell2.setCellStyle(bold);

            row2.createCell(4).setCellValue("LESS DEDUCTIONS: ");


            Row row3 = sheet.createRow(rowNum++);
            row3.createCell(0).setCellValue("DAYS OF WORK: ");
            row3.createCell(1).setCellValue(employee.getWorkdays());

            row3.createCell(4).setCellValue("SSS: ");
            row3.createCell(5).setCellValue(employee.getSss());


            Row row4 = sheet.createRow(rowNum++);
            row4.createCell(0).setCellValue("RATE ("+employee.getMode()+")");
            row4.createCell(1).setCellValue(employee.getWage());

            row4.createCell(4).setCellValue("PHILHEALTH: ");
            row4.createCell(5).setCellValue(employee.getPhilhealth());

            Row row5 = sheet.createRow(rowNum++);
            row5.createCell(0).setCellValue("UNIFORM 10/DAY: ");
            row5.createCell(1).setCellValue(employee.getCola());

            row5.createCell(4).setCellValue("PAG-IBIG: ");
            row5.createCell(5).setCellValue(employee.getPagibig());

            Row row6 = sheet.createRow(rowNum++);
            row6.createCell(0).setCellValue("OVERTIME: ");
            row6.createCell(1).setCellValue(employee.getAmount());

            row6.createCell(4).setCellValue("LATE/UNDERTIME: ");
            row6.createCell(5).setCellValue(employee.getLate());

            Row row7 = sheet.createRow(rowNum++);
            row7.createCell(0).setCellValue("TOTAL REGULAR WAGE: ");
            double regularWage = Double.parseDouble(employee.getRate())*(Double.parseDouble(employee.getWorkdays())+Double.parseDouble(employee.getAbsent()));
            row7.createCell(1).setCellValue(df.format(regularWage));


            row7.createCell(4).setCellValue("ABSENT "+ Double.parseDouble(employee.getAbsent()) +" DAYS");
            double absentVal = Double.parseDouble(employee.getAbsent())*Double.parseDouble(employee.getRate());
            row7.createCell(5).setCellValue(df.format(absentVal));

            Row row8 = sheet.createRow(rowNum);
            row8.createCell(4).setCellValue("TOTAL DEDUCTIONS: ");
            double totalDeduction = Double.parseDouble(employee.getTotal())-Double.parseDouble(employee.getNet())+absentVal;
            row8.createCell(5).setCellValue(df.format(totalDeduction));

            rowNum+=2;
            Row row9 = sheet.createRow(rowNum);
            Cell cell3 = row9.createCell(4);
            cell3.setCellValue("NET AMOUNT PAID: ");
            cell3.setCellStyle(bold);

            Cell cell4 = row9.createCell(6);
            cell4.setCellValue(employee.getNet());
            cell4.setCellStyle(bold);

            rowNum+=9;
        }

        for (int i =0; i<7;i++){
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

}
