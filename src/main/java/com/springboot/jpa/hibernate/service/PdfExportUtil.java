package com.springboot.jpa.hibernate.service;  
 
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.springboot.jpa.hibernate.model.Role;
import com.springboot.jpa.hibernate.model.User;

@Component
public class PdfExportUtil {

    public void writeUsersToPdf(ByteArrayOutputStream outputStream, List<User> users) throws IOException {
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Add title
        document.add(new Paragraph("User List"));

        // Create bold font
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        // Create table with appropriate number of columns
        Table table = new Table(new float[]{1, 2,  2, 2, 2, 2});
        table.addHeaderCell(createHeaderCell("ID", boldFont));
        table.addHeaderCell(createHeaderCell("Username", boldFont));
        //table.addHeaderCell(createHeaderCell("Password", boldFont));
        table.addHeaderCell(createHeaderCell("Account Non-Locked", boldFont));
        table.addHeaderCell(createHeaderCell("Roles", boldFont));
        table.addHeaderCell(createHeaderCell("Failed Attempt Count", boldFont));
        table.addHeaderCell(createHeaderCell("Needs Password Change", boldFont));

        // Add user data to table
        for (User user : users) {
            table.addCell(user.getId().toString()).setTextAlignment(TextAlignment.CENTER);
            table.addCell(user.getUsername()).setTextAlignment(TextAlignment.CENTER);
           // table.addCell(user.getPassword()).setTextAlignment(TextAlignment.CENTER);
            table.addCell(String.valueOf(user.isAccountNonLocked())).setTextAlignment(TextAlignment.CENTER);
            table.addCell(user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", "))).setTextAlignment(TextAlignment.CENTER);
            table.addCell(String.valueOf(user.getFailedAttemptCount())).setTextAlignment(TextAlignment.CENTER);
            table.addCell(String.valueOf(user.isNeedsPasswordChange())).setTextAlignment(TextAlignment.CENTER);
        }

        document.add(table);
        document.close();
    }
    
//    public void writeUsersToPdf(HttpServletResponse response, List<User> users) throws IOException {
//        PdfWriter writer = new PdfWriter(response.getOutputStream());
//        PdfDocument pdfDoc = new PdfDocument(writer);
//        Document document = new Document(pdfDoc);
//
//        // Add title
//        document.add(new Paragraph("User List"));
//
//        // Create bold font
//        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
//        // Create table with appropriate number of columns
//        Table table = new Table(new float[]{1, 2,  2, 2, 2, 2});
//        table.addHeaderCell(createHeaderCell("ID", boldFont));
//        table.addHeaderCell(createHeaderCell("Username", boldFont));
//        //table.addHeaderCell(createHeaderCell("Password", boldFont));
//        table.addHeaderCell(createHeaderCell("Account Non-Locked", boldFont));
//        table.addHeaderCell(createHeaderCell("Roles", boldFont));
//        table.addHeaderCell(createHeaderCell("Failed Attempt Count", boldFont));
//        table.addHeaderCell(createHeaderCell("Needs Password Change", boldFont));
//
//        // Add user data to table
//        for (User user : users) {
//            table.addCell(user.getId().toString()).setTextAlignment(TextAlignment.CENTER);
//            table.addCell(user.getUsername()).setTextAlignment(TextAlignment.CENTER);
//           // table.addCell(user.getPassword()).setTextAlignment(TextAlignment.CENTER);
//            table.addCell(String.valueOf(user.isAccountNonLocked())).setTextAlignment(TextAlignment.CENTER);
//            table.addCell(user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", "))).setTextAlignment(TextAlignment.CENTER);
//            table.addCell(String.valueOf(user.getFailedAttemptCount())).setTextAlignment(TextAlignment.CENTER);
//            table.addCell(String.valueOf(user.isNeedsPasswordChange())).setTextAlignment(TextAlignment.CENTER);
//        }
//
//        document.add(table);
//        document.close();
//    }
    private Cell createHeaderCell(String content, PdfFont font) {
        Cell cell = new Cell().add(new Paragraph(content).setFont(font).setFont(font).setFontColor(new DeviceRgb(0, 0, 255)));
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }
      
}
