package io.github.neel1210.config;

import io.github.neel1210.calculate.SumPairForCo;
import io.github.neel1210.calculate.SumPairForPo;
import io.github.neel1210.excel_utils.ExcelReader;
import io.github.neel1210.excel_utils.ExcelWriter;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ConfigLoader {

    private static AppConfig appConfig = null;
    private static SumPairForCo sumPairForCo = null;
    private static SumPairForPo sumPairForPo = null;
    private static ExcelReader excelReader = null;
    private static ExcelWriter excelWriter = null;

    static {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(new Constructor(AppConfig.class, options));
        InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.yml");
        appConfig = yaml.load(input);
        excelReader = new ExcelReader();
        excelWriter = new ExcelWriter();
        sumPairForCo = new SumPairForCo();
        sumPairForPo = new SumPairForPo();
    }

    public static AppConfig getAppConfig() {
        return appConfig != null ? appConfig : null;
    }

    public static AppConfig.Reader getCongifReader() {
        return appConfig != null ? appConfig.getReader() : null;
    }

    public static AppConfig.Writer getCongifWriter() {
        return appConfig != null ? appConfig.getWriter() : null;
    }

    public static SumPairForCo getSumPairForCo() {
        return sumPairForCo != null ? sumPairForCo : null;
    }

    public static SumPairForPo getSumPairForPo() {
        return sumPairForPo != null ? sumPairForPo : null;
    }

    public static ExcelReader getExcelReader() {
        return excelReader != null ? excelReader : null;
    }

    public static ExcelWriter getExcelWriter() {
        return excelWriter != null ? excelWriter : null;
    }
}