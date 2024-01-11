package com.example.demo.Dto;

import com.example.demo.Model.Account;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Transaction;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfGenerator {

    public static ByteArrayInputStream generatePdf(Customer customer, List<Transaction> transactions) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new PageNumberEvent());

            document.open();

            /*
             *  Add heading
             */
            Paragraph heading = new Paragraph("Customer Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            heading.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);

            /*
             * Customer details
             */
            document.add(new Paragraph("Full-Name: " + customer.getFirstName() + " " + customer.getLastName()));
            document.add(new Paragraph("Email: " + customer.getEmailId()));
            document.add(new Paragraph("Contact No: " + customer.getContactNo()));

            /*
             Address details
             */
            document.add(new Paragraph("\nAddress Details:"));
            document.add(new Paragraph("Address Line 1: " + customer.getAddress().getAddressLine1()));
            document.add(new Paragraph("Address Line 2: " + customer.getAddress().getAdressLine2()));
            document.add(new Paragraph("City: " + customer.getAddress().getCity()));
            document.add(new Paragraph("State: " + customer.getAddress().getState()));
            document.add(new Paragraph("Pincode: " + customer.getAddress().getPincode()));

            document.add(new Paragraph("Gender: " + customer.getGender()));
            document.add(new Paragraph("Registration Date: " + customer.getRegistrationDate()));
            document.add(new Paragraph("Customer Status: " + customer.getCustomerStatus()));

            /*
             * Account details
             */
            document.add(new Paragraph("\nAccount Details:"));

            document.add(new Paragraph("\n"));
            PdfPTable accountTable = new PdfPTable(5);
            accountTable.setWidthPercentage(100);

            /*
             *  Table header
             */
            PdfPCell accountCell = new PdfPCell(new Paragraph("Account No"));
            accountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            accountCell.setBorderWidth(1);
            accountTable.addCell(accountCell);

            accountCell = new PdfPCell(new Paragraph("Account Type"));
            accountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            accountCell.setBorderWidth(1);
            accountTable.addCell(accountCell);

            accountCell = new PdfPCell(new Paragraph("Balance"));
            accountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            accountCell.setBorderWidth(1);
            accountTable.addCell(accountCell);

            accountCell = new PdfPCell(new Paragraph("Account Status"));
            accountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            accountCell.setBorderWidth(1);
            accountTable.addCell(accountCell);
            
            accountCell = new PdfPCell(new Paragraph("Openning Date"));
            accountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            accountCell.setBorderWidth(1);
            accountTable.addCell(accountCell);
            
            

            // Table data
            List<Account> accounts = customer.getAccount();
            for (Account account : accounts) {
                accountTable.addCell(String.valueOf(account.getAccountNo()));
                accountTable.addCell(account.getAccountType());
                accountTable.addCell(String.valueOf(account.getOpeningBalance()));
                accountTable.addCell(account.getDescription());
                accountTable.addCell(account.getOpeningDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            document.add(accountTable);

            // Transaction table
            document.add(new Paragraph("\nAll Transactions:"));

            document.add(new Paragraph("\n"));
            PdfPTable transactionTable = new PdfPTable(5); // 5 columns
            transactionTable.setWidthPercentage(100);

            // Table header
            PdfPCell cell = new PdfPCell(new Paragraph("Transaction ID"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidth(1);
            transactionTable.addCell(cell);

            cell = new PdfPCell(new Paragraph("Transaction Type"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidth(1);
            transactionTable.addCell(cell);

            cell = new PdfPCell(new Paragraph("Transaction Date"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidth(1);
            transactionTable.addCell(cell);

            cell = new PdfPCell(new Paragraph("Amount"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidth(1);
            transactionTable.addCell(cell);

            cell = new PdfPCell(new Paragraph("Description"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidth(1);
            transactionTable.addCell(cell);

            // Table data
            for (Transaction transaction : transactions) {
                transactionTable.addCell(String.valueOf(transaction.getTransactionId()));
                transactionTable.addCell(transaction.getTransactionType());
                transactionTable.addCell(transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                transactionTable.addCell(String.valueOf(transaction.getAmount()));
                transactionTable.addCell(transaction.getDescription());
            }

            document.add(transactionTable);
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static class PageNumberEvent extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            int pageNumber = writer.getPageNumber();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Page number
            Paragraph pageNumberText = new Paragraph("Page " + pageNumber, FontFactory.getFont(FontFactory.HELVETICA, 10));
            pageNumberText.setAlignment(Element.ALIGN_CENTER);
            pageNumberText.setSpacingBefore(10); 
            pageNumberText.setSpacingAfter(5);
            try {
                document.add(pageNumberText);
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            // Date
            Paragraph dateText = new Paragraph("Date: " + now.format(formatter), FontFactory.getFont(FontFactory.HELVETICA, 10));
            dateText.setAlignment(Element.ALIGN_LEFT);
            dateText.setSpacingAfter(5); 
            try {
                document.add(dateText);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

}
