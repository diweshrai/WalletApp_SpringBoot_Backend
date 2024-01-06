package com.example.demo.ExcelHelperPackage;

import com.example.demo.Dto.CustomerRequestDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GettingAllDataOfCustomerFromExcel {

    public static List<CustomerRequestDto> getTheDataOfCustomerFromExcel(MultipartFile file) {

        System.out.println("Inside Impl");

        List<CustomerRequestDto> customers = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0); 

                 for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
               
                String firstName = row.getCell(0).getStringCellValue();
                String lastName = row.getCell(1).getStringCellValue();
                String emailId = row.getCell(2).getStringCellValue();

                Cell contactNoCell = row.getCell(3);
                int contactNo = (contactNoCell.getCellType() == CellType.STRING)
                        ? Integer.parseInt(contactNoCell.getStringCellValue())
                        : (int) contactNoCell.getNumericCellValue();

                String password = row.getCell(4).getStringCellValue();
                LocalDate registrationDate = convertToLocalDate(row.getCell(5));
                String addressLine1 = row.getCell(6).getStringCellValue();
                String adressLine2 = row.getCell(7).getStringCellValue();
                String city = row.getCell(8).getStringCellValue();
                String state = row.getCell(9).getStringCellValue();


                Cell pincodeCell = row.getCell(10);
                int pincode = (pincodeCell.getCellType() == CellType.STRING)
                        ? Integer.parseInt(pincodeCell.getStringCellValue())
                        : (int) pincodeCell.getNumericCellValue();

                String gender = row.getCell(11).getStringCellValue();

                System.out.println(firstName);

                CustomerRequestDto customer = new CustomerRequestDto();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setEmailId(emailId);
                customer.setContactNo(contactNo);
                customer.setGender(gender);
                customer.setPassword(password);
                customer.setRegistrationDate(registrationDate);
                customer.setAddressLine1(addressLine1);
                customer.setAdressLine2(adressLine2);
                customer.setCity(city);
                customer.setState(state);
                customer.setPincode(pincode);

                customers.add(customer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(customers);
        return customers;
    }

    private static LocalDate convertToLocalDate(Cell cell) {

        if (DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        } else if (cell.getCellType() == CellType.STRING) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(cell.getStringCellValue(), formatter);
        } else {

            return null;
        }
    }

}
