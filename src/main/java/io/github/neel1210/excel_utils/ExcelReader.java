package io.github.neel1210.excel_utils;

import io.github.neel1210.config.AppConfig;
import io.github.neel1210.config.ConfigLoader;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class ExcelReader {
    private final AppConfig.Reader readerConfig = ConfigLoader.getCongifReader();

    private final DataFormatter formatter = new DataFormatter();
    private final Map<String, Integer> columnIndexMap = new HashMap<>();
    private final String fileName = String.format("src/main/resources/files/input/%s.xlsx", readerConfig.getFileName());
    private FormulaEvaluator evaluator;

    //For only read the excel
    public static void main(String[] args) {
        ExcelReader excelReader = new ExcelReader();
        List<Map<String, Object>> list = excelReader.getRowMap();
        list.stream().forEach(row -> System.out.println(row));
    }

    public List<Map<String, Object>> getRowMap() {
        try (FileInputStream fis = new FileInputStream(fileName); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            Row headerRow = sheet.getRow(readerConfig.getStartRow());
            buildHeaderMap(headerRow);
            int totalRows = readerConfig.getTotalRows();
            List<Map<String, Object>> rows = new ArrayList<>();
            for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                rows.add(processRow(row));
            }
            return rows;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildHeaderMap(Row headerRow) {
        for (Cell cell : headerRow) {
            String header = formatter.formatCellValue(cell).trim().replaceAll("\\s+", " ");
            columnIndexMap.put(header, cell.getColumnIndex());
        }
    }

    private Map<String, Object> processRow(Row row) {
        Map<String, Object> record = new LinkedHashMap<>();
        for (String header : readerConfig.getHeader()) {
            String value = getValue(row, header);
            record.put(header, value);
        }
        return record;
    }

    private String getValue(Row row, String headerName) {
        Integer index = columnIndexMap.get(headerName);
        if (index == null) return "";
        Cell cell = row.getCell(index);
        if (cell == null) return "";
        return formatter.formatCellValue(cell, evaluator);
    }
}