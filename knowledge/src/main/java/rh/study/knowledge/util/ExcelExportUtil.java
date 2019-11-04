package rh.study.knowledge.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

/**
 * Created by 18121254 on 2018/12/11.
 */
public class ExcelExportUtil {

    /**
     * 功能描述: 导出Excel
     *
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void outWrite(HttpServletRequest request, HttpServletResponse response, XSSFWorkbook wb,
                                String fileName) throws IOException {
        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            wb.write(output);
            output.flush();
        } catch (IOException e) {
//        	logger.logException(e);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /**
     * 功能描述: 字体加粗
     *
     * @param workbook
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static HSSFCellStyle boldStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static boolean containsNum(String strRet) {
        return Pattern.compile("[0]+[0-9]+").matcher(strRet).matches();
    }

    public static void main(String[] args) {
        System.out.println(containsNum("0"));
        System.out.println(containsNum("00EA"));
        System.out.println(containsNum("001"));
        System.out.println(containsNum("1001"));
    }
}
