package com.example.demo.ExcelHelperPackage;

import com.example.demo.Constant.ConstantFile;
import com.example.demo.Model.Customer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class CustomerExcelDownloadHelper {

    public static String[] HEADERS = {

            "customerId", "firstName", "lastName", "emailId", "contactNo", "password", "registrationDate",
            "addressLine1", "adressLine2", "city", "state", "pincode", "gender"

    };

    public static String[] HEADERS_WITHOUT_CUSTOMERID = {

            "firstName", "lastName", "emailId", "contactNo", "password", "registrationDate", "addressLine1",
            "adressLine2", "city", "state", "pincode", "gender"

    };

    public static ByteArrayInputStream customerDataDownloadInExcel(List<Customer> customerList) {

        try {

            @SuppressWarnings("resource")
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Sheet sheet = workbook.createSheet(ConstantFile.All_CUSTOMERS_EXCEL_DOWNLOAD);

            Row row = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            int rowIndex = 1;

            for (Customer customer : customerList) {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;
                dataRow.createCell(0).setCellValue(customer.getCustomerId());
                dataRow.createCell(1).setCellValue(customer.getFirstName());
                dataRow.createCell(2).setCellValue(customer.getLastName());
                dataRow.createCell(3).setCellValue(customer.getEmailId());
                dataRow.createCell(4).setCellValue(customer.getContactNo());
                dataRow.createCell(5).setCellValue(customer.getPassword());
                dataRow.createCell(6).setCellValue(customer.getRegistrationDate());
                dataRow.createCell(7).setCellValue(customer.getAddress().getAddressLine1());
                dataRow.createCell(8).setCellValue(customer.getAddress().getAdressLine2());
                dataRow.createCell(9).setCellValue(customer.getAddress().getCity());
                dataRow.createCell(10).setCellValue(customer.getAddress().getState());
                dataRow.createCell(11).setCellValue(customer.getAddress().getPincode());
                dataRow.createCell(12).setCellValue(customer.getGender());

            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();

            return (null);
        } finally {

        }

    }

    public static ByteArrayInputStream customerHeadersDownloadInExcel() {

        try {

            @SuppressWarnings("resource")
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Sheet sheet = workbook.createSheet(ConstantFile.All_CUSTOMERS_EXCEL_DOWNLOAD);

            Row row = sheet.createRow(0);
            for (int i = 0; i < HEADERS_WITHOUT_CUSTOMERID.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS_WITHOUT_CUSTOMERID[i]);
            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();

            return (null);
        } finally {

        }

    }

}
