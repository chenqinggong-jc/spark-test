package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelGenerator {

    public static void main(String[] args) {
        // 定义资源文件路径
        String resourcesPath = "src/main/resources/mapping文档测试数据.xlsx"; // 假设数据文件是 Excel 格式
        String outputFilePath = System.getProperty("user.home") + "/Documents/工作目录/project/output.xlsx"; // 输出到桌面

        try {
            // 读取资源文件
            List<Map<String, String>> data = readDataFromResources(resourcesPath);

            // 创建 Excel 工作簿
            Workbook workbook = new XSSFWorkbook();

            // 提取唯一的 sink_table_name 并去重
            Set<String> uniqueSinkTableNames = new HashSet<>();
            for (Map<String, String> row : data) {
                uniqueSinkTableNames.add(row.get("sink_table_name"));
            }


            // 遍历每个唯一的 sink_table_name，创建对应的 Sheet
            for (String sinkTableName : uniqueSinkTableNames) {

                // 修复步骤开始
                // 1. 处理空名称（使用默认名称）
                if (sinkTableName == null || sinkTableName.trim().isEmpty()) {
                    sinkTableName = "UnnamedSheet";
                }

                // 2. 截断至31字符（Excel限制）
                String truncatedName = sinkTableName.length() > 31 ?
                        sinkTableName.substring(0, 31) :
                        sinkTableName;

                // 3. 移除非法字符（使用POI内置工具）
                String safeSheetName = WorkbookUtil.createSafeSheetName(truncatedName);
                // 修复步骤结束

               // Sheet sheet = workbook.createSheet(safeSheetName);

                // 创建新的工作表
                Sheet sheet = workbook.createSheet(sinkTableName);

                // 写入元信息（程序名称、调度周期、分区等）
                Row metaRow1 = sheet.createRow(0);
                CellStyle metaStyle = getMetaCellStyle(workbook); // 设置元信息样式
                metaRow1.createCell(0).setCellValue("程序名称");
                metaRow1.createCell(1).setCellValue(data.get(0).get("name")); // 假设 name 是程序名称
                metaRow1.getCell(0).setCellStyle(metaStyle);
                metaRow1.getCell(1).setCellStyle(metaStyle);

                Row metaRow2 = sheet.createRow(1);
                metaRow2.createCell(0).setCellValue("调度周期");
                metaRow2.createCell(1).setCellValue(data.get(0).get("dd_date")); // 假设 dd_date 是调度周期
                metaRow2.getCell(0).setCellStyle(metaStyle);
                metaRow2.getCell(1).setCellStyle(metaStyle);

                Row metaRow3 = sheet.createRow(2);
                metaRow3.createCell(0).setCellValue("目标表名");
                metaRow3.createCell(1).setCellValue(sinkTableName); // 目标表名是 sink_table_name
                metaRow3.getCell(0).setCellStyle(metaStyle);
                metaRow3.getCell(1).setCellStyle(metaStyle);

                Row metaRow4 = sheet.createRow(3);
                metaRow4.createCell(0).setCellValue("目标表中文名称");
                metaRow4.createCell(1).setCellValue(""); // 假设目标表中文名称为空
                metaRow4.getCell(0).setCellStyle(metaStyle);
                metaRow4.getCell(1).setCellStyle(metaStyle);

                // 提取每个 sink_table_name 对应的 column_name 值
                String column_name_value = "";
                String column_type_value = "";
                for (Map<String, String> row : data) {
                    if (sinkTableName.equals(row.get("sink_table_name"))) {
                        column_name_value = row.get("column_name");
                        column_type_value = row.get("column_type");
                        break; // 只取第一行数据
                    }
                }

                Row metaRow5 = sheet.createRow(4);
                metaRow5.createCell(0).setCellValue("分区");
                System.out.println("数据输出 :"+data);
                System.out.println("数据输出2 :"+data);
                System.out.println("数据输出3 :"+data);
                System.out.println("数据输出4 :"+data);
                System.out.println("数据输出5 :"+data);
                System.out.println("数据输出6 main修改 :"+data);
                metaRow5.createCell(1).setCellValue(column_name_value); // 假设 column_name 是分区
                metaRow5.getCell(0).setCellStyle(metaStyle);
                metaRow5.getCell(1).setCellStyle(metaStyle);





                Row metaRow6 = sheet.createRow(5);
                metaRow6.createCell(0).setCellValue("分区内容");
                metaRow6.createCell(1).setCellValue(column_type_value); // 假设 column_type 是分区内容
                metaRow6.getCell(0).setCellStyle(metaStyle);
                metaRow6.getCell(1).setCellStyle(metaStyle);

                // 写入 Excel 表头
                Row headerRow1 = sheet.createRow(7); // 表头从第8行开始
                String[] headers1 = {"表英文名", "表中文名", "字段名", "字段描述", "类型", "源表英文名", "源表中文名", "字段名", "字段描述", "类型"};
                CellStyle headerStyle = getHeaderCellStyle(workbook); // 设置表头样式

                for (int i = 0; i < headers1.length; i++) {
                    Cell cell = headerRow1.createCell(i);
                    cell.setCellValue(headers1[i]);
                    cell.setCellStyle(headerStyle);
                }

//                Row headerRow2 = sheet.createRow(8); // 第二行表头
//                String[] headers2 = {"", "", "", "", "", "", "", "", "", ""};
//                for (int i = 0; i < headers2.length; i++) {
//                    Cell cell = headerRow2.createCell(i);
//                    cell.setCellValue(headers2[i]);
//                    cell.setCellStyle(headerStyle);
//                }

                // 填充数据
                int rowNum = 8;
                for (Map<String, String> row : data) {
                    if (row.get("sink_table_name").equals(sinkTableName)) {
                        Row excelRow = sheet.createRow(rowNum++);
                        excelRow.createCell(0).setCellValue(row.get("sink_table_name")); // 表英文名
                        excelRow.createCell(1).setCellValue(row.get("sink_table_name")); // 表中文名
                        excelRow.createCell(2).setCellValue(row.get("sink_column_name")); // 字段名
                        excelRow.createCell(3).setCellValue(row.get("sink_column_comment")); // 字段描述
                        excelRow.createCell(4).setCellValue(row.get("sink_column_type_name")); // 类型
                        excelRow.createCell(5).setCellValue(row.get("source_table_name")); // 源表英文名
                        excelRow.createCell(6).setCellValue(row.get("source_table_name")); // 源表中文名
                        excelRow.createCell(7).setCellValue(row.get("source_column_name")); // 字段名
                        excelRow.createCell(8).setCellValue(row.get("source_column_comment")); // 字段描述
                        excelRow.createCell(9).setCellValue(row.get("source_column_type_name")); // 类型
                    }
                }

                // 自动调整列宽
                for (int i = 0; i < headers1.length; i++) {
                    sheet.autoSizeColumn(i);
                }
                System.out.println("column_name value: " + data.get(0).get("column_name"));

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
     * 获取元信息单元格样式
     *
     * @param workbook 工作簿对象
     * @return 元信息单元格样式
     */
    private static CellStyle getMetaCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12); // 字体大小
        font.setColor(IndexedColors.BLACK.getIndex()); // 字体颜色
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER); // 居中对齐
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex()); // 背景颜色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    /**
     * 获取表头单元格样式
     *
     * @param workbook 工作簿对象
     * @return 表头单元格样式
     */
    private static CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12); // 字体大小
        font.setBold(true); // 加粗
        font.setColor(IndexedColors.WHITE.getIndex()); // 字体颜色
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER); // 居中对齐
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex()); // 背景颜色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    /**
     * 读取资源文件中的数据
     *
     * @param filePath 资源文件路径
     * @return 数据列表，每个元素是一个 Map，表示一行数据
     */


    private static List<Map<String, String>> readDataFromResources(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            // 假设数据在第一个工作表中
            Sheet sheet = workbook.getSheetAt(0);

            // 获取表头行（假设第一行是表头）
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Excel 文件中没有表头行");
            }

            // 动态获取表头列名
            List<String> actualHeaders = new ArrayList<>();
            for (Cell cell : headerRow) {
                actualHeaders.add(cell.getStringCellValue());
            }

            // 遍历数据行（从第二行开始）
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next(); // 跳过第一行表头
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, String> rowData = new HashMap<>();

                // 遍历每一列
                for (int i = 0; i < actualHeaders.size(); i++) {
                    Cell cell = row.getCell(i);
                    DataFormatter formatter = new DataFormatter();
                    String value = cell != null ? formatter.formatCellValue(cell) : "";
                    rowData.put(actualHeaders.get(i), value);
                }

                // 添加到数据列表
                data.add(rowData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }



//    private static List<Map<String, String>> readDataFromResources(String filePath) {
//        List<Map<String, String>> data = new ArrayList<>();
//        String[] headers = {"name", "source_table_name", "source_column_name", "source_column_type_name",
//                "sink_table_name", "sink_column_name", "sink_column_comment", "sink_column_type_name",
//                "column_name", "db_name", "dd_date", "column_type"};
//
//
//        try (InputStream inputStream = new FileInputStream(filePath);
//             Workbook workbook = new XSSFWorkbook(inputStream)) {
//
//            // 假设数据在第一个工作表中
//            Sheet sheet = workbook.getSheetAt(0);
//
//            // 获取表头行（假设第一行是表头）
//            Row headerRow = sheet.getRow(0);
//            if (headerRow == null) {
//                throw new IllegalStateException("Excel 文件中没有表头行");
//            }
//
//            // 遍历数据行（从第二行开始）
//            Iterator<Row> rowIterator = sheet.iterator();
//            if (rowIterator.hasNext()) {
//                rowIterator.next(); // 跳过第一行表头
//            }
//
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                Map<String, String> rowData = new HashMap<>();
//
//                // 遍历每一列
//                for (int i = 0; i < headers.length; i++) {
//                    Cell cell = row.getCell(i);
//                    String value = cell != null ? cell.toString() : "";
//                    rowData.put(headers[i], value);
//                }
//
//                // 添加到数据列表
//                data.add(rowData);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return data;
//    }
}