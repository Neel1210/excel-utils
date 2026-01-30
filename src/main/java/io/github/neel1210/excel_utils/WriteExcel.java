package io.github.neel1210.excel_utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteExcel {
    private static final String OUTPUT_FILE = "src/main/resources/files/output/CO-1.xlsx";
    private final List<String> columns = List.of("S.no", "Roll No", "Enrollment No", "Name of Students", "P1", "P2", "CO-1");
    private final Workbook workbook = new XSSFWorkbook();
    private final Sheet sheet = workbook.createSheet("Result");
    private int rowIndex = 0;

    public WriteExcel() {
        createHeader();
    }

    private void createHeader() {
        Row header = sheet.createRow(rowIndex++);
        for (int i = 0; i < columns.size(); i++) {
            header.createCell(i).setCellValue(columns.get(i));
        }
    }

    public void writeRow(Object... values) {
        Row row = sheet.createRow(rowIndex++);
        for (int i = 0; i < values.length; i++) {
            row.createCell(i).setCellValue(
                    values[i] == null ? "" : values[i].toString()
            );
        }
    }

    public void save() {
        try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE)) {
            workbook.write(fos);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write Excel file", e);
        }
    }

}
