package com.dhj.demo.business.common.utils.excel.poi;

import com.alibaba.excel.util.CollectionUtils;
import com.dhj.demo.business.common.entity.ExcelUser;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * poi Excel导出工具类
 * @param <T> 对应传入的导入model
 */
@Slf4j
public class ExportExcel<T> {
    /**
     * 导出文件
     *
     * @param exportConfig 导出配置
     * @param dataSet      数据集
     * @param out          输出流
     */
    public void export(ExportConfig exportConfig, Collection<T> dataSet, OutputStream out,HttpServletResponse response) {

        switch (exportConfig.getVersion()) {
            case XLSX:
                exportXlsx(exportConfig, dataSet, out,response);
                break;
            case XLS:
                exportXls(exportConfig, dataSet, out,response);
                break;
            default:
                exportXlsx(exportConfig, dataSet, out,response);
                break;
        }
    }

    /**
     * 2003  xls
     *
     * @param exportConfig 导出配置
     * @param dataSet      数据
     * @param out          输出流  文件or网络
     */
    private void exportXls(ExportConfig exportConfig, Collection<T> dataSet, OutputStream out,HttpServletResponse response) {

        if (Objects.isNull(exportConfig) || CollectionUtils.isEmpty(exportConfig.getColumn())) {
            throw new IllegalArgumentException("The workbook config is error ");
        }
        HSSFWorkbook workbook = new HSSFWorkbook();

        String sheetName = exportConfig.getTitle();
        if (Strings.isNullOrEmpty(sheetName)) {
            sheetName = "Sheet";
        }
        HSSFSheet sheet = workbook.createSheet(sheetName);
        SimpleDateFormat sdf = new SimpleDateFormat(exportConfig.getDataFormat());
        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
        CellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
        CellStyle style = getStyle(workbook);                  //单元格样式对象
        /**
         * 读取配置中的 头和字段信息
         */
        String[] headers = new String[exportConfig.getColumn().size()];
        String[] fields = new String[exportConfig.getColumn().size()];
        for (int i = 0; i < exportConfig.getColumn().size(); i++) {
            headers[i] = exportConfig.getColumn().get(i).getName();
            fields[i] = exportConfig.getColumn().get(i).getField();
        }

        /**
         *创建Excel 第一行 HSSFRichTextString富文本字体，设置表头信息
         */
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
        /**
         * 从第一行开始往里面对应列种添加数据，利用反射
         */
        Iterator<T> it = dataSet.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            for (int j = 0; j < fields.length; j++) {
                HSSFCell cell = row.createCell(j);
                try {
                    cell.setCellValue(getStringValue(sdf, getValue(t, fields[j])));

                } catch (NoSuchMethodException e) {

                    log.error(e.getMessage());
                } catch (IllegalAccessException e) {

                    log.error(e.getMessage());
                } catch (InvocationTargetException e) {

                    log.error(e.getMessage());
                } catch (NoSuchFieldException e) {
                    log.error(e.getMessage());
                }
            }
        }
        if (out !=null && response ==null){//此情况为导出对应目录文件
            close(out, workbook);
        }else if (out ==null && response !=null){
            httpExcle(workbook,response,exportConfig);
        }
    }

    private void close(OutputStream out, Workbook workbook) {
        try {
            workbook.write(out);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                workbook.close();
                out.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        }
    }


    /**
     * 2007  xlsx
     *
     * @param exportConfig 导出配置
     * @param dataSet      数据
     * @param out          输出流  文件or网络
     */
    private void exportXlsx(ExportConfig exportConfig, Collection<T> dataSet, OutputStream out,HttpServletResponse response) {

        if (Objects.isNull(exportConfig) || CollectionUtils.isEmpty(exportConfig.getColumn())) {
            throw new IllegalArgumentException("The workbook config is error ");
        }
        XSSFWorkbook workbook = new XSSFWorkbook();

        String sheetName = exportConfig.getTitle();
        if (Strings.isNullOrEmpty(sheetName)) {
            sheetName = "Sheet";
        }
        XSSFSheet sheet = workbook.createSheet(sheetName);
        SimpleDateFormat sdf = new SimpleDateFormat(exportConfig.getDataFormat());
        /**
         * 读取配置中的 头和字段信息
         */
        String[] headers = new String[exportConfig.getColumn().size()];
        String[] fields = new String[exportConfig.getColumn().size()];
        for (int i = 0; i < exportConfig.getColumn().size(); i++) {
            headers[i] = exportConfig.getColumn().get(i).getName();
            fields[i] = exportConfig.getColumn().get(i).getField();
        }
        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
        CellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
        CellStyle style = getStyle(workbook);                  //单元格样式对象
        /**
         *创建Excel 第一行 HSSFRichTextString富文本字体
         */
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            //设置表头样式
           // cell.setCellStyle(columnTopStyle);
        }
        Iterator<T> it = dataSet.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            for (int j = 0; j < fields.length; j++) {
                XSSFCell cell = row.createCell(j);

                try {
                    String strValue = getStringValue(sdf, getValue(t, fields[j]));

                    cell.setCellValue(strValue);

                } catch (NoSuchMethodException e) {

                    log.error(e.getMessage());
                } catch (IllegalAccessException e) {

                    log.error(e.getMessage());
                } catch (InvocationTargetException e) {

                    log.error(e.getMessage());
                } catch (NoSuchFieldException e) {
                    log.error(e.getMessage());
                }
            }
        }
        if (out !=null && response ==null){//此情况为导出对应目录文件
            close(out, workbook);
        }else if (out ==null && response !=null){
            httpExcle(workbook,response,exportConfig);
        }

    }

    private String getStringValue(SimpleDateFormat sdf, Object value) {
        String strValue;
        if (value instanceof Date) {
            strValue = sdf.format(value);
        } else {
            // 其它数据类型都当作字符串简单处理
            strValue = (value == null) ? "" : value.toString();
        }
        return strValue;
    }

    /**
     * 获取 对象值
     *
     * @param cla
     * @param fieldName
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    private Object getValue(Object cla, String fieldName) throws NoSuchFieldException, NoSuchMethodException,
            IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        Field field = cla.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        //两种方法都可以获取字段信息
        //        String getMethod = "get"
        //                + fieldName.substring(0, 1).toUpperCase()
        //                + fieldName.substring(1);
        //        Method method = cla.getClass().getMethod(getMethod, new Class[]{});
        //
        //        return method.invoke(cla, new Object[]{});
        return field.get(cla);
    }

    /*
     * 列头单元格样式
     */
    public static CellStyle getColumnTopStyle(Workbook workbook) {

        // 设置字体
        Font font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 13);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom( BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor( IndexedColors.BLACK.getIndex());
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;

    }

    /*
     * 列数据信息单元格样式
     */
    public static CellStyle getStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(true);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;

    }

    /**
     * 生成文件流
     * @param workbook
     * @param response
     */
    public static void httpExcle(Workbook workbook, HttpServletResponse response,ExportConfig exportConfig) {
        OutputStream output;
        BufferedOutputStream bufferedOutPut=null;
        String fileName = exportConfig.getFileName();
        switch (exportConfig.getVersion()) {
            case XLSX:
                fileName = fileName+".xlsx";
                break;
            case XLS:
                fileName = fileName+".xls";
                break;
            default:
                fileName = fileName+".xlsx";
                break;
        }
        try {
            response.reset();
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF8"));
            output = response.getOutputStream();
             bufferedOutPut= new BufferedOutputStream(output);
            bufferedOutPut.flush();
            workbook.write(bufferedOutPut);
            bufferedOutPut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
                try {
                    if (workbook !=null) {
                        workbook.close();
                    }
                    if (bufferedOutPut !=null){
                        bufferedOutPut.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    public static void main(String[] args) throws IOException {
        ExportConfig exportConfig = new ExportConfig();
        List<ExcelUser> list = Lists.newArrayList(new ExcelUser("aa", "21","aa.com"),
                new ExcelUser("bb", "22","bb.com"),
                new ExcelUser("cc", "23","cc.com"),
                new ExcelUser("dd", "24","dd.com"),
                new ExcelUser("ee", "25","ee.com"),
                new ExcelUser("ff", "26","ff.com"));

        exportConfig.setColumn("name", "姓名");
        exportConfig.setColumn("age", "年龄");
        exportConfig.setColumn("email", "电子邮箱");
        exportConfig.setTitle("用户测试信息");

        File file = new File("E:\\tmp\\excelUser3.xlsx");

        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);

        ExportExcel<ExcelUser> exportExcel =PoiExcelUtil.getPoiExcelExportInstance();

        exportExcel.export(exportConfig, list, fileOutputStream,null);

        fileOutputStream.flush();
        fileOutputStream.close();
        System.out.println("导出成功");
    }
}
