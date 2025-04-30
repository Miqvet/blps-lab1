package itmo.blps.lab1.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import itmo.blps.lab1.dto.DeliveryStatusMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    private static final Font VALUE_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY);

    public static File generateReceipt(DeliveryStatusMessage message) throws IOException, DocumentException {
        File tempFile = File.createTempFile("receipt_" + message.getOrderId(), ".pdf");

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(tempFile));

        try {
            writer.setPageEvent(new PageDecorator());
            document.open();

            // Title
            Paragraph title = new Paragraph("DELIVERY RECEIPT", TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Information Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.setWidths(new int[]{1, 3});

            addTableRow(table, "Date & Time:", String.valueOf(message.getTimestamp()));
            addTableRow(table, "Recipient Email:", message.getEmail());
            addTableRow(table, "Order Number:", String.valueOf(message.getOrderId()));
            addTableRow(table, "Status:", message.getStatus());

            document.add(table);

            // Separator
            document.add(new Chunk(createLineSeparator()));

            // Message Section
            Paragraph messageHeader = new Paragraph("Delivery Message:", HEADER_FONT);
            messageHeader.setSpacingBefore(15f);
            document.add(messageHeader);

            Paragraph messageText = new Paragraph(message.getMessage(), VALUE_FONT);
            messageText.setIndentationLeft(20f);
            document.add(messageText);

        } finally {
            if(document.isOpen()) document.close();
        }
        return tempFile;
    }

    private static void addTableRow(PdfPTable table, String header, String value) {
        table.addCell(createCell(header, HEADER_FONT, Element.ALIGN_RIGHT));
        table.addCell(createCell(value, VALUE_FONT, Element.ALIGN_LEFT));
    }

    private static PdfPCell createCell(String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5f);
        return cell;
    }

    private static LineSeparator createLineSeparator() {
        LineSeparator line = new LineSeparator();
        line.setLineColor(new BaseColor(200, 200, 200));
        line.setLineWidth(1f);
        return line;
    }

    // Page decoration
    static class PageDecorator extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            Rectangle rect = new Rectangle(
                    document.left() - 10,
                    document.bottom() - 10,
                    document.right() + 10,
                    document.top() + 10
            );
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(2f);
            rect.setBorderColor(new BaseColor(150, 150, 150));
            canvas.rectangle(rect);
        }
    }
}