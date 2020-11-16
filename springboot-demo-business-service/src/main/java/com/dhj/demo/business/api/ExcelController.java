package com.dhj.demo.business.api;

import com.dhj.demo.business.aop.Timing;
import com.dhj.demo.business.common.model.ExcelItems;
import com.dhj.demo.business.common.utils.excel.easyexcel.EasyExcelUtil;
import com.dhj.demo.business.common.utils.excel.easyexcel.EasyExcelWriterFactory;
import com.dhj.demo.business.common.utils.excel.poi.*;
import com.dhj.demo.mp.entity.Items;
import com.dhj.demo.mp.service.IItemsService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private IItemsService iItemsService;

    /**
     * 使用easyexcel导出20万数据 耗时17218毫秒，加上了查询数据库的时间
     * @param response
     * @throws IOException
     */
    @Timing
    @GetMapping("/easy/export")
    public void exportEasyExcel(HttpServletResponse response) throws IOException {
        List<Items> list = iItemsService.list();
        EasyExcelWriterFactory writerFactory = EasyExcelUtil.writeWithSheetsWeb(response, "商品信息表");
        writerFactory.writeModel(ExcelItems.class,list,"sheet1");
        writerFactory.finish();
    }

    /**
     * 使用easyexcel导入20万数据 22007毫秒
     * @param file
     * @throws IOException
     */
    @Timing
    @PostMapping("/easy/import")
    public void importEasyExcel(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<Map<Integer, String>> maps = EasyExcelUtil.syncRead(inputStream, 0, 1);
        System.out.println(maps.size());
        //后续可以根据具体逻辑进行处理数据

    }

    /**
     * POI 20w数据导入方法共用时:41060,单位:毫秒
     * @param file
     * @throws IOException
     * @throws InvalidFormatException
     * @throws PoiExcelParser.ParseException
     */
    @Timing
    @PostMapping("/poi/import")
    public void importPoiExcel(MultipartFile file) throws IOException, InvalidFormatException, PoiExcelParser.ParseException {
        String filename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        ImportExcel.checkFile(file,filename);
        List<String[]> list = ImportExcel.importExcel(inputStream, filename);
        System.out.println("poi导入数据量:"+list.size());
    }

    /**
     *20w 导出 用时 74381毫秒
     * @param response
     * @throws IOException
     * @throws InvalidFormatException
     * @throws PoiExcelParser.ParseException
     */
    @Timing
    @GetMapping("/poi/export")
    public void exportPoiExcel(HttpServletResponse response) throws IOException {
        List<Items> list = iItemsService.list();
        ExportExcel<Items> exportExcel = PoiExcelUtil.getPoiExcelExportInstance();
        ExportConfig exportConfig = new ExportConfig();

        exportConfig.setColumn("shoeName", "鞋名");
        exportConfig.setColumn("cityName", "城市名称");
        exportConfig.setColumn("price", "价格");
        exportConfig.setColumn("number","数量");
        exportConfig.setTitle("items信息");
        exportExcel.export(exportConfig,list,null,response);
    }

}
