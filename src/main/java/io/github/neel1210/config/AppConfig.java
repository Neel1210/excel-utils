package io.github.neel1210.config;

import lombok.Data;

import java.beans.JavaBean;
import java.util.List;

@Data
@JavaBean
public class AppConfig {

    private Reader reader;
    private Writer writer;

    @Data
    public static class Reader {
        private String fileName;
        private int startRow;
        private List<String> header;
        private Range range;
        private String functionality;
        private int totalRows;
    }

    @Data
    public static class Writer {
        private String fileName;
        private List<String> header;
        private int totalRows;
    }

    @Data
    public static class Range {
        private MinMax PO;
        private MinMax CO;
    }

    @Data
    public static class MinMax {
        private int min;
        private int max;
        private int digit;
        private int targetMin;
        private int targetMax;
    }
}