package sw.melody.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sw.melody.modules.docker.entity.GeneSearchEntity;
import sw.melody.modules.docker.entity.ProductEntity;
import sw.melody.modules.docker.entity.SickEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ping
 * @create 2018-08-30 14:43
 **/

public class XlsxExcelUtils {
    public static void parse(HttpServletResponse response, String templateFile, SickEntity sick, ProductEntity prodcut, List<GeneSearchEntity> list, String userAgent, String outputFileName) throws IOException {
        InputStream input = new FileInputStream(templateFile);
        Workbook wb = new HSSFWorkbook(input);

        Sheet sheet = wb.getSheetAt(0);


        sheet.createRow(39);


        String formFileName = outputFileName;
        // 针对IE或者以IE为内核的浏览器：
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            formFileName = java.net.URLEncoder.encode(formFileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            formFileName = new String(formFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", formFileName));
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.close();
    }

    public static void parse(String inputFileName, String outputFileName, SickEntity sick, ProductEntity prodcut, List<GeneSearchEntity> list) throws IOException {
        InputStream input = new FileInputStream(inputFileName);
        Workbook wb = new XSSFWorkbook(input);
        Sheet sheet = wb.getSheetAt(0);

        int size = list.size();
        for (int i=1; i<size;i ++) {
            sheet.createRow(46);
        }
        Row row = sheet.getRow(45);
        row.getCell(0).setCellValue("test");

        OutputStream os = new FileOutputStream(new File(outputFileName));
        wb.write(os);
        os.close();
    }

}
