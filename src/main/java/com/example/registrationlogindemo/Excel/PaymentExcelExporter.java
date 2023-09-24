package com.example.registrationlogindemo.Excel;

import com.example.registrationlogindemo.entity.Payment;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class PaymentExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Payment> listPayments;

    public PaymentExcelExporter(List<Payment> listPayments) {
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

        createCell(row, 0, "id", style);
        createCell(row, 1, "name", style);
        createCell(row, 2, "userId", style);
        createCell(row, 3, "amount", style);
        createCell(row, 4, "address", style);
        createCell(row, 5, "description", style);
        createCell(row, 6, "timeCreated", style);

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
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Payment payment : listPayments) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, payment.getId(), style);
            createCell(row, columnCount++, payment.getName(), style);
            createCell(row, columnCount++, payment.getUserId(), style);
            createCell(row, columnCount++, payment.getAmount(), style);
            createCell(row, columnCount++, payment.getAddress(), style);
            createCell(row, columnCount++, payment.getDescription(), style);
            createCell(row, columnCount++, payment.getTimeCreated(), style);
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
