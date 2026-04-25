package io.github.neel1210.excel_utils;

import io.github.neel1210.config.AppConfig;
import io.github.neel1210.config.ConfigLoader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ExcelWriter {
    private static final AppConfig.Writer writer = ConfigLoader.getCongifWriter();
    private static final List<String> COLUMNS;
    private static final Workbook workbook;
    private static final Sheet sheet;
    private static int rowIndex = 0;

    static {
        COLUMNS = writer.getHeader();
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Result");
    }

    private void createHeader() {
        Row header = sheet.createRow(rowIndex++);
        for (int i = 0; i < COLUMNS.size(); i++) {
            header.createCell(i).setCellValue(COLUMNS.get(i));
        }
    }

    private String getOutputFileName() {
        String baseName = writer.getFileName();
        String action = ConfigLoader.getAppConfig().getReader().getFunctionality();
        ZonedDateTime now = ZonedDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        String time = now.format(DateTimeFormatter.ofPattern("HHmmssSSS"));
        return String.format("src/main/resources/files/output/%s_%s_%s_%s.xlsx", baseName, date, time, action);
    }

    private void writeRow(Object... values) {
        Row row = sheet.createRow(rowIndex++);
        for (int i = 0; i < values.length; i++) {
            row.createCell(i).setCellValue(values[i] == null ? "" : values[i].toString());
        }
    }

    private void writeRowByMap(List<Map<String, Object>> rows) {
        for (Map<String, Object> data : rows) {
            Row row = sheet.createRow(rowIndex++);
            for (int col = 0; col < COLUMNS.size(); col++) {
                String key = COLUMNS.get(col);
                Object value = data.getOrDefault(key, "");
                row.createCell(col).setCellValue(value == null ? "" : value.toString());
            }
        }
    }

    public void save(List<Map<String, Object>> rows) {
        this.createHeader();
        if (rows != null) {
            this.writeRowByMap(rows);
        }
        try (FileOutputStream fos = new FileOutputStream(this.getOutputFileName())) {
            workbook.write(fos);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write Excel file", e);
        }
    }
}