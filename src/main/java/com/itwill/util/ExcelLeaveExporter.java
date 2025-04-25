//package com.itwill.util;
//
//import com.itwill.attendance.dto.AttendanceLeaveDTO;
//import com.itwill.attendance.model.Attendance;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.ByteArrayOutputStream;
//import java.util.List;
//
//public class ExcelLeaveExporter {
//
//    public static byte[] exportLeaveToExcel(List<AttendanceLeaveDTO> leaves) throws Exception {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Leave Reports");
//
//        Row header = sheet.createRow(0);
//        String[] columns = {"휴가ID", "시작일", "종료일", "유형", "사유", "승인상태"};
//        for (int i = 0; i < columns.length; i++) {
//            header.createCell(i).setCellValue(columns[i]);
//        }
//
//        int rowNum = 1;
//        for (AttendanceLeaveDTO leave : leaves) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(leave.getLeaveId());
//            row.createCell(1).setCellValue(leave.getStartDate().toString());
//            row.createCell(2).setCellValue(leave.getEndDate().toString());
//            row.createCell(3).setCellValue(leave.getLeaveType());
//            row.createCell(4).setCellValue(leave.getReason());
//            row.createCell(5).setCellValue(leave.getStatus());
//        }
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        workbook.write(out);
//        workbook.close();
//        return out.toByteArray();
//    }
//}
