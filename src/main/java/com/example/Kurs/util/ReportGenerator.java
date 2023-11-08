package com.example.Kurs.util;

import com.example.Kurs.models.Statistics;
import javafx.util.Pair;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {
    private static int sellIndex = 0;
    private static final String sourcesSheetName = "Source report";

    private static Workbook workbook = null;
    public static String getApsPath() {
        File file = new File("src/main/resources/reports/report.xlsx");
        return file.getAbsolutePath();
    }
    public static void generateXLSXReport() {
        File file = new File("src/main/resources/reports/report.xlsx");
        try (Workbook wb = generateReport();
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Workbook generateReport() {
        workbook = new SXSSFWorkbook();
        generateSourcesReport();
        return workbook;
    }
    private static void generateSourcesReport() {
        workbook.createSheet(sourcesSheetName);
        addSourcesNames();
        addProbabilityRefusalForSources();
    }
    private static void addSourcesNames() {
        SXSSFSheet sheet = (SXSSFSheet) workbook.getSheet(sourcesSheetName);
        SXSSFRow row = sheet.createRow(0);
        row.createCell(sellIndex).setCellValue("№ Источника");
        int rowCount = 1;
        Map <Integer, Double> map = Statistics.getProbabilityRefusalForSources();
        for (int i = 0; i < map.size(); i++) {
            row = sheet.createRow(rowCount++);
            row.createCell(sellIndex).setCellValue("И" + i);
        }
        sellIndex++;
    }
    private static void addProbabilityRefusalForSources() {
        SXSSFSheet sheet = (SXSSFSheet) workbook.getSheet(sourcesSheetName);
        Map <Integer, Double> map = Statistics.getProbabilityRefusalForSources();
        SXSSFRow row = sheet.getRow(0);
        row.createCell(sellIndex).setCellValue("Pотк");
        int rowCount = 1;
        for (int i = 0; i < map.size(); i++) {
            row = sheet.getRow(rowCount++);
            row.createCell(sellIndex).setCellValue(map.get(i));
        }
    }
    //добавить остальную статистику
}
