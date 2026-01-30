package io.github.neel1210.excel_utils;

import io.github.neel1210.bussinessLogic.SumPair;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;

public class ReadExcel {

    private final int SHEET_HEADER = 0;
    private final int START_ROW = 3;
    private final int LAST_ROW = 370;
    private String fileName = "src/main/resources/files/input/Practical Performance_A.xlsx";

    private final DataFormatter formatter = new DataFormatter();
    FormulaEvaluator evaluator;
    Map<String, Integer> columnIndexMap = new HashMap<>();
    WriteExcel writer = new WriteExcel();

    public void read() {
        SumPair sumPair = new SumPair();
        SplittableRandom rng = new SplittableRandom();
        try (FileInputStream fis = new FileInputStream(fileName);
             Workbook workbook = new XSSFWorkbook(fis)) {


            Sheet sheet = workbook.getSheetAt(SHEET_HEADER);
            Row headerRow = sheet.getRow(START_ROW);
            evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            for (Cell cell : headerRow) {
                String header = formatter.formatCellValue(cell).trim();
                header = header.replaceAll("\\s+", " "); // normalize spaces
                columnIndexMap.put(header, cell.getColumnIndex());
            }

            System.out.println(columnIndexMap.toString());

            for (int i = START_ROW + 1; i <= LAST_ROW; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String serialNo = display(row.getCell(0), formatter, evaluator);
                if (serialNo.isBlank()) continue;
                String rollNo = display(row.getCell(1), formatter, evaluator);
                String enroll = display(row.getCell(2), formatter, evaluator);
                String name = display(row.getCell(3), formatter, evaluator);
                String co1 = display(row.getCell(8), formatter, evaluator);

                int[] pair;
                if (!co1.isBlank()) {
                    int co1_val = Integer.parseInt(co1.trim());
                    if (co1_val == 10) {
                        pair = sumPair.randomPair(rng.nextInt(19, 21), rng);
                    } else if (co1_val == 9) {
                        pair = sumPair.randomPair(rng.nextInt(17, 19), rng);
                    } else if (co1_val == 8) {
                        pair = sumPair.randomPair(rng.nextInt(15, 17), rng);
                    } else {
                        pair = sumPair.randomPair(rng.nextInt(13, 15), rng);
                    }
                } else {
                    pair = new int[]{0, 0};
                }

                writer.writeRow(
                        serialNo,
                        rollNo,
                        enroll,
                        name,
                        pair[0],
                        pair[1],
                        co1
                );
                System.out.println(serialNo + "\t" + enroll + "\t" + name + "\t" + pair[0] + "\t" + pair[1] + "\t" + co1);
            }
            writer.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String display(Cell cell,
                           DataFormatter formatter,
                           FormulaEvaluator evaluator) {
        if (cell == null) return "";
        return formatter.formatCellValue(cell, evaluator);
    }
}
