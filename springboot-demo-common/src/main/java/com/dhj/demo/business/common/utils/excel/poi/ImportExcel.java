package com.dhj.demo.business.common.utils.excel.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * excel读取工具类
 * 可以是多个sheet工作区间文件导入功能
 * @author bxlong
 *
 */
public class ImportExcel {

        private static Logger logger = LoggerFactory.getLogger(ImportExcel.class);
        private final static String xls = ".xls";
        private final static String xlsx = ".xlsx";

        /**
         * 读入excel文件，解析内容后返回结果
         *
         * @param inputStream
         * @throws IOException
         */
        public static List<String[]> importExcel(InputStream inputStream, String fileName) throws IOException {
            // 检查文件
            //checkFile(file, fileName);
            // 获得Workbook工作薄对象
            Workbook workbook = getWorkBook(inputStream, fileName);
            // 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
            List<String[]> list = new ArrayList<>();
            if (workbook != null) {
                if (workbook.getNumberOfSheets() < 1 || workbook.getSheetAt(0).getLastRowNum() == 0) {
                    logger.error("文件内容为空!");
                    throw new FileNotFoundException("文件不存在!");
                }
                for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {// 循环导入各个Sheet
                    // 获得当前sheet工作表
                    Sheet sheet = workbook.getSheetAt(sheetNum);
                    if (sheet == null) {
                        continue;
                    }
                    // 获得当前sheet的开始行
                    int firstRowNum = sheet.getFirstRowNum();
                    // 获得当前sheet的结束行
                    int lastRowNum = sheet.getLastRowNum();
                    // 根据行头确定每一行的列数，这里规定了行头的列数 = 数据的列数
                    int lastCellNum = sheet.getRow(0).getLastCellNum();
                    // 循环除了第一行的所有行
                    for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                        // 获得当前行
                        Row row = sheet.getRow(rowNum);
                        // 获得当前行的开始列
                        int firstCellNum = row.getFirstCellNum();
                        String[] cellDataArr = new String[lastCellNum];
                        // 循环当前行
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                            Cell cell = row.getCell(cellNum);
                            cellDataArr[cellNum] = getCellValue(cell);
                        }
                        list.add(cellDataArr);
                    }
                }
            }
            return list;
        }

        public static void checkFile(MultipartFile file, String fileName) throws IOException {
            // 判断文件是否存在
            if (null == file) {
                logger.error("文件不存在！");
                throw new FileNotFoundException("文件不存在！");
            }
            // 判断文件是否是excel文件
            if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
                logger.error(fileName + "不是excel文件");
                throw new IOException(fileName + "不是excel文件");
            }
        }

        /**
         * 获取工作区间
         *
         * @param is
         * @return
         */
        private static Workbook getWorkBook(InputStream is, String fileName) {
            // 创建Workbook工作薄对象
            Workbook workbook = null;
            try {
                // 获取文件io流
                //InputStream is = new FileInputStream(file);
                // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
                if (fileName.endsWith(xls)) {
                    // 2003
                    workbook = new HSSFWorkbook(is);
                } else if (fileName.endsWith(xlsx)) {
                    // 2007
                    workbook = new XSSFWorkbook(is);
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
            return workbook;
        }

        /**
         * 获取各个单元格的内容
         *
         * @param cell
         * @return
         */
        private static String getCellValue(Cell cell) {
            String cellValue = "";
            if (cell == null) {
                return cellValue;
            }
            // 判断数据的类型
            switch (cell.getCellTypeEnum()) {
                case NUMERIC: // 数值
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    // 把数字当成String来读，避免出现1读成1.0的情况
                    if (cellValue.indexOf(".") > 0) {
                        new DecimalFormat("#").format(cell.getNumericCellValue());
                    }
                    break;
                case STRING: // 字符串
                    cellValue = String.valueOf(cell.getStringCellValue());
                    break;
                case BOOLEAN: // Boolean
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA: // 公式
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case BLANK: // 空值
                    break;
                case ERROR: // ERROR
                    cellValue = "ERROR";
                    break;
                default:
                    cellValue = "UNDEFINED";
                    break;
            }
            return cellValue;
        }
}
