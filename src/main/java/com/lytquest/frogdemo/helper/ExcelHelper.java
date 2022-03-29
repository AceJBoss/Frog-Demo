package com.lytquest.frogdemo.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lytquest.frogdemo.entity.Book;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = { "Id", "Title", "Description", "Downloads" };
    static String SHEET = "Books";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Book> readExcel(InputStream is) throws IOException {
        List<Book> tempBookList = new ArrayList<Book>();
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1;i<worksheet.getPhysicalNumberOfRows();i++) {
            Book book = new Book();

            XSSFRow row = worksheet.getRow(i);
            book.setId((long) row.getCell(0).getNumericCellValue());
            book.setTitle(row.getCell(1).getStringCellValue());
            book.setDescription(row.getCell(2).getStringCellValue());
            book.setDownloads((int)row.getCell(3).getNumericCellValue());
            tempBookList.add(book);
        }
        return tempBookList;
    }
}
