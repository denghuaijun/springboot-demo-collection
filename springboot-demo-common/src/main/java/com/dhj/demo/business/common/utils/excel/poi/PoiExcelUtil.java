package com.dhj.demo.business.common.utils.excel.poi;

/**
 * 使用单例得到对应的PoiExcelParser 对象
 */
public class PoiExcelUtil {

    private static PoiExcelParser poiExcelParser ;

    private  PoiExcelUtil(){};

    /**
     *获取POI导入对象
     * @return
     */
    public static PoiExcelParser getPoiExcelImportParser(){
        synchronized (PoiExcelUtil.class){
            if (poiExcelParser == null) {
                return new PoiExcelParser();
            }
        }
        return poiExcelParser;

    }

    /**
     * 获取导出对象
     * @return
     */
    public static ExportExcel getPoiExcelExportInstance(){

        return new ExportExcel();

    }

}
