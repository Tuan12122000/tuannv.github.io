package com.example.registrationlogindemo.Excel;

import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.entity.Payment;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<PaymentDto> listPayments;

    public PaymentExcelExporter(List<PaymentDto> listPayments) {
        this.listPayments = listPayments;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("payments");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "STT", style);
        createCell(row, 1, "Họ Tên", style);
        createCell(row, 2, "Mã Khách Hàng", style);
        createCell(row, 3, "Số Tiền", style);
        createCell(row, 4, "Địa Chỉ", style);
        createCell(row, 5, "Nội Dung", style);
        createCell(row, 6, "Thời Gian Tạo Giao Dịch", style);
        createCell(row, 7, "Trạng Thái Giao Dịch", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        CreationHelper createHelper = workbook.getCreationHelper();
        font.setFontHeight(14);
        style.setFont(font);

        for (PaymentDto payment : listPayments) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, payment.getStt(), style);
            createCell(row, columnCount++, payment.getName(), style);
            createCell(row, columnCount++, payment.getUserId(), style);
            createCell(row, columnCount++, payment.getAmount(), style);
            createCell(row, columnCount++, payment.getAddress(), style);
            createCell(row, columnCount++, payment.getDescription(), style);
            createCell(row, columnCount++, payment.getTimeCreated(), style);
            createCell(row, columnCount++, payment.getStatus(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
