package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelGenerator {

    public static void main(String[] args) {
        // 定义资源文件路径
        String resourcesPath = "src/main/resources/mapping文档测试数据.xlsx"; // 假设数据文件是 Excel 格式
        String outputFilePath = System.getProperty("user.home") + "/Desktop/output.xlsx"; // 输出到桌面

        try {
            // 读取资源文件
            List<Map<String, String>> data = readDataFromResources(resourcesPath);

            // 按 sink_table_name 分组
            Map<String, List<Map<String, String>>> groupedData = groupDataBySinkTableName(data);

            // 创建 Excel 工作簿
            Workbook workbook = new XSSFWorkbook();

            // 遍历每个分组，创建对应的工作表
            for (Map.Entry<String, List<Map<String, String>>> entry : groupedData.entrySet()) {
                String sheetName = entry.getKey(); // 工作表名称为 sink_table_name
                List<Map<String, String>> rows = entry.getValue();

                // 创建工作表
                Sheet sheet = workbook.createSheet(sheetName);

                // 设置列宽
                sheet.setColumnWidth(0, 5000); // 调整列宽，可根据需要调整
                sheet.setColumnWidth(1, 5000);
                sheet.setColumnWidth(2, 5000);
                sheet.setColumnWidth(3, 5000);
                sheet.setColumnWidth(4, 5000);
                sheet.setColumnWidth(5, 5000);
                sheet.setColumnWidth(6, 5000);
                sheet.setColumnWidth(7, 5000);
                sheet.setColumnWidth(8, 5000);
                sheet.setColumnWidth(9, 5000);

                // 写入多行表头
                writeMultiRowHeader(sheet, sheetName);

                // 填充数据
                int rowNum = 6; // 数据从第6行开始写入
                for (Map<String, String> row : rows) {
                    Row excelRow = sheet.createRow(rowNum++);
                    excelRow.createCell(0).setCellValue(row.get("sink_table_name")); // 表英文名
                    excelRow.createCell(1).setCellValue(""); // 表中文名（假设为空）
                    excelRow.createCell(2).setCellValue(row.get("sink_column_name")); // 字段名
                    excelRow.createCell(3).setCellValue(row.get("sink_column_comment")); // 字段描述
                    excelRow.createCell(4).setCellValue(row.get("sink_column_type_name")); // 类型
                    excelRow.createCell(5).setCellValue(row.get("source_table_name")); // 源表英文名
                    excelRow.createCell(6).setCellValue(""); // 源表中文名（假设为空）
                    excelRow.createCell(7).setCellValue(row.get("source_column_name")); // 字段名
                    excelRow.createCell(8).setCellValue(row.get("source_column_comment")); // 字段描述
                    excelRow.createCell(9).setCellValue(row.get("source_column_type_name")); // 类型
                }

                // 应用样式
                applyStyles(sheet);
            }

            // 保存 Excel 文件
            try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
                workbook.write(fileOut);
            }

            System.out.println("Excel 文件已成功生成并保存到桌面: " + outputFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按 sink_table_name 分组数据
     *
     * @param data 输入数据列表
     * @return 按 sink_table_name 分组的数据 Map
     */
    private static Map<String, List<Map<String, String>>> groupDataBySinkTableName(List<Map<String, String>> data) {
        Map<String, List<Map<String, String>>> groupedData = new HashMap<>();
        for (Map<String, String> row : data) {
            String sinkTableName = row.get("sink_table_name");
            groupedData.computeIfAbsent(sinkTableName, k -> new ArrayList<>()).add(row);
        }
        return groupedData;
    }

    /**
     * 写入多行表头
     *
     * @param sheet Excel 工作表
     * @param sheetName 工作表名称
     */
    private static void writeMultiRowHeader(Sheet sheet, String sheetName) {
        // 创建第一行表头
        Row headerRow1 = sheet.createRow(0);
        headerRow1.createCell(0).setCellValue("程序名称");
        headerRow1.createCell(1).setCellValue("imp_" + sheetName);
        headerRow1.createCell(7).setCellValue("调度周期");
        headerRow1.createCell(8).setCellValue("天");

        // 创建第二行表头
        Row headerRow2 = sheet.createRow(1);
        headerRow2.createCell(0).setCellValue("目标表名");
        headerRow2.createCell(1).setCellValue(sheetName);
        headerRow2.createCell(7).setCellValue("目标表中文名称");
        headerRow2.createCell(8).setCellValue("");

        // 创建第三行表头
        Row headerRow3 = sheet.createRow(2);
        headerRow3.createCell(0).setCellValue("分区");
        headerRow3.createCell(1).setCellValue("ins");
        headerRow3.createCell(7).setCellValue("分区内容");
        headerRow3.createCell(8).setCellValue("$\\{#date(0,0,-1)\\}yyyyMMdd\\$");

        // 创建第四行表头
        Row headerRow4 = sheet.createRow(3);
        headerRow4.createCell(0).setCellValue("表英文名(必填)");
        headerRow4.createCell(1).setCellValue("表中文名");
        headerRow4.createCell(2).setCellValue("字段名(必填)");
        headerRow4.createCell(3).setCellValue("字段描述");
        headerRow4.createCell(4).setCellValue("类型");
        headerRow4.createCell(5).setCellValue("源表英文名(必填)");
        headerRow4.createCell(6).setCellValue("源表中文名");
        headerRow4.createCell(7).setCellValue("字段名");
        headerRow4.createCell(8).setCellValue("字段描述");
        headerRow4.createCell(9).setCellValue("类型");

        // 创建第五行表头
        Row headerRow5 = sheet.createRow(4);
        headerRow5.createCell(0).setCellValue("表英文名");
        headerRow5.createCell(1).setCellValue("表中文名");
        headerRow5.createCell(2).setCellValue("字段名");
        headerRow5.createCell(3).setCellValue("字段描述");
        headerRow5.createCell(4).setCellValue("类型");
        headerRow5.createCell(5).setCellValue("源表英文名");
        headerRow5.createCell(6).setCellValue("源表中文名");
        headerRow5.createCell(7).setCellValue("字段名");
        headerRow5.createCell(8).setCellValue("字段描述");
        headerRow5.createCell(9).setCellValue("类型");
    }

    /**
     * 应用样式：设置字体大小、背景颜色等
     *
     * @param sheet Excel 工作表
     */
    private static void applyStyles(Sheet sheet) {
        // 设置表头样式
        Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setBold(true); // 字体加粗
        headerFont.setFontHeightInPoints((short) 12); // 字体大小

        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex()); // 背景颜色
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(headerFont);

        // 应用样式到前5行表头
        for (int i = 0; i < 5; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                for (Cell cell : row) {
                    cell.setCellStyle(headerStyle);
                }
            }
        }

        // 设置表头单元格的边框
        CellStyle borderStyle = sheet.getWorkbook().createCellStyle();
        borderStyle.setBorderBottom(BorderStyle.THIN);
        borderStyle.setBorderTop(BorderStyle.THIN);
        borderStyle.setBorderLeft(BorderStyle.THIN);
        borderStyle.setBorderRight(BorderStyle.THIN);

        for (int i = 0; i < 5; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                for (Cell cell : row) {
                    cell.setCellStyle(borderStyle);
                }
            }
        }

        // 设置数据行的边框
        for (int rowNum = 5; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                for (Cell cell : row) {
                    cell.setCellStyle(borderStyle);
                }
            }
        }
    }

    /**
     * 读取资源文件中的数据
     *
     * @param filePath 资源文件路径
     * @return 数据列表，每个元素是一个 Map，表示一行数据
     */
    private static List<Map<String, String>> readDataFromResources(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] headers = {"name", "source_table_name", "source_column_name", "source_column_type_name",
                "sink_table_name", "sink_column_name", "sink_column_comment", "sink_column_type_name",
                "column_name", "db_name"};

        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // 假设数据在第一个工作表中
            Iterator<Row> rowIterator = sheet.iterator();

            // 跳过表头
            if (rowIterator.hasNext()) {
                rowIterator.next(); // 跳过第一行表头
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, String> rowData = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = row.getCell(i);
                    String value = cell != null ? cell.toString() : "";
                    rowData.put(headers[i], value);
                }
                data.add(rowData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}