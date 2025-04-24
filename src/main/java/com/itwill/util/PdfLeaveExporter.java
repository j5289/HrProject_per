//package com.itwill.util;
//
//import com.itwill.attendance.dto.AttendanceLeaveDTO;
//import com.itwill.attendance.model.Attendance;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//
//import java.util.stream.Stream;
//import java.io.ByteArrayOutputStream;
//import java.util.List;
//
//public class PdfLeaveExporter {
//
//    public static byte[] exportLeaveToPdf(List<AttendanceLeaveDTO> leaves) throws Exception {
//        Document document = new Document();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        PdfWriter.getInstance(document, out);
//        document.open();
//
//        Paragraph title = new Paragraph("나의 휴가 보고서");
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//        document.add(Chunk.NEWLINE);
//
//        PdfPTable table = new PdfPTable(6);
//        Stream.of("휴가ID", "시작일", "종료일", "유형", "사유", "승인상태")
//              .forEach(header -> {
//                  PdfPCell cell = new PdfPCell(new Phrase(header));
//                  cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                  table.addCell(cell);
//              });
//
//        for (AttendanceLeaveDTO leave : leaves) {
//            table.addCell(leave.getLeaveId());
//            table.addCell(leave.getStartDate().toString());
//            table.addCell(leave.getEndDate().toString());
//            table.addCell(leave.getLeaveType());
//            table.addCell(leave.getReason());
//            table.addCell(leave.getStatus());
//        }
//
//        document.add(table);
//        document.close();
//        return out.toByteArray();
//    }
//}
