package io.github.neel1210;

import io.github.neel1210.config.AppConfig;
import io.github.neel1210.config.ConfigLoader;
import io.github.neel1210.evaluators.Evaluator;
import io.github.neel1210.evaluators.EvaluatorFactory;
import io.github.neel1210.excel_utils.ExcelReader;
import io.github.neel1210.excel_utils.ExcelWriter;

import java.util.List;
import java.util.Map;

import static io.github.neel1210.config.ConfigLoader.getExcelReader;
import static io.github.neel1210.config.ConfigLoader.getExcelWriter;

public class Main {
    public static void main(String[] args) {
        ExcelReader excelReader = getExcelReader();
        AppConfig.Reader input = ConfigLoader.getAppConfig().getReader();

        //Read Rows
        List<Map<String, Object>> rows = excelReader.getRowMap();
        rows.stream().forEach(row -> System.out.println(row));

        //Evaluating rows
        Evaluator evaluator = EvaluatorFactory.get(input.getFunctionality());
        List<Map<String, Object>> output = rows.stream().map(evaluator::evaluate).toList();
        output.stream().forEach(row -> System.out.println(row));

        //Writing the rows
        ExcelWriter excelWriter = getExcelWriter();
        excelWriter.save(output);
    }
}